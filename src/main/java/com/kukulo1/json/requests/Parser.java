package com.kukulo1.json.requests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kukulo1.json.objects.Character;
import com.kukulo1.json.objects.Player;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Parser {
    private Gson gson = new Gson();
    private String wynnApiEndpoint = "https://api.wynncraft.com/v3/";

    public Player getPlayerData(String UUID) {
        try {
            HttpURLConnection c = (HttpURLConnection) new URL(
                    String.format(wynnApiEndpoint + "player/%s", UUID)
            ).openConnection();
            return gson.fromJson(new InputStreamReader(c.getInputStream()), Player.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//PS до лучших времен
//    public Map<String, Character> getCharactersList(String username) {
//        TypeToken<Map<String, Character>> mapType = new TypeToken<Map<String, Character>>(){};
//        try {
//            HttpURLConnection c = (HttpURLConnection) new URL(
//                    String.format(wynnApiEndpoint + "player/%s/characters", username)
//            ).openConnection();
//            return gson.fromJson(new InputStreamReader(c.getInputStream()), Player.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }

    public Character getCharacter(String UUID, String characterUUID) {
        try {
            HttpURLConnection c = (HttpURLConnection) new URL(
                    String.format(wynnApiEndpoint + "player/%s/characters/%s", UUID, characterUUID)).openConnection();
            return gson.fromJson(new InputStreamReader(c.getInputStream()), Character.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
