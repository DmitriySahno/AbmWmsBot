package com.abmcloud.abmwmsbot.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class ReportCommand {

    public void onCallbackQuery(CallbackQuery callbackQuery) {

    }


    private void sendReports(User user) {
        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(user.getId()));
        response.setText("Выберите отчет");

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Загруженность склада");
        button1.setCallbackData("Загруженность склада");

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Товарооборот");
        button2.setCallbackData("Товарооборот");

        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("Производительность склада");
        button3.setCallbackData("Производительность склада");

        InlineKeyboardButton button4 = new InlineKeyboardButton();
        button4.setText("Неликвиды");
        button4.setCallbackData("Неликвиды");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                Arrays.asList(
                        List.of(button1),
                        List.of(button2),
                        List.of(button3),
                        List.of(button4)
                ));

        response.setReplyMarkup(markup);
        try {
            log.info("Executing report message \"{}\"", response);
            execute(response);
        } catch (TelegramApiException e) {
            log.error("Failed to execute report message with error \"{}\"", e);
        }
    }







}