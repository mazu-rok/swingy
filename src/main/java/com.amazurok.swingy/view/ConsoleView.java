package com.amazurok.swingy.view;

import com.amazurok.swingy.model.characters.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class ConsoleView implements WindowManager{
    private Logger log = LoggerFactory.getLogger(ConsoleView.class);

    private Scanner stdin;

    ConsoleView() {
        stdin = new Scanner(System.in);
    }

    @Override
    public void displayStartView() {
        String input = "";
        while (!(input.equals("1") || input.equals("2") || input.equals("3"))) {
            clearScreen();
            System.out.print(
                            "\n*************************************\n" +
                            "*                                     *\n" +
                            "*           Welcome To Swingy         *\n" +
                            "*                                     *\n" +
                            "*           Make your choice?         *\n" +
                            "*    1. Create new player             *\n" +
                            "*    2. Select a saved player         *\n" +
                            "*    3. Exit                          *\n" +
                            "*                                     *\n" +
                            "***************************************\n");
        }
        System.out.print("Choice: ");
        if (stdin.hasNext())
            input = stdin.next();
        else
            System.exit(0);
        if (!(input.equals("1") || input.equals("2") || input.equals("3")))
        {
            System.out.println("\nInvalid symbol. Enter either 1, 2, or 3.");
            try { Thread.sleep(1000);} catch (InterruptedException e) {log.error(e.getMessage());}
        }

    }

    @Override
    public void displayPlayerSelectionView(ArrayList<Person> heroes) {

    }

    @Override
    public void displayCreatePlayerView() {

    }

    @Override
    public void displayBattleReport(String report) {

    }

    @Override
    public void displayErrors(ArrayList<String> errors) {

    }

    @Override
    public void displayGameOver(boolean heroWon) {

    }

    @Override
    public void displayQuitDialogue() {

    }

    @Override
    public void displayMap(Map map) {

    }

    @Override
    public void displayFightOrRunPrompt() {

    }

    @Override
    public void displayForcedFightNotice() {

    }

    @Override
    public void displayPlayView() {

    }

    @Override
    public void displayRenderGame() {

    }

    public void clearScreen()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
