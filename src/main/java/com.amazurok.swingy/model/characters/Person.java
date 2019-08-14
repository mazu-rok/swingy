package com.amazurok.swingy.model.characters;

import com.amazurok.swingy.anotation.ValidateType;
import com.amazurok.swingy.model.artifacts.Armor;
import com.amazurok.swingy.model.artifacts.Helm;
import com.amazurok.swingy.model.artifacts.Weapon;
import com.amazurok.swingy.model.map.Coordinates;
import lombok.*;
import org.mongodb.morphia.annotations.*;

import javax.validation.constraints.Min;
import java.util.UUID;

@Getter
@AllArgsConstructor
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
    protected Integer level;

    @NonNull
    @Min(value = 0, message = "Experience cannot be lower than 0.")
    protected Integer experience;

    @NonNull
    @Setter
    @Min(value = 0, message = "Attack cannot be lower than 0.")
    protected Integer attack;

    @NonNull
    @Setter
    @Min(value = 0, message = "Defense cannot be lower than 0.")
    protected Integer defense;

    @NonNull
    @Setter
    @Min(value = 0, message = "Hit point cannot be lower than 0.")
    protected Integer hp;

    @Setter
    @NonNull
    protected Coordinates coordinates;

    public void setExperience(int experience) {
        for (int i = level;; i++) {
            if (experience < (i*1000 + Math.pow(i - 1, 2) * 450)) {
                level = i - 1;
                break;
            }
        }
        this.experience = experience;
    }

    public void punch(Person person) {
        Integer attack = this.attack;
        attack = (int)(attack - (attack * (double)person.getDefense() / 100));
        person.setHp(person.getHp() - attack);
        person.setDefense(person.getDefense() - (int)(person.getDefense() * (double)attack / 200));
    }

    public void incrementExperience(int experience) {
        setExperience(this.experience + experience);
    }
}
