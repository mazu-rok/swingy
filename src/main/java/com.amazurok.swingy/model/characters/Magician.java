package com.amazurok.swingy.model.characters;

import com.amazurok.swingy.model.artifacts.Armor;
import com.amazurok.swingy.model.artifacts.Helm;
import com.amazurok.swingy.model.artifacts.Weapon;
import com.amazurok.swingy.model.map.Coordinates;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Magician extends Person {

    @NonNull
    private Weapon weapon;

    @NonNull
    private Armor armor;

    @NonNull
    private Helm helm;

    public Magician(String name, int level, int attack, int defense, int hp, Coordinates coordinates) {
        super(name, Magician.class.getSimpleName(), level, (int)(level*1000 + Math.pow(level - 1, 2) * 450), attack, defense, hp, coordinates);
    }

    public Magician(String name, Coordinates coordinates)
    {
        super(name, Magician.class.getSimpleName(), 1, 0, 90, 15, 400, coordinates);
    }
}
