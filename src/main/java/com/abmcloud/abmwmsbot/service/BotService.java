package com.abmcloud.abmwmsbot.service;

import com.abmcloud.abmwmsbot.dto.ReportData;
import com.abmcloud.abmwmsbot.enums.BotCommands;
import com.abmcloud.abmwmsbot.enums.ChartTypes;
import com.abmcloud.abmwmsbot.model.BotUser;
import com.abmcloud.abmwmsbot.model.Organization;
import com.abmcloud.abmwmsbot.model.Report;
import com.abmcloud.abmwmsbot.utils.ChartGoogleUtils;
import com.abmcloud.abmwmsbot.utils.ChartJavaFXUtils;
import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
//@RequiredArgsConstructor
public class BotService extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String name;
    @Value("${bot.token}")
    private String token;

    private final ReportService reportService;
    private final UserService userService;
    private final WmsService wmsService;

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            onMessage(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            try {
                onCallbackQuery(update.getCallbackQuery());
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    public BotService(ReportService reportService, UserService userService, WmsService wmsService) {
        this.reportService = reportService;
        this.userService = userService;
        this.wmsService = wmsService;
        Platform.startup(()->{});
    }

    private void onMessage(Message message) {
        SendMessage response = new SendMessage();
        Long chatId = message.getChatId();
        User tgUser = message.getFrom();
        response.setChatId(String.valueOf(chatId));

        String command = message.getText();
        log.info("Processing command \"{}\"", command);
        switch (BotCommands.getFromString(command)) {
            case START -> checkAndRegisterUser(tgUser, response);
            case REPORTS -> addButtonReports(tgUser, response);
            case INFO -> response.setText("I`m a bot, created by ABM WMS");
            case SETTINGS -> response.setText("This is a settings");
            default -> response.setText("Please, choose a command from list");
        }
        sendResponse(response);
    }

    private void onCallbackQuery(CallbackQuery callback) throws TimeoutException {
        String[] data = callback.getData().split("\\|");
        ChartTypes chartType = ChartTypes.getType(data[0]); //get chart type
        String uri = data[1]; // get report uri
        Long chatId = callback.getFrom().getId();
        log.info("Processing callback data \"{}\", \"{}\"", chartType, uri);

        //removing button message
        int msgId = callback.getMessage().getMessageId();
        DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(chatId), msgId);
        try {
            execute(deleteMessage);
            log.info("Message \"{}\" removed successfully", chatId);
        } catch (TelegramApiException e) {
            log.error("Failed to remove message \"{}\" with error \"{}\" ", msgId, e.getMessage());
        }

        BotUser botUser = userService.getUserByTelegramId(String.valueOf(chatId)).get();
        String url = botUser.getOrganization().getUrl() + uri;
        ReportData report = wmsService.getReport(url);

        SendPhoto responsePhoto = new SendPhoto();
        responsePhoto.setChatId(String.valueOf(chatId));

        ChartJavaFXUtils chartJavaFXThread = new ChartJavaFXUtils(report, chartType);

        Platform.runLater(chartJavaFXThread);

        int count = 0;
        while (chartJavaFXThread.getFileName().equals("")) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
            if (count == 100) {
                throw new TimeoutException(String.format("Photo waiting time out. ChatId: %s, ChartType: %s", chatId,chartType));
            }
        }

        String fileName = chartJavaFXThread.getFileName();

        try {
            responsePhoto.setPhoto(new InputFile(new FileInputStream(fileName), report.getName()));
            log.info("Photo \"{}\" was sended to \"{}\" succesfully", fileName, chatId);
        } catch (FileNotFoundException e) {
            log.error("Failed to set photo \"{}\" to \"{}\" photoMessage with error \"{}\"", fileName, chatId, e.getMessage());
        }

        sendPhoto(responsePhoto);
        /*SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(chatId));
        response.setText(report.getName() + "\n");
        report.getData().stream().forEach(rr -> {
            response.setText(StringUtils.defaultIfEmpty(response.getText(), "") + String.format("%s: %s", rr.getParam(), rr.getValue()) + "\n");
        });
        sendResponse(response);
        */

    }

    private void sendResponse(SendMessage response) {
        try {
            log.info("Executing response \"{}\"", response);
            if (response.getText() != null)
                execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Failed to execute response \"{}\", error: \"{}\"", response.getText(), e.getMessage());
        }
    }

    private void sendPhoto(SendPhoto response) {
        try {
            if (response.getPhoto() != null) {
                execute(response);
                log.info("Sending a photo \"{}\"", response.getPhoto().getMediaName());
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Failed to send a photo \"{}\", error: \"{}\"", response.getPhoto().getMediaName(), e.getMessage());
        }
    }

    private void checkAndRegisterUser(User tgUser, SendMessage response) {
        String telegramId = String.valueOf(tgUser.getId());
        Optional<BotUser> userByTelegramId = userService.getUserByTelegramId(telegramId);
        if (userByTelegramId.isEmpty()) {
            BotUser botUser = new BotUser();
            botUser.setName(tgUser.getFirstName());
            botUser.setSurname(tgUser.getLastName());
            botUser.setTelegramId(telegramId);
            botUser.setLogin(tgUser.getUserName());
            //TODO Remade recognizing organization by Key
            Organization organization = new Organization();
            organization.setId(1L);
            organization.setName("TEST");
            organization.setUrl("http://localhost:8081/");
            botUser.setOrganization(organization);
            userService.saveUser(botUser);
            response.setText(String.format("Пользователь %s успешно добавлен", tgUser.getUserName()));
        } else {
            response.setText(String.format("С возвращением, %s", tgUser.getUserName()));
        }

    }

    private void addButtonReports(User tgUser, SendMessage response) {
        BotUser botUser = userService.getUserByTelegramId(String.valueOf(tgUser.getId())).get();
        List<Report> reports = reportService.getReportsByUser(botUser);
        List<List<InlineKeyboardButton>> reportButtons = new ArrayList<>();
        for (Report report : reports) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(report.getAlias());
            button.setCallbackData(String.format("%s|%s", report.getChartType(), report.getUri()));
            reportButtons.add(Collections.singletonList(button));
        }

        response.setText("Choose a report:");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(reportButtons);
        response.setReplyMarkup(markup);
    }

    @PostConstruct
    public void init() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
            log.info("Bot \"{}\" has been registered successfully", name);
        } catch (TelegramApiException e) {
            log.error("Failed to register bot: \"{}\"", e.getMessage());
        }
    }

}
