package com.amazurok.swingy.view;

import com.amazurok.swingy.exceptions.IllegalInputException;
import com.amazurok.swingy.model.artifacts.Artifact;
import com.amazurok.swingy.model.characters.Person;
import com.amazurok.swingy.model.map.Map;

import java.util.ArrayList;
import java.util.List;

public interface WindowManager {
    Integer displayStartView();
    String displayPlayerSelectionView(List<Person> heroes);
    void displayCreatePlayerView() throws IllegalInputException;
    void displayFightReport(Person person);
    void displayErrors(List<String> errors);
    void displayGameOver(boolean heroWon);
    void displayQuitDialogue();
    void displayMap(final Map map, Person person);
    String displayFightOrRun();
    void displayForcedFightNotice();
    String displayArtifact(Artifact artifact);
    String displayPlayView();
    boolean displayWinView();
}
