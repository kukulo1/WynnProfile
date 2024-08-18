package com.kukulo1.output;

import com.kukulo1.json.objects.Player;
import com.kukulo1.json.objects.Character;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Image.SCALE_DEFAULT;


public class ImageGenerator {
    private BufferedImage background;
    private BufferedImage skin = null;
    private Graphics2D g = null;
    private File output = null;

    private Font font52 = new Font("Sans-serif", Font.PLAIN, 52);
    private Font font82 = new Font("Sans-serif", Font.PLAIN, 82);
    private Font font85 = new Font("Sans-serif", Font.PLAIN, 85);

    private int baseUpperY = 337; //337
    private int baseDownY = 1130; //1145
    private int[] xCoordsNums = {
            0, // nick's profile xcoord
            3840-1791, //general stats
            3177, //pvp, dungeons and raids
            3840-1971, //gathering
            3027,  //crafting
            220, //class
            210,//x coord of invisible rectangle which contains rank in the center
            1383, //x coord of gamemode's rectangle
            220 //guild
    };
    private int[] yCoordsNums = {
            64, //nick's profile ycoord
            baseUpperY, baseUpperY+64, baseUpperY+64*2, baseUpperY+64*3, baseUpperY+64*4, baseUpperY+64*5, baseUpperY+64*6, baseUpperY+64*7, //upper blocks [1-8]
            baseDownY, baseDownY+64, baseDownY+64*2, baseDownY+64*3, baseDownY+64*4, baseDownY+64*5, baseDownY+64*6, baseDownY+64*7,//down blocks [9-16]
            1800, //class
            1450, //y coord of invisible rectangle which contains rank in the center
            864, //y coord of gamemode's rectangle
            1950 //guild
    };
    private final int skinCoordX = 250;
    private final int skinCoordY = 381;
    private final int skinScaleX = 732;
    private final int skinScaleY = 1042;

    public ImageGenerator() {
        try {
            background  = ImageIO.read(new File("src/main/resources/zalupaTemplate.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File createImage(Player player, Character mainChar) {
        try {
            g = background.createGraphics();
            skin = getSkin(player.getUuid());
            g.drawImage(skin.getScaledInstance(skinScaleX, skinScaleY, SCALE_DEFAULT), skinCoordX, skinCoordY, null);
            System.out.println("stat start");
            addStatisticsToImage(g, player, mainChar);
            System.out.println("stat end");
            output = new File("src/main/resources/output.png");
            ImageIO.write(background, "png", output);
            System.out.println("output is made");
            return output;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addStatisticsToImage(Graphics2D g, Player player, Character mainChar) {
        g.setFont(font85);
        drawString(String.format("%s's profile:", player.getUsername()), 0, 64, g);

        //font 52
        g.setFont(font52);
        drawString(mainChar.getPlaytime(), xCoordsNums[1], yCoordsNums[1], g);
        drawString(mainChar.getTotalLevel(), xCoordsNums[1], yCoordsNums[2], g);
        drawString(mainChar.getWars(), xCoordsNums[1], yCoordsNums[3], g);
        drawString(mainChar.getMobsKilled(), xCoordsNums[1], yCoordsNums[4], g);
        drawString(mainChar.getChestsFound(), xCoordsNums[1], yCoordsNums[5], g);
        drawString(mainChar.getItemsIdentified(), xCoordsNums[1], yCoordsNums[6], g);
        drawString(mainChar.getDeaths(), xCoordsNums[1], yCoordsNums[7], g);

        drawString(mainChar.getPvp().getKills(), xCoordsNums[2], yCoordsNums[1], g);
        drawString(mainChar.getPvp().getDeaths(), xCoordsNums[2], yCoordsNums[2], g);
        drawString(mainChar.getDungeons().getTotal(), xCoordsNums[2], yCoordsNums[5], g);
        try {
            drawString(mainChar.getRaids().getTotal(), xCoordsNums[2], yCoordsNums[8], g);
        } catch (Exception e) {
            drawString("N/A", xCoordsNums[2], yCoordsNums[8], g);
        }

        drawString(mainChar.getProfessions().getFishing().toString() + String.format(" (#%d)", player.getRanking().getFishingLevel()), xCoordsNums[3], yCoordsNums[9], g );
        drawString(mainChar.getProfessions().getWoodcutting().toString() + String.format(" (#%d)", player.getRanking().getWoodcuttingLevel()), xCoordsNums[3], yCoordsNums[10], g );
        drawString(mainChar.getProfessions().getMining().toString() + String.format(" (#%d)", player.getRanking().getMiningLevel()), xCoordsNums[3], yCoordsNums[11], g );
        drawString(mainChar.getProfessions().getFarming().toString() + String.format(" (#%d)", player.getRanking().getFarmingLevel()), xCoordsNums[3], yCoordsNums[12], g );

        drawString(mainChar.getProfessions().getScribing().toString() + String.format(" (#%d)", player.getRanking().getScribingLevel()), xCoordsNums[4], yCoordsNums[9], g );
        drawString(mainChar.getProfessions().getJeweling().toString() + String.format(" (#%d)", player.getRanking().getJewelingLevel()), xCoordsNums[4], yCoordsNums[10], g );
        drawString(mainChar.getProfessions().getAlchemism().toString() + String.format(" (#%d)", player.getRanking().getAlchemismLevel()), xCoordsNums[4], yCoordsNums[11], g );
        drawString(mainChar.getProfessions().getCooking().toString() + String.format(" (#%d)", player.getRanking().getCookingLevel()), xCoordsNums[4], yCoordsNums[12], g );
        drawString(mainChar.getProfessions().getWeaponsmithing().toString() + String.format(" (#%d)", player.getRanking().getWeaponsmithingLevel()), xCoordsNums[4], yCoordsNums[13], g );
        drawString(mainChar.getProfessions().getTailoring().toString() + String.format(" (#%d)", player.getRanking().getTailoringLevel()), xCoordsNums[4], yCoordsNums[14], g );
        drawString(mainChar.getProfessions().getWoodworking().toString() + String.format(" (#%d)", player.getRanking().getWoodworkingLevel()), xCoordsNums[4], yCoordsNums[15], g );
        drawString(mainChar.getProfessions().getArmouring().toString() + String.format(" (#%d)", player.getRanking().getArmouringLevel()), xCoordsNums[4], yCoordsNums[16], g );
        if (!mainChar.getGamemode().isEmpty()) {
            List<String> gamemodesList = new ArrayList<String>(mainChar.getGamemode().stream().toList());
            drawCenteredString(gamemodesList.toString()
                    .replace("[", "")
                    .replace("]", ""), new Rectangle(
                    xCoordsNums[7], yCoordsNums[19],
                    2241, 126), g, font52); //gamemodes
        } else {
            drawCenteredString("Just a regular character :)", new Rectangle(
                    xCoordsNums[7], yCoordsNums[19],
                    2241, 126), g, font52);
        }
        //font 82
        g.setFont(font82);
        drawString(mainChar.getClassAndXpInfo(), xCoordsNums[5], yCoordsNums[17], g); //class

        drawCenteredString(player.getRank(), new Rectangle(
                xCoordsNums[6], yCoordsNums[18],
                807, 144), g, font82); //player rank
        drawString(player.getGuild().formStatistics(), xCoordsNums[8], yCoordsNums[20], g); //guild

    }
    private BufferedImage getSkin(String uuid) {
        String s = String.format("https://visage.surgeplay.com/full/448/%s", uuid);
        try {
            URL url = new URL(s);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "image/png");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            System.out.println(connection.getResponseMessage());
            connection.connect();
            return ImageIO.read(connection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private void drawString(String str, int x, int y, Graphics2D g) {
        g.drawString(str, x, y);
    }
    private void drawString(int number, int x, int y, Graphics2D g) {
        g.drawString(String.valueOf(number), x, y);
    }
    private void drawString(double number, int x, int y, Graphics2D g) {
        g.drawString(String.valueOf(number), x, y);
    }
    private void drawString(Double number, int x, int y, Graphics2D g) {
        g.drawString(String.valueOf(number), x, y);
    }
    public void drawCenteredString(String text, Rectangle rect, Graphics2D g, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }


}
