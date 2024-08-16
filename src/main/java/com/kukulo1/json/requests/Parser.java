package com.kukulo1.json.requests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kukulo1.json.objects.Character;

import java.util.Map;

public class Parser {
    public static String getPlayerData(String username, boolean full) {
        if (full) return RequestSender.sendRequest(String.format("https://api.wynncraft.com/v3/player/%s?fullResult", username), 1000);
        else return RequestSender.sendRequest(String.format("https://api.wynncraft.com/v3/player/%s", username), 1000);
    }

    public static Map<String, Character> getCharactersList(String username) {
        String json = RequestSender.sendRequest(String.format("https://api.wynncraft.com/v3/player/%s/characters", username), 1000);
        TypeToken<Map<String, Character>> mapType = new TypeToken<Map<String, Character>>(){};
        Gson gson = new Gson();
        return gson.fromJson(json, mapType);
    }

    public static Character getCharacter(String username, String characterUUID) {
        String json = RequestSender.sendRequest(String.format(String.format("https://api.wynncraft.com/v3/player/%s/characters/%s", username, characterUUID)), 1000);
        Gson gson = new Gson();
        TypeToken<Character> typeToken = new TypeToken<Character>(){};
        return gson.fromJson(json, typeToken);
    }
}
