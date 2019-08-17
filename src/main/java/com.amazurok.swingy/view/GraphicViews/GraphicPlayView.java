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

    private boolean initialised = false, reDrawMap = true, switched = false;

    @Getter
    private JFrame frame;
    private JButton btnQuit, btnNorth, btnSouth, btnWest, btnEast, btnSwitch;
    private JPanel mapPanel, arenaPanel, movementPanel, statsPanel, reportPanel;
    private JLabel reportLabel, statsLabel, arenaLabel;
    private JTextArea reportText, statsText;
    private JScrollPane reportScroll, statsScroll;

    public GraphicPlayView(GamePlayController gamePlayController, CharacterController characterController) {
        this.gamePlayController = gamePlayController;
        this.characterController = characterController;
    }

    private void initPlayView() {
        btnQuit = new JButton("Quit");
        btnSwitch = new JButton("Switch to console mode");
        btnWest = new JButton("West");
        btnEast = new JButton("East");
        btnSouth = new JButton("South");
        btnNorth = new JButton("North");
        mapPanel = new JPanel();
        arenaPanel = new JPanel();
        movementPanel = new JPanel();
        statsPanel = new JPanel();
        reportPanel = new JPanel();
        statsLabel = new JLabel("Hero Stats");
        reportLabel = new JLabel("Battle report");
        arenaLabel = new JLabel("Arena");
        statsText = new JTextArea();
        reportText = new JTextArea();
        statsScroll = new JScrollPane(statsText);
        reportScroll = new JScrollPane(reportText);

        statsText.setEditable(false);
        statsPanel.setLayout(null);

        reportText.setEditable(false);
        reportPanel.setLayout(null);

        movementPanel.setLayout(null);
        arenaPanel.setLayout(null);
        setColors();
        setBounds();
        setListeners();
        addToPanel();
    }

    private void setListeners() {
        btnNorth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePlayController.setInput("n");
            }
        });

        btnSouth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePlayController.setInput("s");
            }
        });

        btnWest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePlayController.setInput("w");
            }
        });

        btnEast.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePlayController.setInput("e");
            }
        });

        btnSwitch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePlayController.setInput("c");
            }
        });

        btnQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePlayController.setInput("q");
            }
        });
    }

    private void setBounds() {
        statsLabel.setBounds(120, 10, 100, 20);
        statsScroll.setBounds(20, 40, 270, 460);
        statsPanel.setPreferredSize(new Dimension(295, 495));

        reportLabel.setBounds(120, 10, 100, 20);
        ;
        reportScroll.setBounds(0, 40, 270, 460);
        reportPanel.setPreferredSize(new Dimension(295, 495));

        btnNorth.setBounds(560, 20, 120, 20);
        btnSouth.setBounds(560, 100, 120, 20);
        btnEast.setBounds(700, 60, 120, 20);
        btnWest.setBounds(420, 60, 120, 20);
        btnQuit.setBounds(700, 140, 160, 20);
        btnSwitch.setBounds(380, 140, 160, 20);
        movementPanel.setPreferredSize(new Dimension(1200, 195));
        arenaPanel.setPreferredSize(new Dimension(600, 495));
        arenaLabel.setBounds(280, 10, 100, 20);
        mapPanel.setBounds(0, 40, 600, 455);
    }

    private void setColors() {
        statsLabel.setForeground(Color.WHITE);
        statsText.setBackground(Color.DARK_GRAY);
        statsText.setForeground(Color.WHITE);
        statsPanel.setBackground(Color.DARK_GRAY);
        reportLabel.setForeground(Color.WHITE);
        reportText.setBackground(Color.DARK_GRAY);
        reportText.setForeground(Color.WHITE);
        reportPanel.setBackground(Color.DARK_GRAY);
        movementPanel.setBackground(Color.DARK_GRAY);
        arenaLabel.setForeground(Color.WHITE);
        arenaPanel.setBackground(Color.DARK_GRAY);
    }

    private void addToPanel() {
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
    }

    public void displayPlayView() {
        if (!initialised) {
            initPlayView();
            frame = new JFrame("Swingy");
            frame.add(statsPanel, BorderLayout.WEST);
            frame.add(arenaPanel, BorderLayout.CENTER);
            frame.add(reportPanel, BorderLayout.EAST);
            frame.add(movementPanel, BorderLayout.SOUTH);
            frame.setBackground(Color.WHITE);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setPreferredSize(new Dimension(1200, 720));
            frame.setResizable(false);
            frame.pack();
            initialised = true;
        }
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
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
        String line = String.format(
                "Name: %s\n" +
                        "\tClass: %s\n" +
                        "\tLevel: %d\n" +
                        "\tExperience: %d\n" +
                        "\tAttack: %d\n" +
                        "\tDefense: %d\n" +
                        "\tHP: %d\n",
                player.getName(), player.getType(), player.getLevel(), player.getExperience(), player.getAttack(),
                player.getDefense(), player.getHp()
        );
        reportText.setText(line);
    }
}
