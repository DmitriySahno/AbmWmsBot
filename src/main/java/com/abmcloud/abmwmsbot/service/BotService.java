package com.abmcloud.abmwmsbot.service;

import com.abmcloud.abmwmsbot.enums.BotCommands;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class BotService extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String name;
    @Value("${bot.token}")
    private String token;

    private final UserService userService;

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
        Message message = update.getMessage();
        String command = message.getText();
        Long chatId = message.getChatId();

        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(chatId));

        log.info("Processing command \"{}\"", command);
        switch (BotCommands.getFromString(command)) {
            case START:
                response.setText("Введите ключ доступа, выданный технической поддержкой ABM WMS");
                break;
            case REPORTS:
//                response.setText("Выберите отчет");
                sendReports(chatId);
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

        try {
            log.info("Executing response \"{}\"", response);
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Failed to execute response \"{}\", error: \"{}\"", command, e);
        }

        log.info("End of processing command \"{}\"", command);

    }



    public void onCallbackQuery(CallbackQuery callbackQuery) {

    }




}
