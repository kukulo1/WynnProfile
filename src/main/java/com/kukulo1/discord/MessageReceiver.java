package com.kukulo1.discord;

import com.google.gson.Gson;
import com.kukulo1.json.objects.Player;
import com.kukulo1.json.objects.Character;
import com.kukulo1.json.requests.Parser;
import com.kukulo1.output.ImageGenerator;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;

import java.awt.image.BufferedImage;
import java.io.File;

public class MessageReceiver extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        Message msg = event.getMessage();
        String content = msg.getContentRaw();

        if (content.equals("!profile")) {
            String nick = "yksti";
            Gson gson = new Gson();
            System.out.println("1");
            Player player = gson.fromJson(Parser.getPlayerData(nick, false), Player.class);
            System.out.println("12");
            if (!(player.getActiveCharacter() == null || player.getActiveCharacter().isEmpty())) {
                ImageGenerator ig = new ImageGenerator();
                Character mainChar = Parser.getCharacter(nick, player.getActiveCharacter());
                System.out.println("123");
                File outputImage = ig.createImage(player, mainChar);
                System.out.println("1234");
                FileUpload f = FileUpload.fromData(outputImage);
                event.getChannel().sendFiles(f).queue();
            }
            //player.setCharacterList(Parser.getCharactersList(nick).values());


        }
    }
}
