package com.kukulo1;

import com.kukulo1.discord.MessageReceiver;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException {
    //todo handle names duplication while looking for a player
        Properties appProperties = new Properties();
        appProperties.load(new FileInputStream("src/main/resources/app.properties"));
        JDA api = JDABuilder.createDefault(appProperties.getProperty("discord_api_token"), GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT).build();
        api.addEventListener(new MessageReceiver());
    }
}
