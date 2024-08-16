package com.kukulo1.json.objects;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Guild {
    // Геттеры и сеттеры
    private String name;
    private String prefix;
    private String rank;
    private String rankStars;

    public String formStatistics() {
        return String.format("%s of [%s]%s", rank, prefix, name);
    }

}
