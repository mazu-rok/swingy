package com.amazurok.swingy.model.characters;

import com.amazurok.swingy.anotation.ValidateType;
import com.amazurok.swingy.model.map.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class Person {

    @NotNull
    @Setter
    protected String name;

    @NotNull
    @Setter
    @ValidateType(types = {"Elf", "Knight", "Magician", "Orc"})
    protected String type;

    @Setter
    @Min(value = 1, message = "Level cannot be lower than 1.")
    protected int level;

    @Min(value = 0, message = "Experience cannot be lower than 0.")
    protected int experience;

    @Setter
    @Min(value = 0, message = "Attack cannot be lower than 0.")
    protected int attack;

    @Setter
    @Min(value = 0, message = "Defense cannot be lower than 0.")
    protected int defense;

    @Setter
    @Min(value = 0, message = "Hit point cannot be lower than 0.")
    protected int hp;

    @Setter
    @NotNull
    protected Coordinates coordinates;

    public void setExperience(int experience) {
        for (int i = level;; i++) {
            if (experience < (level*1000 + Math.pow(level - 1, 2) * 450)) {
                level = i - 1;
                break;
            }
        }
        this.experience = experience;
    }

    public void incrementExperience(int experience) {
        setExperience(this.experience + experience);
    }
}
