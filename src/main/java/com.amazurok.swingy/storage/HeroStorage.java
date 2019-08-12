package com.amazurok.swingy.storage;

import com.amazurok.swingy.model.characters.Person;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class HeroStorage {

    public static Datastore getHeroStorage (String dbName) {
        Morphia morphia = new Morphia();
        Datastore db = morphia.createDatastore(new MongoClient(), dbName);
        db.ensureIndexes(Person.class);
        return db;
    }
}
