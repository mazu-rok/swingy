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

        int dialogResult = JOptionPane.showOptionDialog(graphicPlayView.getFrame(), "You lost!!!\nDo you want to continue?", "Game Over",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options1, null);
        if (dialogResult == JOptionPane.YES_OPTION) {
            gamePlayController.setInput("y");
        } else {
            gamePlayController.setInput("n");
        }
        graphicPlayView.getFrame().dispose();
    }

    @Override
    public void displayQuitDialogue() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (graphicPlayView.getFrame(), "Goodbye!!!","Quite game",dialogButton);
        graphicPlayView.getFrame().dispose();
    }

    @Override
    public void displayFightOrRun() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(graphicPlayView.getFrame(), "Make your choice, fight or run?", "Enemy encountered", dialogButton);
        if (dialogResult == JOptionPane.YES_OPTION)
            gamePlayController.setInput("f");
        else
            gamePlayController.setInput("r");
    }

    @Override
    public void displayForcedFightNotice() {
        String message = "Unfortunately you could not escape";
        JOptionPane.showMessageDialog(graphicPlayView.getFrame(), message);
    }

    @Override
    public void displayArtifact(Artifact artifact) {

    }

    @Override
    public void displayPlayView() {
        graphicPlayView.displayPlayView();
    }

    @Override
    public void displayWinView() {

    }
}
