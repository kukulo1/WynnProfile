package com.kukulo1.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TelegramUserWithWynnProfile {
    @Id
    private Long senderId;
    private String mcUUID;
}
