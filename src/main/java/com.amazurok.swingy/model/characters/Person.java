package com.amazurok.swingy.model.characters;

import com.amazurok.swingy.anotation.ValidateType;
import com.amazurok.swingy.model.artifacts.Armor;
import com.amazurok.swingy.model.artifacts.Helm;
import com.amazurok.swingy.model.artifacts.Weapon;
import com.amazurok.swingy.model.map.Coordinates;
import lombok.*;
import org.mongodb.morphia.annotations.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity("heroes")
@Indexes(@Index(fields = { @Field("name") }, options = @IndexOptions(unique = true)))
public class Person {

    @NonNull
    @Id
    UUID id;

    @NonNull
    @Setter
    protected String name;

    @NonNull
    @Setter
    @ValidateType(types = {"Elf", "Knight", "Magician", "Orc"})
    protected String type;

    @NonNull
    @Setter
    @Min(value = 1, message = "Level cannot be lower than 1.")
    @Max(value = Integer.MAX_VALUE, message = "Level is too big")
    protected Integer level;

    @NonNull
    @Min(value = 0, message = "Experience cannot be lower than 0.")
    @Max(value = Integer.MAX_VALUE, message = "Experience is too big")
    protected Integer experience;

    @NonNull
    @Setter
    @Min(value = 0, message = "Attack cannot be lower than 0.")
    @Max(value = Integer.MAX_VALUE, message = "Attack is too big")
    protected Integer attack;

    @NonNull
    @Setter
    @Min(value = 0, message = "Defense cannot be lower than 0.")
    @Max(value = Integer.MAX_VALUE, message = "Defense is too big")
    protected Integer defense;

    @NonNull
    @Setter
    @Min(value = 0, message = "Hit point cannot be lower than 0.")
    @Max(value = Integer.MAX_VALUE, message = "HP is too big")
    protected Integer hp;

    @Setter
    @NonNull
    protected Coordinates coordinates;

    protected Weapon weapon = new Weapon(0);

    protected Armor armor = new Armor(0);

    protected Helm helm = new Helm(0);

    protected int DEFAULT_ATTACK;
    protected int DEFAULT_DEFENSE;
    protected int DEFAULT_HP;

    public void setExperience(int experience) {
        for (int i = level + 1;; i++) {
            if (experience < (i*1000 + Math.pow(i - 1, 2) * 450)) {
                level = i - 1;
                break;
            }
        }
        this.experience = experience;
    }

    public void incrementExperience(int experience) {
        setExperience(this.experience + experience);
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

    public void punch(Person person) {
        Integer attack = this.attack;
        attack = (int)(attack - (attack * (double)person.getDefense() / 100));
        person.setHp(person.getHp() - attack);
        person.setDefense(person.getDefense() - (int)(person.getDefense() * (double)attack / 200));
    }
}
