package com.amazurok.swingy.controller;

import com.amazurok.swingy.exceptions.IllegalInputException;
import com.amazurok.swingy.model.artifacts.Artifact;
import com.amazurok.swingy.model.characters.Person;
import com.amazurok.swingy.model.map.Coordinates;
import com.amazurok.swingy.model.map.Map;
import com.amazurok.swingy.util.ArtifactFactory;
import com.amazurok.swingy.util.CharacterFactory;
import com.mongodb.DuplicateKeyException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CharacterController {
    private Logger log = LoggerFactory.getLogger(CharacterController.class);
    private DBController db;

    private Person currentEnemy = null;
    private Random rand = new Random();

    @Getter
    private Map map;
    @Getter
    private Person person;
    private Coordinates previousCoordinates;
    @Getter
    private Artifact lastArtifact;

    @Getter
    private Person winner;

    @Getter
    private List<Person> enemies;

    public CharacterController(DBController db) {
        this.db = db;
    }

    public void createMap() {
            this.map = new Map(person.getLevel());
    }

    public void createDefaultPerson(String name, String type) throws DuplicateKeyException, IllegalInputException {
        this.map = new Map(1);
        this.person = CharacterFactory.createPerson(name, type, new Coordinates(map.getSize()/2, map.getSize()/2));
        db.save(this.person);
    }

    public void createPerson(String name, String type, int level, int attack, int defense, int hp) throws IllegalInputException, DuplicateKeyException {
        this.map = new Map(level);
        this.person = CharacterFactory.createPerson(name, type, level, attack, defense, hp, new Coordinates(map.getSize()/2, map.getSize()/2));
        db.save(this.person);
    }

    public void setPerson(Person person) {
        this.person = person;
        createMap();
    }

    public void createEnemies() {
        enemies = new ArrayList<>();

        String[] names = {"Thanos", "Mysterio", "Hela"};
        for (int i = 0; i < person.getLevel() * 20; i++) {
            int name = rand.nextInt(3);
            int level = rand.nextInt(person.getLevel() + 3);
            int x = rand.nextInt(map.getSize());
            if (x == map.getSize()/2) {
                x--;
            }
            int y = rand.nextInt(map.getSize());
            if (y == map.getSize()/2) {
                y--;
            }
            try {
                this.enemies.add(CharacterFactory.createPerson(names[name], "Enemy", level, 0, 0, 0, new Coordinates(x, y)));
            } catch (IllegalInputException e) {
                log.error(e.getMessage());
            }
        }
    }

    public boolean move(String direction) {
        Coordinates coordinates = person.getCoordinates();
        previousCoordinates = new Coordinates(coordinates.getX(), coordinates.getY());

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
         return coordinates.getX() < 0 || coordinates.getY() < 0 || coordinates.getX() >= map.getSize() || coordinates.getY() >= map.getSize();
    }

    public boolean isItTimeToFight() {
        for (Person enemy: enemies) {
            if (enemy.getCoordinates().equals(person.getCoordinates())) {
                currentEnemy = enemy;
                return true;
            }
        }
        currentEnemy = null;
        return false;
    }

    public void setPersonToCentre() throws DuplicateKeyException, IllegalInputException {
        this.map = new Map(person.getLevel());
        person.setCoordinates(new Coordinates(map.getSize()/2, map.getSize()/2));
        db.save(person);
    }

    public void fight() {
        while (person.getHp() > 0 && currentEnemy.getHp() > 0) {
            person.punch(currentEnemy);
            if (currentEnemy.getHp() > 0) {
                currentEnemy.punch(person);
            }
        }
        if (person.getHp() > 0) {
            person.incrementExperience(person.getLevel() * 500);
            enemies.remove(currentEnemy);
            winner = person;
        } else {
            winner = currentEnemy;
        }
    }

    public boolean findArtifact() {
        int power = rand.nextInt(100);
        String[] artifacts = {"Armor", "Helm", "Weapon"};
        int ind = rand.nextInt(3);
        if (ind < 4) {
            lastArtifact = ArtifactFactory.createArtifact(artifacts[ind], power);
            return true;
        }
        return false;
    }

    public void setArtifact() {
        person.setArtifact(lastArtifact);
    }

    public boolean run() {
        if (rand.nextInt(2) == 1) {
            person.setCoordinates(previousCoordinates);
            return true;
        }
        return false;
    }
}
