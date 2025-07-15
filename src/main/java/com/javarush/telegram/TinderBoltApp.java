package com.javarush.telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Objects;

public class TinderBoltApp extends MultiSessionTelegramBot {
    public static final String TELEGRAM_BOT_NAME = "mayflower2_bot"; // добавь имя бота в кавычках
    public static final String TELEGRAM_BOT_TOKEN = "7787520600:AAHmKL9xo7rz1cVshQNDb7pMUhm_V8oaNKo"; // добавь токен бота в кавычках
    public static final String OPEN_AI_TOKEN = "gpt:AUFMFhaIxsLXr0B-4fw5Ik4FWbxdKoFXzX3lK0-eGXhH5LQrJfGAQzs5F44VgDQ4LEuCL6c6A4JFkblB3TJujlqox1oLzy0gB77UvCxpwpxiYZ5mnUTAaIold1B09jsDr0EPRetAXxuWnkyDagxA9OMCJZ__"; // добавь токен ChatGPT в кавычках

    private final ChatGPTService chatGPT = new ChatGPTService(OPEN_AI_TOKEN);
    private DialogMode mode = DialogMode.MAIN;

    public TinderBoltApp() {
        super(TELEGRAM_BOT_NAME, TELEGRAM_BOT_TOKEN);
    }

    @Override
    public void onUpdateEventReceived(Update update) {
        //основной функционал бота будем писать здесь
        String messageText = getMessageText();
        if (Objects.equals(messageText, "/start")) {
            mode = DialogMode.MAIN;
            sendPhotoMessage("main");
            sendTextMessage("*Привет!*");
            sendTextMessage(loadMessage("main"));

            showMainMenu("Начало", "/start",
                    "Генерация Tinder-профля", "/profile",
                    "Сообщение для знакомства", "/opener ",
                    "Переписка от вашего имени", "/message ",
                    "Переписка со звездами", "/date ",
                    "Задать вопрос чату GPT", "/gpt "
                    );
            return;
        }
        if (Objects.equals(messageText, "/gpt")) {
            mode = DialogMode.GPT;
            sendPhotoMessage("gpt");
            sendTextMessage(loadMessage("gpt"));
        }

        if (mode == DialogMode.GPT) {
            String answer = chatGPT.sendMessage(loadPrompt("gpt"), messageText);
            sendTextMessage(answer);
            return;
        }

        sendTextMessage("*Привет!*");
        sendTextMessage("_Вы написали: " + messageText + "_");
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
