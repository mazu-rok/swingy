package com.amazurok.swingy.controller;

import com.amazurok.swingy.model.characters.Person;
import com.amazurok.swingy.storage.HeroStorage;
import com.mongodb.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class DBController {
    private Logger log = LoggerFactory.getLogger(DBController.class);

    private Datastore db = HeroStorage.getHeroStorage("swingy");

    public void save(Person person) throws DuplicateKeyException {
        db.save(person);
    }

    public List<Person> getHeroes() {
        return db.find(Person.class).asList();
    }

    public Person getHeroById(String id) {
        return db.find(Person.class).field("id").equal(UUID.fromString(id)).get();
    }

}
