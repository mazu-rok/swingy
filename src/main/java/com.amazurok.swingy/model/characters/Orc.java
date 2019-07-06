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
public class Orc extends Person {

    @NonNull
    private Weapon weapon;

    @NonNull
    private Armor armor;

    @NonNull
    private Helm helm;

    public Orc(String name, int level, int experience, int attack, int defense, int hp, Coordinates coordinates) {
        super(name, Orc.class.getSimpleName(), level, experience, attack, defense, hp, coordinates);
    }

    public Orc(String name, Coordinates coordinates)
    {
        super(name, Orc.class.getSimpleName(), 1, 0, 40, 50, 900, coordinates);
    }
}
