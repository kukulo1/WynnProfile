package com.kukulo1.json.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter @Setter
public class Player {
    private String username;
    private boolean online;
    private String server;
    private String activeCharacter;
    private String uuid;
    private String rank;
    private float playtime;
    private Guild guild;
    private Collection<Character> characterList;
    private Ranking ranking;
}