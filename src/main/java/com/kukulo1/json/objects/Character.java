package com.kukulo1.json.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter @Setter
public class Character {
    private String type;
    private String nickname;
    private int level;
    private int xp;
    private int xpPercent;
    private int totalLevel;
    private int wars;
    private Double playtime;
    private int mobsKilled;
    private int chestsFound;
    private int itemsIdentified;
    private int blocksWalked;
    private int logins;
    private int deaths;
    private int discoveries;
    private Pvp pvp;
    private Dungeon dungeons;
    private Raid raids;
    private Collection<String> gamemode;
    private SkillPoints skillPoints;
    private Professions professions;

    public String getClassAndXpInfo() {
        return type + " - " + level + "lvl, " + xpPercent + "%;";
    }
}