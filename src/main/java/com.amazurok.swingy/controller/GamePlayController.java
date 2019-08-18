package com.amazurok.swingy.controller;

import com.amazurok.swingy.exceptions.IllegalInputException;
import com.amazurok.swingy.model.Game;
import com.amazurok.swingy.model.artifacts.Artifact;
import com.amazurok.swingy.model.characters.Person;
import com.amazurok.swingy.view.ConsoleView;
import com.amazurok.swingy.view.GraphicViews.GraphicView;
import com.amazurok.swingy.view.WindowManager;
import javafx.beans.WeakInvalidationListener;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GamePlayController {
    Logger log = LoggerFactory.getLogger(GamePlayController.class);

    private enum GameStage {START, SELECTION, CREATION, PLAY, RUN_FIGHT, FORCED_FIGHT, FIGHT, RUN, ARTIFACT, WIN, GAME_OVER, QUIT}

    private DBController db = new DBController();
    private CharacterController characterController = new CharacterController(db);

    private ConsoleView consoleView = new ConsoleView(characterController, this);
    private GraphicView graphicView = new GraphicView(characterController, this);

    private WindowManager windowManager;
    private GameStage stage = GameStage.START;

    public GamePlayController(String mode) {
        setOutMode(mode);
    }

    private void setOutMode(String mode) {
        if (mode.toLowerCase().equals("console") || mode.toLowerCase().equals("c")) {
            windowManager = consoleView;
        } else if (mode.toLowerCase().equals("gui") || mode.toLowerCase().equals("g")) {
            windowManager = graphicView;
        } else {
            log.error("Invalid game mode");
            System.exit(1);
        }
    }

    public void displayGame() {
        switch (stage) {
            case START:
                windowManager.displayStartView();
                break;
            case SELECTION:
                List<Person> heroes = db.getHeroes();
                windowManager.displayPlayerSelectionView(heroes);
                break;
            case CREATION:
                windowManager.displayCreatePlayerView();
                break;
            case PLAY:
                windowManager.displayPlayView();
                break;
            case RUN_FIGHT:
                windowManager.displayFightOrRun();
                break;
            case FORCED_FIGHT:
                windowManager.displayForcedFightNotice();
                break;
            case FIGHT:
                characterController.fight();
                windowManager.displayFightReport(characterController.getWinner());
                break;
            case RUN:
                break;
            case ARTIFACT:
                windowManager.displayArtifact(characterController.getLastArtifact());
                break;
            case WIN:
                windowManager.displayWinView();
                break;
            case GAME_OVER:
                windowManager.displayGameOver();
                break;
            case QUIT:
                windowManager.displayQuitDialogue();
                break;
        }
    }

    public void handleInput(String input) {
        switch (stage) {
            case START:
                if (input.equals("1")) {
                    stage = GameStage.CREATION;
                } else if (input.equals("2")) {
                    stage = GameStage.SELECTION;
                } else if (input.equals("3")) {
                    stage = GameStage.QUIT;
                }
                break;
            case SELECTION:
                if (input.equals("q")) {
                    stage = GameStage.QUIT;
                } else if (input.equals("b")) {
                    stage = GameStage.START;
                } else {
                    characterController.setPerson(db.getHeroById(input));
                    characterController.createEnemy();
                    stage = GameStage.PLAY;
                }
                break;
            case CREATION:
                stage = GameStage.PLAY;
                break;
            case PLAY:
                if (input.equals("q")) {
                    stage = GameStage.QUIT;
                    break;
                } else if (input.equals("g") || input.equals("c")) {
                    setOutMode(input);
                    break;
                }
                if (characterController.move(input)) {
                    stage = GameStage.WIN;
                }
                if (characterController.isItTimeToFight()) {
                    stage = GameStage.RUN_FIGHT;
                }
                break;
            case RUN_FIGHT:
                if (input.equals("r") && characterController.run()) {
                    stage = GameStage.RUN;
                } else {
                    stage = GameStage.FORCED_FIGHT;
                }
                break;
            case FORCED_FIGHT:
                stage = GameStage.FIGHT;
                break;
            case FIGHT:
                if (characterController.getWinner().getType().equals("Enemy")) {
                    stage = GameStage.GAME_OVER;
                } else {
                    if (characterController.findArtifact()) {
                        stage = GameStage.ARTIFACT;
                    }
                    characterController.createMap();
                    stage = GameStage.PLAY;
                }
                break;
            case RUN:
                break;
            case ARTIFACT:
                if (input.equals("y")) {
                    characterController.setArtifact();
                }
                stage = GameStage.PLAY;
                break;
            case WIN:
                if (input.equals("y")) {
                    stage = GameStage.PLAY;
                    characterController.getPerson().incrementExperience(2000);
                    try {
                        characterController.setPersonToCentre();
                    } catch (IllegalInputException e) {
                        windowManager.displayError(e.getMessage());
                        stage = GameStage.QUIT;
                        break;
                    }
                    characterController.createEnemy();
                } else {
                    stage = GameStage.QUIT;
                }
                break;
            case GAME_OVER:
                if (input.equals("y")) {
                    stage = GameStage.START;
                } else {
                    stage = GameStage.QUIT;
                }
                break;
            case QUIT:
                System.exit(0);
                break;
        }
    }
}
