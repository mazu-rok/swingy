package com.amazurok.swingy.view;

import com.amazurok.swingy.exceptions.IllegalInputException;
import com.amazurok.swingy.model.characters.Person;
import com.amazurok.swingy.model.map.Map;

import java.util.ArrayList;
import java.util.List;

public interface WindowManager {
    Integer displayStartView();
    String displayPlayerSelectionView(List<Person> heroes);
    void displayCreatePlayerView() throws IllegalInputException;
    void displayBattleReport(String report);
    void displayErrors(List<String> errors);
    void displayGameOver(boolean heroWon);
    void displayQuitDialogue();
    void displayMap(final Map map, Person person);
    void displayFightOrRunPrompt();
    void displayForcedFightNotice();
    void displayPlayView();
    void displayRenderGame();
}
