package com.amazurok.swingy.view;

import com.amazurok.swingy.controller.CharacterController;
import com.amazurok.swingy.exceptions.IllegalInputException;
import com.amazurok.swingy.model.characters.Person;
import com.amazurok.swingy.util.CharacterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class ConsoleView implements WindowManager {
    private Logger log = LoggerFactory.getLogger(ConsoleView.class);

    private Scanner stdin;
    CharacterController characterController = new CharacterController();

    ConsoleView() {
        stdin = new Scanner(System.in);
    }

    @Override
    public void displayStartView() {
        String input = "";
        while (!(input.equals("1") || input.equals("2") || input.equals("3"))) {
            clearScreen();
            System.out.print(
                    "\n***************************************\n" +
                            "*                                     *\n" +
                            "*           Welcome To Swingy         *\n" +
                            "*                                     *\n" +
                            "*           Make your choice?         *\n" +
                            "*    1. Create new player             *\n" +
                            "*    2. Select a saved player         *\n" +
                            "*    3. Exit                          *\n" +
                            "*                                     *\n" +
                            "***************************************\n");
            System.out.print("Choice: ");
            if (stdin.hasNext())
                input = stdin.next();
            else
                System.exit(0);
            if (!(input.equals("1") || input.equals("2") || input.equals("3"))) {
                System.out.println("\nInvalid symbol. Enter either 1, 2, or 3.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }
        }
        //TODO: execute input
    }

    @Override
    public void displayPlayerSelectionView(ArrayList<Person> heroes) {
        String input = "";
        while (!(input.matches("\\d+$") || input.equals("q") || input.equals("b"))) {
            clearScreen();
            System.out.println(
                    "\n*******************************************************\n" +
                            "*                                                     *\n" +
                            "*                   Select your hero                  *\n" +
                            "*                                                     *\n" +
                            "*******************************************************\n"
            );
            int index = 0;
            for (Person player : heroes) {
                index++;
                String line = String.format(
                        "%d. Name: %s\n" +
                                "\tClass: %s\n" +
                                "\tLevel: %d\n" +
                                "\tExperience: %d\n" +
                                "\tAttack: %d\n" +
                                "\tDefense: %d\n" +
                                "\tHP: %d\n",
                        index, player.getName(), player.getType(), player.getLevel(), player.getExperience(), player.getAttack(),
                        player.getDefense(), player.getHp()
                );
                System.out.println(line);

            }
            System.out.print(
                    "\n*******************************************************\n" +
                            "*  q - Quit                                           *\n" +
                            "*  b - Back                                           *\n" +
                            "*                                                     *\n" +
                            "*******************************************************\n" +
                            "Press number of a hero: ");
            if (stdin.hasNext())
                input = stdin.next();
            else
                System.exit(0);
            if (!(input.matches("\\d+$") || input.equals("q") || input.equals("b"))) {
                System.out.println("Invalid symbol. Enter a number of a hero");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }
        }
        //TODO: execute input
    }

    @Override
    public void displayCreatePlayerView() {
        String heroClass = getHeroClass();
        String heroName = getHeroName();
        boolean isHeroDefault = isHeroDefault();
        try {
            if (isHeroDefault) {

                characterController.createDefaultPerson(heroName, heroClass);

            } else {
                int level, attack, defense, hp;

                level = getStat("Level");
                attack = getStat("Attack");
                defense = getStat("Defense");
                hp = getStat("HP");
                characterController.createPerson(heroName, heroClass, level, attack, defense, hp);
            }
        } catch (IllegalInputException e) {
            log.error("Error with creating hero\n" + e.getMessage());
        }
    }

    private String getHeroClass() {
        String input = "";
        while (!(input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4"))) {
            clearScreen();
            System.out.print(
                    "\n************************\n" +
                            "*                      *\n" +
                            "*   Select Hero class  *\n" +
                            "*                      *\n" +
                            "* 1. Elf               *\n" +
                            "* 2. Knight            *\n" +
                            "* 3. Magician          *\n" +
                            "* 4. Orc               *\n" +
                            "*                      *\n" +
                            "************************\n" +
                            "Number: "
            );
            if (stdin.hasNext())
                input = stdin.next();
            else
                System.exit(0);
            if (!(input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4"))) {
                System.out.println("Invalid choice. Enter either 1, 2 or 3.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return input;
    }

    private String getHeroName() {
        String input = "";
        clearScreen();
        System.out.print(
                "\n************************\n" +
                        "*                      *\n" +
                        "*    Print hero name   *\n" +
                        "*                      *\n" +
                        "************************\n" +
                        "Name: "
        );
        if (stdin.hasNext())
            input = stdin.next();
        else
            System.exit(0);
        return input;
    }

    private boolean isHeroDefault() {
        String input = "";
        while (!(input.equals("y") || input.equals("n"))) {
            clearScreen();
            System.out.print(
                    "\n****************************************\n" +
                            "*                                      *\n" +
                            "* Do you want hero with default stats? *\n" +
                            "*                                      *\n" +
                            "****************************************\n" +
                            "Y/N: "
            );
            if (stdin.hasNext())
                input = stdin.next().toLowerCase();
            else
                System.exit(0);
            if (!(input.equals("y") || input.equals("n"))) {
                System.out.println("Invalid choice. Enter either Y(Yes) or N(No)");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return input.equals("y");
    }

    private int getStat(String stat) {
        String value = "";
        while (!value.matches("-?\\d+$")) {
            clearScreen();
            System.out.println(String.format(
                    "\n*****************************\n" +
                            "*                           *\n" +
                            "*        Enter %-10s*\n" +
                            "*                           *\n" +
                            "*****************************\n", stat)
            );
            System.out.print("Value: ");
            if (stdin.hasNext())
                value = stdin.next();
            else
                System.exit(0);
            if (!value.matches("-?\\d+$")) {
                System.out.println("\nInvalid " + stat + ". Please enter a number.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return (Integer.parseInt(value));
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

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
