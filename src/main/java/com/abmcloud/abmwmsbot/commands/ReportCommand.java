package com.abmcloud.abmwmsbot.commands;

import com.abmcloud.abmwmsbot.service.BotService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ReportCommand {

    private final BotService botService;

    public void onCallbackQuery(CallbackQuery callbackQuery) {

    }

    public void sendReports(User user) {
        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(user.getId()));
        response.setText("Выберите отчет");

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("Загруженность склада");
        button1.setCallbackData("Отчеты_ЗагруженностьСклада");

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("Товарооборот");
        button2.setCallbackData("Отчеты_Товарооборот");

        InlineKeyboardButton button3 = new InlineKeyboardButton();
        button3.setText("Производительность склада");
        button3.setCallbackData("Отчеты_ПроизводительностьСклада");

        InlineKeyboardButton button4 = new InlineKeyboardButton();
        button4.setText("Неликвиды");
        button4.setCallbackData("Отчеты_Неликвиды");

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
            botService.execute(response);
        } catch (TelegramApiException e) {
            log.error("Failed to execute report message with error \"{}\"", e.getMessage());
        }
    }







}