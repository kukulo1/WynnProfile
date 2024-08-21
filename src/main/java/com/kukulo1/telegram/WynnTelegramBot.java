package com.kukulo1.telegram;

import com.kukulo1.json.requests.MojangParser;
import com.kukulo1.jpa.TelegramUserWithWynnProfile;
import com.kukulo1.jpa.TgUserRepository;
import com.kukulo1.json.objects.Character;
import com.kukulo1.json.objects.Player;
import com.kukulo1.json.requests.Parser;
import com.kukulo1.output.ImageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Logger;

@Component
public class WynnTelegramBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    private String message;
    private Long chatId;
    private Long senderId;

    private final TelegramClient tgClient;
    private final Properties appProp;
    private TelegramUserWithWynnProfile currentUser;
    @Autowired
    private TgUserRepository repository;
    private final Parser parser;
    private final ImageGenerator imageGenerator;

    private Player currentPlayer;
    private Character currentPlayersCharacter;

    public WynnTelegramBot() {
        appProp = new Properties();
        try {
            appProp.load(new FileInputStream("src/main/resources/app.properties"));
        } catch (Exception e) {
            Logger.getLogger("logger").warning("Couldn't load app.properties!");
            //todo suspicious line up there
        }
        tgClient = new OkHttpTelegramClient(getBotToken());
        parser = new Parser();
        imageGenerator = new ImageGenerator();

    }

    @Override
    public void consume(Update update) {
        if (!update.hasMessage() && !update.getMessage().hasText() && !(update.getMessage().getText().charAt(0) == '!')) return;

        message = update.getMessage().getText();
        chatId = update.getMessage().getChatId();
        //command: !setprofile <wynncraft nickname>
        if (message.startsWith("!setprofile ")) {
            //todo
            // if multiple nicknames make choice "please select your account"
            try {
                currentUser = new TelegramUserWithWynnProfile(update.getMessage().getFrom().getId(), MojangParser.getUUID(message.split(" ")[1]));
                repository.save(currentUser);
            } catch (NullPointerException e) {
                try {
                    tgClient.execute(SendMessage.builder().chatId(chatId).text("Couldn't find any player with nick " + message.split(" ")[1]).build());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
        if (message.equalsIgnoreCase("!profile")) {
            //todo image generation and
            //imageGenerator.createImage(currentPlayer, currentPlayersCharacter)
            currentPlayer = parser.getPlayerData(repository.findById(update.getMessage().getFrom().getId()).get().getMcUUID());
            currentPlayersCharacter = parser.getCharacter(
                    repository.findById(update.getMessage().getFrom().getId()).get().getMcUUID(),
                    currentPlayer.getActiveCharacter()
            );
            try {
                SendPhoto msg = SendPhoto
                        .builder()
                        .chatId(chatId)
                        .photo(new InputFile(imageGenerator.createImage(currentPlayer, currentPlayersCharacter)))
                        .build();
                tgClient.execute(msg);
            } catch (TelegramApiException e) {
                Logger.getAnonymousLogger().warning("Something went wrong while sending profile's info image!");
            }
        }
        if (message.equalsIgnoreCase("!help")) {
            try {
                tgClient.execute(SendMessage.builder().chatId(chatId).text("""
                        Usage:\s
                        !setprofile <nickname>\s
                        !profile\s
                        Пожалуйста не злитесь, что я так медленно работаю. Мой создатель - Еблан Безрукий""").build());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (message.equalsIgnoreCase("/start")) {
            try {
                tgClient.execute(SendMessage.builder().chatId(chatId).text("""
                        Приветик, я бот для показа статистики на сервере Wynncraft!
                        Usage:\s
                        !setprofile <nickname>\s
                        !profile\s
                        Пожалуйста не злитесь, что я так медленно работаю. Мой создатель - Еблан Безрукий""").build());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String getBotToken() {
        return appProp.getProperty("telegram_api_token");
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }
}
