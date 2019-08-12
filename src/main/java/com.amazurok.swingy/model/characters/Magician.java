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
public class Magician extends Person {
    private static int DEFAULT_ATTACK = 90;
    private static int DEFAULT_DEFENSE = 15;
    private static int DEFAULT_HP = 400;

    @NonNull
    private Weapon weapon;

    @NonNull
    private Armor armor;

    @NonNull
    private Helm helm;

    public Magician(String name, int level, int attack, int defense, int hp, Coordinates coordinates) {
        super(UUID.randomUUID(), name, Magician.class.getSimpleName(), level, (int)(level*1000 + Math.pow(level - 1, 2) * 450), attack, defense, hp, coordinates);
    }

    public Magician(String name, Coordinates coordinates)
    {
        super(UUID.randomUUID(), name, Magician.class.getSimpleName(), 1, 0, DEFAULT_ATTACK, DEFAULT_DEFENSE, DEFAULT_HP, coordinates);
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
        this.attack = DEFAULT_ATTACK + (DEFAULT_ATTACK * (weapon.getPower() / 100));
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
        this.defense = DEFAULT_DEFENSE + (DEFAULT_DEFENSE * (armor.getPower() / 100));
    }

    public void setHelm(Helm helm) {
        this.helm = helm;
        this.hp = DEFAULT_HP + (DEFAULT_HP * (helm.getPower() / 100));
    }

}
