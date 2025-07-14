package com.javarush.telegram;

import com.javarush.telegram.ChatGPTService;
import com.javarush.telegram.DialogMode;
import com.javarush.telegram.MultiSessionTelegramBot;
import com.javarush.telegram.UserInfo;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.Objects;

public class TinderBoltApp extends MultiSessionTelegramBot {
    public static final String TELEGRAM_BOT_NAME = "mayflower2_bot"; // добавь имя бота в кавычках
    public static final String TELEGRAM_BOT_TOKEN = "7787520600:AAHmKL9xo7rz1cVshQNDb7pMUhm_V8oaNKo"; // добавь токен бота в кавычках
    public static final String OPEN_AI_TOKEN = "chat-gpt-token"; // добавь токен ChatGPT в кавычках

    public TinderBoltApp() {
        super(TELEGRAM_BOT_NAME, TELEGRAM_BOT_TOKEN);
    }

    @Override
    public void onUpdateEventReceived(Update update) {
        //основной функционал бота будем писать здесь
        if (Objects.equals(getMessageText(), "/start")) {
            sendPhotoMessage("main");
            sendTextMessage("*Привет!*");
            sendTextMessage(loadMessage("main"));
            return;
        }
        sendTextMessage("*Привет!*");
        sendTextMessage("_Вы написали: " + getMessageText() + "_");
        sendTextButtonsMessage("Выберите режим работы:",
                "Старт", "start",
                            "Стоп", "stop"
        );
    }

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new TinderBoltApp());
    }
}
