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
    private String rankBadge;
    private String legacyRankColourMain;
    private String legacyRankColourSub;
    private String shortenedRank;
    private String supportRank;
    private boolean veteran;
    private String firstJoin;
    private String lastJoin;
    private float playtime;
    private Guild guild;
    private Collection<Character> characterList;
    private Ranking ranking;
}