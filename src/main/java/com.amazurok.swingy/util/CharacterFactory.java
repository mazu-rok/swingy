package com.amazurok.swingy.util;

import com.amazurok.swingy.exceptions.IllegalInputException;
import com.amazurok.swingy.model.characters.Elf;
import com.amazurok.swingy.model.characters.Enemy;
import com.amazurok.swingy.model.characters.Knight;
import com.amazurok.swingy.model.characters.Magician;
import com.amazurok.swingy.model.characters.Orc;
import com.amazurok.swingy.model.characters.Person;
import com.amazurok.swingy.model.map.Coordinates;

public class CharacterFactory {
    public static Person createPerson(String name, String type, int level, int attack, int defense, int hp,
                                      Coordinates coordinates) throws IllegalInputException {
        switch (type) {
            case "Elf":
                return new Elf(name, level, attack, defense, hp, coordinates);
            case "Knight":
                return new Knight(name, level, attack, defense, hp, coordinates);
            case "Magician":
                return new Magician(name, level, attack, defense, hp, coordinates);
            case "Orc":
                return new Orc(name, level, attack, defense, hp, coordinates);
            case "Enemy":
                return new Enemy(name, level, coordinates);
            default:
                throw new IllegalInputException(String.format("Type '%s' is not supported", type));
        }
    }

    public static Person createPerson(String name, String type, Coordinates coordinates) throws IllegalInputException {
        switch (type) {
            case "Elf":
                return new Elf(name, coordinates);
            case "Knight":
                return new Knight(name, coordinates);
            case "Magician":
                return new Magician(name, coordinates);
            case "Orc":
                return new Orc(name, coordinates);
            case "Enemy":
                return new Enemy(name, coordinates);
            default:
                throw new IllegalInputException(String.format("Type '%s' is not supported", type));
        }
    }
}
