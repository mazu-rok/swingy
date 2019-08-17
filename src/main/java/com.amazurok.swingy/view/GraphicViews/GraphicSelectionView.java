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

    GamePlayController controller;
    private JFrame frame;
    private JButton btnContinue;
    private JButton btnBack;
    private JPanel panel;
    private ArrayList<String> options;
    private JComboBox checkboxOptions;
    private JLabel lHeading;
    private JTextArea info;
    private JScrollPane jScrollPane;
    private String selectedHero = "-1";

    public GraphicSelectionView(GamePlayController controller) {
        this.controller = controller;
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

    private void initSelectionView(List<Person> heroes) {
        frame = new JFrame("Swingy");
        btnContinue = new JButton("Start Game");
        if (heroes.size() == 0)
            btnContinue.setEnabled(false);
        else
            btnContinue.setEnabled(true);
        btnBack = new JButton("Back");
        panel = new JPanel();
        options = getHeroesTypesAndNames(heroes);
        checkboxOptions = new JComboBox(options.toArray());
        lHeading = new JLabel("Hero Details");
        info = new JTextArea();
        jScrollPane = new JScrollPane(info);
        info.setEditable(false);
        setColors();
        setBounds();
        setListeners(heroes);
        addToPanel();
    }

    private void addToPanel() {
        panel.add(btnBack);
        panel.add(btnContinue);
        panel.add(checkboxOptions);
        panel.add(lHeading);
        panel.add(jScrollPane);
    }

    private void setBounds() {
        lHeading.setBounds(180, 10, 150, 20);
        jScrollPane.setBounds(50, 70, 350, 300);
        checkboxOptions.setBounds(50, 380, 350, 20);
        btnBack.setBounds(100, 420, 130, 20);
        btnContinue.setBounds(230, 420, 130, 20);
    }

    private void setColors() {
        info.setBackground(Color.DARK_GRAY);
        info.setForeground(Color.WHITE);
        lHeading.setForeground(Color.WHITE);
        panel.setBackground(Color.DARK_GRAY);
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
        return (details);
    }

    private void setListeners(final List<Person> heroes) {
        btnContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setInput(selectedHero);
                frame.dispose();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                controller.setInput("b");
            }
        });

        checkboxOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> details = getHeroDetails(heroes);
                info.setText(details.get(checkboxOptions.getSelectedIndex()));
                controller.setInput(heroes.get(checkboxOptions.getSelectedIndex()).getId().toString());
            }
        });
    }

    public void displaySelectionView(List<Person> heroes) {
        initSelectionView(heroes);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(450, 500));
        frame.setResizable(false);
        frame.setLayout(null);
        frame.pack();
        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }
}
