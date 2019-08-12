package com.amazurok.swingy.model.characters;

import com.amazurok.swingy.model.map.Coordinates;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
public class Enemy extends Person {
    private static int DEFAULT_ATTACK = 40;
    private static int DEFAULT_DEFENSE = 15;
    private static int DEFAULT_HP = 300;

    public Enemy(String name, int level, Coordinates coordinates) {
        super(UUID.randomUUID(), name, Enemy.class.getSimpleName(), level, (int)(level*1000 + Math.pow(level - 1, 2) * 450),
                DEFAULT_ATTACK + (DEFAULT_ATTACK * (level / 100)), DEFAULT_DEFENSE + (DEFAULT_DEFENSE * (level / 100)),
                DEFAULT_HP + (DEFAULT_HP * (level / 100)), coordinates);
    }

    public Enemy(String name, Coordinates coordinates)
    {
        super(UUID.randomUUID(), name, Enemy.class.getSimpleName(), 1, 0, DEFAULT_ATTACK, DEFAULT_DEFENSE, DEFAULT_HP , coordinates);
    }
}
