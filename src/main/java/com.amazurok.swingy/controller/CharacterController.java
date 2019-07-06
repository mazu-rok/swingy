package com.amazurok.swingy.controller;

import com.amazurok.swingy.exceptions.IllegalInputException;
import com.amazurok.swingy.model.characters.Person;
import com.amazurok.swingy.model.map.Coordinates;
import com.amazurok.swingy.model.map.Map;
import com.amazurok.swingy.util.CharacterFactory;

public class CharacterController {
    private int defaultMapSize = 9;
    private Map map;
    private Person person;

    public void createDefaultPerson(String name, String type) throws IllegalInputException {
        this.map = new Map(1);
        this.person = CharacterFactory.createPerson(name, type, new Coordinates(map.getSize()/2, map.getSize()/2));
    }

}
