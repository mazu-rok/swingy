package com.amazurok.swingy.util;

import com.amazurok.swingy.model.artifacts.Armor;
import com.amazurok.swingy.model.artifacts.Artifact;
import com.amazurok.swingy.model.artifacts.Helm;
import com.amazurok.swingy.model.artifacts.Weapon;

public class ArtifactFactory {
    public static Artifact createArtifact(String name, int power) {
        switch (name) {
            case "Armor":
                return new Armor(power);
            case "Helm":
                return new Helm(power);
            case "Weapon":
                return new Weapon(power);
            default:
                return null;
        }
    }

}
