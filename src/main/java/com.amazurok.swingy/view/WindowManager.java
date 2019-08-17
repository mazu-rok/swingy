package com.amazurok.swingy.view;

import com.amazurok.swingy.exceptions.IllegalInputException;
import com.amazurok.swingy.model.artifacts.Artifact;
import com.amazurok.swingy.model.characters.Person;
import com.amazurok.swingy.model.map.Map;

import java.util.ArrayList;
import java.util.List;

public interface WindowManager {
    void displayStartView();
    void displayPlayerSelectionView(List<Person> heroes);
    void displayCreatePlayerView();
    void displayFightReport(Person person);
    void displayError(String error);
    void displayGameOver();
    void displayQuitDialogue();
    void displayFightOrRun();
    void displayForcedFightNotice();
    void displayArtifact(Artifact artifact);
    void displayPlayView();
    void displayWinView();
}
