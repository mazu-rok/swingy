package com.amazurok.swingy.controller;

import com.amazurok.swingy.exceptions.IllegalInputException;
import com.amazurok.swingy.model.characters.Enemy;
import com.amazurok.swingy.model.characters.Person;
import com.amazurok.swingy.model.map.Coordinates;
import com.amazurok.swingy.model.map.Map;
import com.amazurok.swingy.util.CharacterFactory;

import java.util.Random;

public class CharacterController {
    private int defaultMapSize = 9;
    private Map map;
    private Person person;
    private Person enemy;
    private int enemyLevel;

    public void createDefaultPerson(String name, String type) throws IllegalInputException {
        this.map = new Map(1);
        this.person = CharacterFactory.createPerson(name, type, new Coordinates(map.getSize()/2, map.getSize()/2));
    }

    public void createPerson(String name, String type, int level, int experience, int attack, int defense, int hp,
                             Coordinates coordinates) throws IllegalInputException {
        this.map = new Map(level);
        this.person = CharacterFactory.createPerson(name, type, level, experience, attack, defense, hp, coordinates);
    }

    public void createEnemy(Coordinates coordinates) throws IllegalInputException {
        Random rand = new Random();

        String[] names = {"Thanos", "Mysterio", "Hela"};
        int name = rand.nextInt(3);
        this.enemy = CharacterFactory.createPerson(names[name], "Enemy", coordinates);
    }

    private void updatePerson() {
        int level = person.getLevel();
        for (int i = 0;; i++) {
            if (this.person.getExperience() < (level*1000 + Math.pow(level - 1, 2) * 450)) {
                level = i - 1;
                break;
            }
        }
        this.person =
    }

    private void setLevel(int level) {

    }

    private void setExperience(int experience) {
        person.setExperience(person.getExperience() + experience);

    }

}
