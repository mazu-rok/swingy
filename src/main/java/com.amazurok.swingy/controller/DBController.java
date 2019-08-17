package com.amazurok.swingy.controller;

import com.amazurok.swingy.exceptions.IllegalInputException;
import com.amazurok.swingy.model.characters.Person;
import com.amazurok.swingy.storage.HeroStorage;
import com.mongodb.*;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DBController {
    private Logger log = LoggerFactory.getLogger(DBController.class);

     private Datastore db = HeroStorage.getHeroStorage("swingy");

    public void save(Person person) throws DuplicateKeyException, IllegalInputException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
        if (constraintViolations.size() > 0 ) {
            List<String> errors = new LinkedList<>();
            for (ConstraintViolation<Person> constraints : constraintViolations) {
                errors.add(constraints.getMessage() + "\n");
            }
            throw new IllegalInputException(errors.toString());
        }
        db.save(person);
    }

    public List<Person> getHeroes() {
        return db.find(Person.class).asList();
    }

    public Person getHeroById(String id) {
        return db.find(Person.class).field("id").equal(UUID.fromString(id)).get();
    }

}
