package com.amazurok.swingy.controller;

import com.amazurok.swingy.exceptions.IllegalInputException;
import com.amazurok.swingy.model.characters.Enemy;
import com.amazurok.swingy.model.characters.Person;
import com.amazurok.swingy.model.map.Coordinates;
import com.amazurok.swingy.model.map.Map;
import com.amazurok.swingy.util.CharacterFactory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CharacterController {

    private DBController db;

    private int defaultMapSize = 9;
    @Getter
    private Map map;
    @Getter
    private Person person;
    @Getter
    private List<Person> enemies;

    public CharacterController(DBController db) {
        this.db = db;
    }

    public void createDefaultPerson(String name, String type) throws IllegalInputException {
        this.map = new Map(1);
        this.person = CharacterFactory.createPerson(name, type, new Coordinates(map.getSize()/2, map.getSize()/2));
        db.save(this.person);
    }

    public void createPerson(String name, String type, int level, int attack, int defense, int hp) throws IllegalInputException {
        this.map = new Map(level);
        this.person = CharacterFactory.createPerson(name, type, level, attack, defense, hp, new Coordinates(map.getSize()/2, map.getSize()/2));
        db.save(this.person);
    }

    public void setPerson(Person person) {
        this.map = new Map(person.getLevel());
        this.person = person;
    }

    public void createEnemy() throws IllegalInputException {
        Random rand = new Random();
        enemies = new ArrayList<>();

        String[] names = {"Thanos", "Mysterio", "Hela"};
        for (int i = 0; i < person.getLevel(); i++) {
            int name = rand.nextInt(3);
            int x = rand.nextInt(map.getSize());
            int y = rand.nextInt(map.getSize());
            this.enemies.add(CharacterFactory.createPerson(names[name],"Enemy", person.getLevel(), 0, 0, 0,  new Coordinates(x, y)));
        }
    }

    public boolean move(String direction) {
        Coordinates coordinates = person.getCoordinates();

        switch (direction.toLowerCase()) {
            case "n":
                coordinates.setY(coordinates.getY() - 1);
                break;
            case "e":
                coordinates.setX(coordinates.getX() + 1);
                break;
            case "w":
                coordinates.setX(coordinates.getX() - 1);
                break;
            case "s":
                coordinates.setY(coordinates.getY() + 1);
                break;
        }
        person.setCoordinates(coordinates);
        return isEnd();
    }

    private boolean isEnd() {
        Coordinates coordinates = person.getCoordinates();
         return coordinates.getX() < 0 || coordinates.getY() < 0 || coordinates.getX() > map.getSize() || coordinates.getY() > map.getSize();
    }

    public boolean isItTimeToFight() {
        for (Person enemy: enemies) {
            if (enemy.getCoordinates().equals(person.getCoordinates())) {
                return true;
            }
        }
        return false;
    }

    public void setPersonToCentre() {
        this.map = new Map(person.getLevel());
        person.setCoordinates(new Coordinates(map.getSize()/2, map.getSize()/2));
    }
}
