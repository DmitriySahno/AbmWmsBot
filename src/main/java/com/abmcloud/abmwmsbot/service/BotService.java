package com.abmcloud.abmwmsbot.service;

import com.abmcloud.abmwmsbot.commands.ReportCommand;
import com.abmcloud.abmwmsbot.enums.BotCommands;
import com.abmcloud.abmwmsbot.model.BotUser;
import com.abmcloud.abmwmsbot.model.Organization;
import com.abmcloud.abmwmsbot.model.Report;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class BotService extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String name;
    @Value("${bot.token}")
    private String token;

    private final ReportCommand reportCommand;
    private final ReportService reportService;
    private final UserService userService;

    public BotService(ReportService reportService, UserService userService) {
        this.reportCommand = new ReportCommand(this);
        this.reportService = reportService;
        this.userService = userService;
    }

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
            onCallbackQuery(update.getCallbackQuery());
        }
    }

    private void onMessage(Message message) {
        SendMessage response = new SendMessage();
        Long chatId = message.getChatId();
        User tgUser = message.getFrom();
        response.setChatId(String.valueOf(chatId));

        String command = message.getText();
        log.info("Processing command \"{}\"", command);
        switch (BotCommands.getFromString(command)) {
            case START:
                checkAndRegisterUser(tgUser, response);
                break;
            case REPORTS:
                addButtonReports(tgUser, response);
                break;
            case INFO:
                response.setText("Я - бот, созданный ABM WMS");
                break;
            case SETTINGS:
                response.setText("Вы вошли в настройки");
                break;
            case ADMIN:
                response.setText("Вы вошли в панель администратора");
                break;
            default:
                response.setText("Выберите команду из списка");
        }
        sendResponse(response);
    }

    private void onCallbackQuery(CallbackQuery callback) {
        String data = callback.getData();
        Long chatId = callback.getFrom().getId();
        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(chatId));
        log.info("Processing callback data \"{}\"", data);
        if (data.startsWith("Отчеты_"))


            sendResponse(response);

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
            button.setCallbackData(report.getName());
            reportButtons.add(Collections.singletonList(button));
        }

        response.setText("Выберите отчет");

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
