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
public class Knight extends Person {
    private static int DEFAULT_ATTACK = 60;
    private static int DEFAULT_DEFENSE = 60;
    private static int DEFAULT_HP = 500;

    private Weapon weapon;

    private Armor armor;

    private Helm helm;

    public Knight(String name, int level, int attack, int defense, int hp, Coordinates coordinates) {
        super(UUID.randomUUID(), name, Knight.class.getSimpleName(), level, (int)(level*1000 + Math.pow(level - 1, 2) * 450), attack, defense, hp, coordinates);
    }

    public Knight(String name, Coordinates coordinates)
    {
        super(UUID.randomUUID(), name, Knight.class.getSimpleName(), 1, 0, DEFAULT_ATTACK, DEFAULT_DEFENSE, DEFAULT_HP, coordinates);
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
        this.attack = (int)(DEFAULT_ATTACK + ((double)DEFAULT_ATTACK * (weapon.getPower() / 100)));
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
        this.defense = (int)(DEFAULT_DEFENSE + ((double)DEFAULT_DEFENSE * (armor.getPower() / 100)));
    }

    public void setHelm(Helm helm) {
        this.helm = helm;
        this.hp = (int)(DEFAULT_HP + ((double)DEFAULT_HP * (helm.getPower() / 100)));
    }

    public void setArtifact(Object artifact) {
        if (artifact instanceof Armor) {
            setArmor((Armor) artifact);
        } else if (artifact instanceof Helm) {
            setHelm((Helm) artifact);
        } else {
            setWeapon((Weapon) artifact);
        }
    }
}
