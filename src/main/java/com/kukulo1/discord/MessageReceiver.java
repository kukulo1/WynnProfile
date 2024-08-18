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
    private ImageGenerator ig = new ImageGenerator();
    private Parser parser = new Parser();
    private FileUpload f = null;
    private Player player = null;
    private Character mainChar = null;
    private Gson gson = new Gson();
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        long startTime = System.currentTimeMillis();
        if (event.getAuthor().isBot()) return;

        Message msg = event.getMessage();
        String content = msg.getContentRaw();

        if (content.equals("!profile")) {
            String nick = "yksti";
            System.out.println("1");
            player = parser.getPlayerData(nick);
            System.out.println("12");
            if (!(player.getActiveCharacter() == null || player.getActiveCharacter().isEmpty())) {
                mainChar = parser.getCharacter(nick, player.getActiveCharacter());
                System.out.println("123");
                f = FileUpload.fromData(ig.createImage(player, mainChar)); //output image
                System.out.println("1234");
                event.getChannel().sendFiles(f).queue();
            }
            //player.setCharacterList(Parser.getCharactersList(nick).values());


        }
        long endTime = System.currentTimeMillis();
        System.out.println("full time = " + (endTime - startTime));
    }
}
