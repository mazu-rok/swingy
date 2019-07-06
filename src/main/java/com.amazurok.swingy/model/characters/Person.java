package com.amazurok.swingy.model.characters;

import com.amazurok.swingy.anotation.ValidateType;
import com.amazurok.swingy.model.map.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class Person {

    @NotNull
    protected String name;

    @NotNull
    @ValidateType(types = {"Elf", "Knight", "Magician", "Orc"})
    protected String type;

    @Min(value = 1, message = "Level cannot be lower than 1.")
    protected int level;

    @Min(value = 0, message = "Experience cannot be lower than 0.")
    protected int experience;

    @Min(value = 0, message = "Attack cannot be lower than 0.")
    protected int attack;

    @Min(value = 0, message = "Defense cannot be lower than 0.")
    protected int defense;

    @Min(value = 0, message = "Hit point cannot be lower than 0.")
    protected int hp;

    @NotNull
    protected Coordinates coordinates;
}
