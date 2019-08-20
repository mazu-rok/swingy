package com.amazurok.swingy.view.GraphicViews;

import com.amazurok.swingy.controller.GamePlayController;
import com.amazurok.swingy.model.characters.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GraphicSelectionView {
    Logger log = LoggerFactory.getLogger(GraphicSelectionView.class);

    private GamePlayController controller;
    private JFrame frame = new JFrame("Swingy");
    private JButton btnContinue = new JButton("Start Game");
    private JButton btnBack = new JButton("Back");
    private JComboBox checkboxOptions = new JComboBox();
    private JTextArea info = new JTextArea();

    public GraphicSelectionView(GamePlayController controller) {
        this.controller = controller;
        JScrollPane jScrollPane = new JScrollPane(info);
        this.info.setEditable(false);

        // Set size and position
        JLabel lHeading = new JLabel("Hero Details:");
        lHeading.setBounds(180, 10, 140, 20);
        jScrollPane.setBounds(50, 30, 350, 150);
        checkboxOptions.setBounds(50, 180, 350, 30);
        btnBack.setBounds(50, 220, 150, 40);
        btnContinue.setBounds(250, 220, 150, 40);

        // Put items to panel
        JPanel panel = new JPanel();
        panel.add(btnBack);
        panel.add(btnContinue);
        panel.add(checkboxOptions);
        panel.add(lHeading);
        panel.add(jScrollPane);

        // Set up frame
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(450, 300));
        frame.setResizable(false);
        frame.setLayout(null);
        frame.pack();
        frame.setLocation(500, 500);
    }

    private ArrayList<String> getHeroesTypesAndNames(List<Person> heroes) {
        ArrayList<String> details = new ArrayList<>();
        int index = 0;
        for (Person person : heroes) {
            index++;
            String detail = index + ". " + person.getType() + " " + person.getName();
            details.add(detail);
        }
        return details;
    }

    private ArrayList<String> getHeroDetails(List<Person> heroes)
    {
        ArrayList<String> details = new ArrayList<>();
        for(Person player : heroes)
        {
            String heroInfo = String.format(
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
            details.add(heroInfo);
        }
        return details;
    }

    private void startListeners(final List<Person> heroes) {
        btnContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleInput(heroes.get(checkboxOptions.getSelectedIndex()).getId().toString());
                frame.dispose();
                controller.displayGame();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                controller.handleInput("b");
                controller.displayGame();
            }
        });

        checkboxOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> heroesDetail = getHeroDetails(heroes);
                info.setText(heroesDetail.get(checkboxOptions.getSelectedIndex()));
            }
        });
    }

    public void displaySelectionView(List<Person> heroes) {
        if (heroes.size() == 0)
            btnContinue.setEnabled(false);
        else
            btnContinue.setEnabled(true);
        ArrayList<String> options = getHeroesTypesAndNames(heroes);
        checkboxOptions.setModel(new DefaultComboBoxModel<>(options.toArray()));
        startListeners(heroes);
        frame.setVisible(true);
    }
}
