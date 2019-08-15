package com.amazurok.swingy.model.characters;

import com.amazurok.swingy.model.artifacts.Armor;
import com.amazurok.swingy.model.artifacts.Helm;
import com.amazurok.swingy.model.artifacts.Weapon;
import com.amazurok.swingy.model.map.Coordinates;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class Elf extends Person {
    public Elf(String name, int level, int attack, int defense, int hp, Coordinates coordinates) {
        super(UUID.randomUUID(), name, Elf.class.getSimpleName(), level, (int)(level*1000 + Math.pow(level - 1, 2) * 450), attack, defense, hp, coordinates);
        DEFAULT_ATTACK = attack;
        DEFAULT_DEFENSE = defense;
        DEFAULT_HP = hp;
    }

    public Elf(String name, Coordinates coordinates)
    {
        super(UUID.randomUUID(), name, Elf.class.getSimpleName(), 1, 0, 70, 25, 500 , coordinates);
        DEFAULT_ATTACK = 70;
        DEFAULT_DEFENSE = 25;
        DEFAULT_HP = 500;
    }
}
