package com.kukulo1.json.requests;

import com.google.gson.Gson;
import com.kukulo1.json.objects.MojangProfile;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MojangParser {
    private static String mojangApiEndpoint = "https://api.mojang.com/users/profiles/minecraft/";

    public static String getUUID(String nickname) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(mojangApiEndpoint + nickname).openConnection();
            return new Gson().fromJson(new InputStreamReader(connection.getInputStream()), MojangProfile.class).id.replaceFirst(
                    "([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5"
            );
        } catch(Exception e) {
            throw new NullPointerException();
        }
    }
}