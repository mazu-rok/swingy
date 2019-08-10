package com.amazurok.swingy.controller;

import com.amazurok.swingy.model.characters.Person;
import com.mongodb.*;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class DBController {
    private Logger log = LoggerFactory.getLogger(DBController.class);

    private final Datastore db;

    public DBController() {
        Morphia morphia = new Morphia();
        db = morphia.createDatastore(new MongoClient(), "swingy");
        db.ensureIndexes(Person.class);

    }

    public boolean save(Person person) {
        try {
            db.save(person);
            return true;
        } catch (DuplicateKeyException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public List<Person> getHeroes() {
        return db.find(Person.class).find().toList();
    }

    public Person getHeroById(String id) {
        return db.find(Person.class).field("id").equal(UUID.fromString(id)).first();
    }

}
