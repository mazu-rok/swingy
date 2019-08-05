package com.amazurok.swingy.model.characters;

import com.amazurok.swingy.model.map.Coordinates;

public class Enemy extends Person {
    public Enemy(String name, int level, int attack, int defense, int hp, Coordinates coordinates) {
        super(name, Enemy.class.getSimpleName(), level, (int)(level*1000 + Math.pow(level - 1, 2) * 450), attack, defense, hp, coordinates);
    }

    public Enemy(String name, Coordinates coordinates)
    {
        super(name, Enemy.class.getSimpleName(), 1, 0, 40, 15, 300 , coordinates);
    }
}
