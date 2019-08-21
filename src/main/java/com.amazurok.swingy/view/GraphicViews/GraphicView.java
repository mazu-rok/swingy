package com.amazurok.swingy.view.GraphicViews;

import com.amazurok.swingy.controller.CharacterController;
import com.amazurok.swingy.controller.GamePlayController;
import com.amazurok.swingy.model.artifacts.Artifact;
import com.amazurok.swingy.model.characters.Person;
import com.amazurok.swingy.model.map.Map;
import com.amazurok.swingy.view.WindowManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.List;

public class GraphicView implements WindowManager {
    private Logger log = LoggerFactory.getLogger(GraphicView.class);

    private CharacterController characterController;
    private GamePlayController gamePlayController;

    private JFrame frame = new JFrame("Swingy");

    private GraphicStartView graphicStartView;
    private GraphicSelectionView graphicSelectionView;
    private GraphicCreateView graphicCreateView;
    private GraphicPlayView graphicPlayView;
    private JFrame errorFrame;


    public GraphicView(CharacterController characterController, GamePlayController gamePlayController) {
        this.characterController = characterController;
        this.gamePlayController = gamePlayController;

        graphicStartView = new GraphicStartView(gamePlayController);
        graphicSelectionView = new GraphicSelectionView(gamePlayController);
        graphicCreateView = new GraphicCreateView(gamePlayController, characterController);
        graphicPlayView = new GraphicPlayView(gamePlayController, characterController);
    }

    @Override
    public void displayStartView() {
        graphicStartView.displayStartView();
    }

    @Override
    public void displayPlayerSelectionView(List<Person> heroes) {
        graphicSelectionView.displaySelectionView(heroes);
    }

    @Override
    public void displayCreatePlayerView() {
        graphicCreateView.displayCreatePlayerView();
    }

    @Override
    public void displayFightReport(Person person) {
        graphicPlayView.displayBattle(person);
    }

    @Override
    public void displayError(String error) {
        JOptionPane.showMessageDialog(errorFrame, error);
        errorFrame.dispose();
    }

    @Override
    public void displayGameOver() {

        Object[] options1 = {"Main menu", "Quit"};

        int dialogResult = JOptionPane.showOptionDialog(graphicPlayView.getFrame(), "You lost!!!", "Game Over",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options1, null);
        if (dialogResult == JOptionPane.YES_OPTION) {
            gamePlayController.handleInput("y");
        } else {
            gamePlayController.handleInput("n");
        }
        graphicPlayView.getFrame().dispose();
        gamePlayController.displayGame();
    }

    @Override
    public void displayQuitDialogue() {
        JOptionPane.showMessageDialog (graphicPlayView.getFrame(), "Goodbye!!!","Quite game", JOptionPane.INFORMATION_MESSAGE);
        graphicPlayView.getFrame().dispose();
    }

    @Override
    public void displayFightOrRun() {
        int dialogResult = JOptionPane.showConfirmDialog(graphicPlayView.getFrame(), "You found enemy, do you want to fight?", "Enemy encountered", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION)
            gamePlayController.handleInput("f");
        else
            gamePlayController.handleInput("r");
        gamePlayController.displayGame();
    }

    @Override
    public void displayForcedFightNotice() {
        String message = "Unfortunately you could not escape";
        JOptionPane.showMessageDialog(null, message);
    }

    @Override
    public void displayArtifact(Artifact artifact) {
        String message = String.format("You found %s with power %d", artifact.getType(), artifact.getPower());
        int dialogResult = JOptionPane.showConfirmDialog(graphicPlayView.getFrame(), message, "Artifact", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION)
            gamePlayController.handleInput("y");
        else
            gamePlayController.handleInput("n");
        gamePlayController.displayGame();
    }

    @Override
    public void displayMap(Map map, Person person) {
        graphicPlayView.drawMap();
    }

    @Override
    public void displayPlayView() {
        graphicPlayView.displayPlayView();
    }

    @Override
    public void displayWinView() {
        int dialogResult = JOptionPane.showConfirmDialog(graphicPlayView.getFrame(), "You win!!!\nDo you want to continue?", "WIN", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION)
            gamePlayController.handleInput("y");
        else
            gamePlayController.handleInput("n");
        gamePlayController.displayGame();

    }
}
