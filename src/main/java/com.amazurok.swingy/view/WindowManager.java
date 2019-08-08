package com.amazurok.swingy.view;

import com.amazurok.swingy.model.characters.Person;

import java.util.ArrayList;
import java.util.Map;

public interface WindowManager {
    void displayStartView();
    void displayPlayerSelectionView(ArrayList<Person> heroes);
    void displayCreatePlayerView();
    void displayBattleReport(String report);
    void displayErrors(ArrayList<String> errors);
    void displayGameOver(boolean heroWon);
    void displayQuitDialogue();
    void displayMap(final Map map);
    void displayFightOrRunPrompt();
    void displayForcedFightNotice();
    void displayPlayView();
    void displayRenderGame();
}
