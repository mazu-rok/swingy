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
    private static int DEFAULT_ATTACK = 70;
    private static int DEFAULT_DEFENSE = 25;
    private static int DEFAULT_HP = 500;

    @NonNull
    private Weapon weapon;

    @NonNull
    private Armor armor;

    @NonNull
    private Helm helm;

    public Elf(String name, int level, int attack, int defense, int hp, Coordinates coordinates) {
        super(UUID.randomUUID(), name, Elf.class.getSimpleName(), level, (int)(level*1000 + Math.pow(level - 1, 2) * 450), attack, defense, hp, coordinates);
    }

    public Elf(String name, Coordinates coordinates)
    {
        super(UUID.randomUUID(), name, Elf.class.getSimpleName(), 1, 0, DEFAULT_ATTACK, DEFAULT_DEFENSE, DEFAULT_HP , coordinates);
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
