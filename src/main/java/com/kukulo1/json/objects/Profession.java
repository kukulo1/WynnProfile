package com.kukulo1.json.objects;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Profession {
    private int level;
    private int xpPercent;

    @Override
    public String toString() {
        return String.valueOf(level) + "lvl, " + String.valueOf(xpPercent) + "%";
    }
}
