package com.amazurok.swingy.view;

import com.amazurok.swingy.controller.CharacterController;
import com.amazurok.swingy.controller.GamePlayController;
import com.amazurok.swingy.exceptions.IllegalInputException;
import com.amazurok.swingy.model.artifacts.Artifact;
import com.amazurok.swingy.model.characters.Person;
import com.amazurok.swingy.model.map.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class ConsoleView implements WindowManager {
    private Logger log = LoggerFactory.getLogger(ConsoleView.class);

    private Scanner stdin;
    private CharacterController characterController;
    private GamePlayController gamePlayController;

    public ConsoleView(CharacterController characterController, GamePlayController gamePlayController) {
        this.characterController = characterController;
        this.gamePlayController = gamePlayController;
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
                            "*           Make your choice!         *\n" +
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
        gamePlayController.handleInput(input);
        gamePlayController.displayGame();
    }

    @Override
    public void displayPlayerSelectionView(List<Person> heroes) {
        String input = "";
        if (heroes.isEmpty()) {
            clearScreen();
            System.out.println(
                    "\n*******************************************************\n" +
                            "*                                                     *\n" +
                            "*                    No saved heroes                  *\n" +
                            "*                                                     *\n" +
                            "*******************************************************\n"
            );
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
            gamePlayController.handleInput("b");
            gamePlayController.displayGame();
            return;
        }
        while (!input.matches("\\d+$")) {
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
                String heroInfo = String.format(
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
                System.out.println(heroInfo);

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
            if (input.equals("q") || input.equals("b")) {
                gamePlayController.handleInput(input);
                gamePlayController.displayGame();
                return;
            } else if (!(input.matches("\\d+$") && Integer.parseInt(input) >= 1 || Integer.parseInt(input) <= index)) {
                System.out.println("Invalid symbol. Enter a number of a hero");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }
        }
        gamePlayController.handleInput(heroes.get(Integer.parseInt(input) - 1).getId().toString());
        gamePlayController.displayGame();
    }

    @Override
    public void displayCreatePlayerView() {
        String heroName = getHeroName();
        String heroClass = getHeroClass();
        while (true) {
            try {
                if (isHeroDefault()) {
                    characterController.createDefaultPerson(heroName, heroClass);
                    break;
                } else {
                    int level, attack, defense, hp;

                    level = getStat("Level");
                    attack = getStat("Attack");
                    defense = getStat("Defense");
                    hp = getStat("HP");
                    characterController.createPerson(heroName, heroClass, level, attack, defense, hp);
                    break;
                }
            } catch (Exception e) {
                displayError("Cannot create hero, please try again\n" + e.getMessage());
            }
        }
        gamePlayController.handleInput("");
        gamePlayController.displayGame();
    }

    private String getHeroClass() {
        String[] heroesClasses = {"Elf", "Knight", "Magician", "Orc"};
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
                System.out.println("Invalid choice. Enter either 1, 2, 3 or 4.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return heroesClasses[Integer.parseInt(input) - 1];
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
                            "*        Enter %-13s*\n" +
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
    public void displayFightReport(Person player) {
        try {
            clearScreen();
            System.out.println(
                    "\n*************************\n" +
                            "*                       *\n" +
                            "*     Winner is....     *\n" +
                            "*                       *\n" +
                            "*************************");
            Thread.sleep(500);
            String line = String.format(
                    "Name: %s\n" +
                            "\tClass: %s\n" +
                            "\tLevel: %d\n" +
                            "\tExperience: %d\n" +
                            "\tAttack: %d\n" +
                            "\tDefense: %d\n" +
                            "\tHP: %d\n" +
                            "\tArmor: %d\n" +
                            "\tHelm: %d\n" +
                            "\tWeapon: %d",
                    player.getName(), player.getType(), player.getLevel(), player.getExperience(), player.getAttack(),
                    player.getDefense(), player.getHp(), player.getArmor().getPower(), player.getHelm().getPower(),
                    player.getWeapon().getPower()
            );
            System.out.println(line);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        gamePlayController.handleInput("");
        gamePlayController.displayGame();

    }

    @Override
    public void displayError(String error) {
        clearScreen();
        System.out.println(
                "\n***************************************\n" +
                        "*                                     *\n" +
                        "*           Something happen          *\n" +
                        "* Press any key and Enter to continue *\n" +
                        "*                                     *\n" +
                        "***************************************\n" +
                        error);
        stdin.hasNext();
    }

    @Override
    public void displayGameOver() {
        String input = "";
        clearScreen();
        System.out.print(
                "\n********************************\n" +
                        "*                              *\n" +
                        "*          You lost!!!         *\n" +
                        "*   Do you want to continue?   *\n" +
                        "*                              *\n" +
                        "********************************\n" +
                        "Y/N: "
        );
        if (stdin.hasNext())
            input = stdin.next();
        else
            System.exit(0);
        gamePlayController.handleInput(input.toLowerCase());
        gamePlayController.displayGame();
    }

    @Override
    public void displayQuitDialogue() {
        clearScreen();
        System.out.print(
                "\n****************\n" +
                        "*              *\n" +
                        "*  Goodbye!!!  *\n" +
                        "*              *\n" +
                        "****************\n");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        gamePlayController.handleInput("");
    }

    @Override
    public void displayMap(Map map, Person person) {
        for (int i = 0; i < map.getSize(); i++) {
            for (int j = 0; j < map.getSize(); j++) {
                if (i == person.getCoordinates().getY() && j == person.getCoordinates().getX()) {
                    System.out.print(person.getName().charAt(0));
                } else {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
    }

    @Override
    public void displayFightOrRun() {
        String input = "";
        while (!(input.toLowerCase().equals("f") || input.toLowerCase().equals("r"))) {
            clearScreen();
            displayMap(characterController.getMap(), characterController.getPerson());
            System.out.print(
                    "\n****************************************\n" +
                            "*                                      *\n" +
                            "*    Make your choice, fight or run?   *\n" +
                            "*                                      *\n" +
                            "****************************************\n" +
                            "F/R: "
            );
            if (stdin.hasNext())
                input = stdin.next();
            else
                System.exit(0);
            if (!(input.toLowerCase().equals("f") || input.toLowerCase().equals("r"))) {
                System.out.println("\nInvalid instruction. Please try again.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }
        }
        gamePlayController.handleInput(input.toLowerCase());
        gamePlayController.displayGame();
    }

    @Override
    public void displayForcedFightNotice() {
        clearScreen();
        System.out.print(
                "\n****************************************\n" +
                        "*                                      *\n" +
                        "*  Unfortunately you could not escape  *\n" +
                        "*                                      *\n" +
                        "****************************************\n");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        gamePlayController.handleInput("");
        gamePlayController.displayGame();
    }

    @Override
    public void displayArtifact(Artifact artifact) {
        String input = "";
        while (!(input.toLowerCase().equals("y") || input.toLowerCase().equals("n"))) {
            clearScreen();
            System.out.println(String.format(
                    "\n****************************************\n" +
                            "*                                      *\n" +
                            "*     You found %6s with power %3d  *\n" +
                            "*         Do you want to get it?       *\n" +
                            "*                                      *\n" +
                            "****************************************\n" +
                            "Y/N: ", artifact.getType(), artifact.getPower()));

            if (stdin.hasNext())
                input = stdin.next();
            else
                System.exit(0);
            if (!(input.toLowerCase().equals("y") || input.toLowerCase().equals("n"))) {
                System.out.println("\nInvalid answer. Please try again.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }
        }
        gamePlayController.handleInput(input.toLowerCase());
        gamePlayController.displayGame();

    }

    @Override
    public void displayPlayView() {
        String input = "";
        while (!(input.toLowerCase().equals("n") || input.toLowerCase().equals("e") || input.toLowerCase().equals("s")
                || input.toLowerCase().equals("w") || input.toLowerCase().equals("q") || input.toLowerCase().equals("g"))) {
            clearScreen();
            displayMap(characterController.getMap(), characterController.getPerson());
            System.out.print(
                    "\n********************************************************\n" +
                            "*                                                      *\n" +
                            "*  Where do you want to go, North, East, South, West?  *\n" +
                            "*                                                      *\n" +
                            "********************************************************\n" +
                            "N/E/S/W: "
            );
            if (stdin.hasNext())
                input = stdin.next();
            else
                System.exit(0);
            if (!(input.toLowerCase().equals("n") || input.toLowerCase().equals("e") || input.toLowerCase().equals("s")
                    || input.toLowerCase().equals("w") || input.toLowerCase().equals("q") || input.toLowerCase().equals("g"))) {
                System.out.println("\nInvalid instruction. Please try again.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }
        }
        gamePlayController.handleInput(input.toLowerCase());
        gamePlayController.displayGame();

    }

    @Override
    public void displayWinView() {
        String input = "";
        clearScreen();
        System.out.print(
                "\n********************************\n" +
                        "*                              *\n" +
                        "*          You win!!!          *\n" +
                        "*   Do you want to continue?   *\n" +
                        "*                              *\n" +
                        "********************************\n" +
                        "Y/N: "
        );
        if (stdin.hasNext())
            input = stdin.next();
        else
            System.exit(0);
        gamePlayController.handleInput(input.toLowerCase());
        gamePlayController.displayGame();

    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
