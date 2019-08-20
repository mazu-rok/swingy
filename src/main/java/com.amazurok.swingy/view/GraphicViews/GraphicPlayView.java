package com.amazurok.swingy.view.GraphicViews;

import com.amazurok.swingy.controller.CharacterController;
import com.amazurok.swingy.controller.GamePlayController;
import com.amazurok.swingy.model.characters.Person;
import com.amazurok.swingy.model.map.Coordinates;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicPlayView {
    Logger log = LoggerFactory.getLogger(GraphicPlayView.class);

    private GamePlayController gamePlayController;
    private CharacterController characterController;

    @Getter
    private JFrame frame = new JFrame("Swingy");
    private JButton btnQuit = new JButton("Quit");
    private JButton btnNorth = new JButton("North");
    private JButton btnSouth = new JButton("South");
    private JButton btnWest = new JButton("West");
    private JButton btnEast = new JButton("East");
    private JButton btnSwitch = new JButton("Switch to console");
    private JPanel mapPanel = new JPanel();
    private JTextArea reportText = new JTextArea();
    private JTextArea statsText = new JTextArea();

    public GraphicPlayView(GamePlayController gamePlayController, CharacterController characterController) {
        this.gamePlayController = gamePlayController;
        this.characterController = characterController;

        statsText.setEditable(false);
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(null);

        reportText.setEditable(false);
        JPanel reportPanel = new JPanel();
        reportPanel.setLayout(null);

        JPanel movementPanel = new JPanel();
        movementPanel.setLayout(null);
        JPanel arenaPanel = new JPanel();
        arenaPanel.setLayout(null);
        JLabel statsLabel = new JLabel("Hero Stats");
        statsLabel.setBounds(120, 10, 100, 20);
        JScrollPane statsScroll = new JScrollPane(statsText);
        statsScroll.setBounds(20, 40, 270, 460);
        statsPanel.setPreferredSize(new Dimension(295, 495));

        JLabel reportLabel = new JLabel("Battle report");
        reportLabel.setBounds(120, 10, 100, 20);
        JScrollPane reportScroll = new JScrollPane(reportText);
        reportScroll.setBounds(0, 40, 270, 460);
        reportPanel.setPreferredSize(new Dimension(295, 495));

        btnNorth.setBounds(560, 20, 120, 40);
        btnSouth.setBounds(560, 100, 120, 40);
        btnEast.setBounds(700, 60, 120, 40);
        btnWest.setBounds(420, 60, 120, 40);
        btnQuit.setBounds(700, 140, 160, 40);
        btnSwitch.setBounds(380, 140, 160, 40);
        movementPanel.setPreferredSize(new Dimension(1200, 195));
        arenaPanel.setPreferredSize(new Dimension(600, 495));
        JLabel arenaLabel = new JLabel("Arena");
        arenaLabel.setBounds(280, 10, 100, 20);
        mapPanel.setBounds(0, 40, 600, 455);

        // Add to panel
        statsPanel.add(statsLabel);
        statsPanel.add(statsScroll);
        reportPanel.add(reportLabel);
        reportPanel.add(reportScroll);
        movementPanel.add(btnNorth);
        movementPanel.add(btnSouth);
        movementPanel.add(btnEast);
        movementPanel.add(btnWest);
        movementPanel.add(btnQuit);
        movementPanel.add(btnSwitch);

        arenaPanel.add(arenaLabel);
        arenaPanel.add(mapPanel);

        // Set up frame
        frame.add(statsPanel, BorderLayout.WEST);
        frame.add(arenaPanel, BorderLayout.CENTER);
        frame.add(reportPanel, BorderLayout.EAST);
        frame.add(movementPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setPreferredSize(new Dimension(1200, 720));
        frame.setLocation(500, 500);
        frame.setResizable(false);
        frame.pack();

        startListeners();
    }

    private void startListeners() {
        btnNorth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePlayController.handleInput("n");
                gamePlayController.displayGame();
            }
        });

        btnSouth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePlayController.handleInput("s");
                gamePlayController.displayGame();
            }
        });

        btnWest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePlayController.handleInput("w");
                gamePlayController.displayGame();
            }
        });

        btnEast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePlayController.handleInput("e");
                gamePlayController.displayGame();
            }
        });

        btnSwitch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                gamePlayController.handleInput("c");
                gamePlayController.displayGame();
            }
        });

        btnQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePlayController.handleInput("q");
                gamePlayController.displayGame();
            }
        });
    }

    public void displayPlayView() {
        frame.setVisible(true);
        drawMap();
    }

    public void drawMap() {
        displayStats();
        int mapSize = characterController.getMap().getSize();
        Coordinates personCoordinates = characterController.getPerson().getCoordinates();
        mapPanel.removeAll();
        mapPanel.setLayout(new GridLayout(mapSize, mapSize));
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                JButton button;
                if (i == personCoordinates.getY() && j == personCoordinates.getX()) {
                    button = new JButton();
                    button.setBackground(Color.GREEN);
                } else {
                    button = new JButton();
                    button.setBackground(Color.LIGHT_GRAY);
                }
                button.setOpaque(true);
                button.setEnabled(false);
                mapPanel.add(button);
            }
        }
        mapPanel.revalidate();
        mapPanel.repaint();
    }

    private void displayStats() {
        Person person = characterController.getPerson();
        String detail = "\n\tName: " + person.getName() + "\n\n\n";
        detail += "\tClass: " + person.getType() + "\n\n\n";
        detail += "\tLevel: " + person.getLevel() + "\n\n\n";
        detail += "\tExp: " + person.getExperience() + "\n\n\n";
        detail += "\tAtk: " + person.getAttack() + "\n\n\n";
        detail += "\tDef: " + person.getDefense() + "\n\n\n";
        detail += "\tHp: " + person.getHp() + "\n\n\n";
        statsText.setText(detail);
    }

    public void displayBattle(Person player) {
        String line = "Winner is:\n";
        line += String.format(
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
                player.getDefense(), player.getHp(), player.getArmor() != null ? player.getArmor().getPower() : 0,
                player.getHelm() != null ? player.getHelm().getPower() : 0, player.getWeapon() != null ? player.getWeapon().getPower() : 0
        );
        reportText.setText(line);
    }
}
