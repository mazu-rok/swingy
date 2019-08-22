package com.amazurok.swingy.view.GraphicViews;

import com.amazurok.swingy.controller.CharacterController;
import com.amazurok.swingy.controller.GamePlayController;
import com.amazurok.swingy.exceptions.IllegalInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GraphicCreateView {
    Logger log = LoggerFactory.getLogger(GraphicCreateView.class);

    private GamePlayController gamePlayController;
    private CharacterController characterController;

    private JFrame frame = new JFrame("Swingy");
    private JButton btnContinue = new JButton("Start Game");
    private JButton btnBack = new JButton("Back");
    private JRadioButton defaultPerson = new JRadioButton("Default hero");
    private JRadioButton customPerson = new JRadioButton("Custom hero");
    private JComboBox typeComboBox;
    private JLabel levelLabel = new JLabel("Level:");
    private JLabel attackLabel = new JLabel("Attack:");
    private JLabel defenseLabel = new JLabel("Defense:");
    private JLabel hpLabel = new JLabel("HP");
    private JLabel errorLabel = new JLabel();
    private JTextField nameInput = new JTextField();
    private JTextField levelInput = new JTextField();
    private JTextField attackInput = new JTextField();
    private JTextField defenseInput = new JTextField();
    private JTextField hpInput = new JTextField();

    public GraphicCreateView(GamePlayController gamePlayController, CharacterController characterController) {
        this.gamePlayController = gamePlayController;
        this.characterController = characterController;
        String[] options = {"Elf", "Knight", "Magician", "Orc"};
        typeComboBox = new JComboBox(options);

        ButtonGroup playerGroup = new ButtonGroup();
        playerGroup.add(customPerson);
        playerGroup.add(defaultPerson);
        defaultPerson.setSelected(true);


        // Set size and position
        JLabel headingLabel = new JLabel("Create a Hero");
        headingLabel.setBounds(140, 10, 150, 20);
        errorLabel.setBounds(50, 17, 400, 60);
        errorLabel.setForeground(Color.red);
        nameInput.setBounds(180, 60, 150, 20);
        JLabel nameLabel = new JLabel("Hero name:");
        nameLabel.setBounds(40, 60, 100, 20);
        JLabel typeLabel = new JLabel("Hero type:");
        typeLabel.setBounds(40, 100, 100, 20);
        typeComboBox.setBounds(180, 100, 150, 20);
        levelLabel.setBounds(40, 140, 100, 20);
        levelInput.setBounds(180, 140, 150, 20);
        attackLabel.setBounds(40, 180, 100, 20);
        attackInput.setBounds(180, 180, 150, 20);
        defenseLabel.setBounds(40, 220, 100, 20);
        defenseInput.setBounds(180, 220, 150, 20);
        hpLabel.setBounds(40, 260, 150, 20);
        hpInput.setBounds(180, 260, 150, 20);
        defaultPerson.setBounds(40, 300, 140, 20);
        customPerson.setBounds(180, 300, 150, 20);
        btnBack.setBounds(50, 330, 130, 40);
        btnContinue.setBounds(200, 330, 130, 40);


        // Add to panel
        JPanel panel = new JPanel();
        panel.add(errorLabel);
        panel.add(customPerson);
        panel.add(defaultPerson);
        panel.add(headingLabel);
        panel.add(nameLabel);
        panel.add(nameInput);
        panel.add(typeLabel);
        panel.add(typeComboBox);
        panel.add(levelLabel);
        panel.add(levelInput);
        panel.add(attackLabel);
        panel.add(attackInput);
        panel.add(defenseLabel);
        panel.add(defenseInput);
        panel.add(hpLabel);
        panel.add(hpInput);
        panel.add(btnBack);
        panel.add(btnContinue);

        // Set up frame
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.setResizable(false);
        frame.setLayout(null);
        frame.pack();
        frame.setLocation(500, 500);

        hideAddictionalInfo();
        startListeners();
    }

    private void hideAddictionalInfo() {
        levelInput.setVisible(false);
        levelLabel.setVisible(false);
        attackInput.setVisible(false);
        attackLabel.setVisible(false);
        defenseInput.setVisible(false);
        defenseLabel.setVisible(false);
        hpInput.setVisible(false);
        hpLabel.setVisible(false);
        errorLabel.setVisible(false);
        frame.setResizable(false);
    }

    private void showAddictionalInfo() {
        levelInput.setVisible(true);
        levelLabel.setVisible(true);
        attackInput.setVisible(true);
        attackLabel.setVisible(true);
        defenseInput.setVisible(true);
        defenseLabel.setVisible(true);
        hpInput.setVisible(true);
        hpLabel.setVisible(true);
        errorLabel.setVisible(true);
    }

    private boolean isInputValid(String level, String attack, String defense, String hp) {
        if (!(level.matches("\\d+") && attack.matches("\\d+") && defense.matches("\\d+") &&
                hp.matches("\\d+"))) {
            errorLabel.setText("Non-integer in level, attack, defense or hp");
            return false;
        }
        return true;
    }

    private void startListeners() {
        btnContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (customPerson.isSelected()) {
                    if (isInputValid(levelInput.getText(), attackInput.getText(), defenseInput.getText(), hpInput.getText())) {
                        try {
                            characterController.createPerson(nameInput.getText(), typeComboBox.getSelectedItem().toString(), Integer.parseInt(levelInput.getText()),
                                    Integer.parseInt(attackInput.getText()), Integer.parseInt(defenseInput.getText()),
                                    Integer.parseInt(hpInput.getText()));
                            gamePlayController.handleInput("");
                            gamePlayController.displayGame();
                        } catch (Exception ex) {
                            log.error(ex.getMessage());
                        }
                    }

                } else {
                    try {
                        characterController.createDefaultPerson(nameInput.getText(), typeComboBox.getSelectedItem().toString());
                        frame.dispose();
                        gamePlayController.handleInput("");
                        gamePlayController.displayGame();
                    } catch (Exception ex) {
                        log.error(ex.getMessage());
                    }
                }

            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                gamePlayController.handleInput("b");
                gamePlayController.displayGame();
            }
        });

        customPerson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddictionalInfo();
            }
        });

        defaultPerson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideAddictionalInfo();
            }
        });
    }

    public void displayCreatePlayerView() {
        frame.setVisible(true);
    }
}
