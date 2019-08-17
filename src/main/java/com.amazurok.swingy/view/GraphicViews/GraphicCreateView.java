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

    private  JFrame frame;
    private  JButton btnContinue;
    private  JButton btnBack;
    private  JPanel panel;
    private  JRadioButton defaultPerson, customPerson;
    private  ButtonGroup playerGroup;
    private  JComboBox typeComboBox;
    private  JLabel headingLabel, nameLabel, typeLabel, levelLabel, atkLabel, defLabel, hpLabel, errorLabel;
    private  JTextField nameText, levelText, attackText, defenseText, hpText;

    public GraphicCreateView(GamePlayController gamePlayController, CharacterController characterController) {
        this.gamePlayController = gamePlayController;
        this.characterController = characterController;
    }

    private   void initCreatePlayerView()
    {
        frame = new JFrame("Swingy");
        btnContinue = new JButton("Start Game");
        btnBack = new JButton("Back");
        panel = new JPanel();
        String[] options  = {"Elf", "Knight", "Magician", "Orc"};
        typeComboBox = new JComboBox(options);
        headingLabel = new JLabel("Create a Hero");
        typeLabel = new JLabel("Hero class:"); errorLabel = new JLabel("");
        nameLabel = new JLabel("Hero name:"); levelLabel = new JLabel("level:");
        atkLabel = new JLabel("attack:");
        defLabel = new JLabel("defense:"); hpLabel = new JLabel("hp");
        nameText = new JTextField(""); levelText = new JTextField("");
        attackText = new JTextField(""); defenseText = new JTextField(""); hpText = new JTextField("");
        playerGroup = new ButtonGroup();
        customPerson = new JRadioButton("custom hero"); defaultPerson = new JRadioButton("default hero");
        playerGroup.add(customPerson); playerGroup.add(defaultPerson);
        defaultPerson.setSelected(true);
        setdefaultPersonBounds();
        setColors();
        setListeners();
        addToPanel();
    }

    private  void addToPanel()
    {
        panel.add(btnBack);
        panel.add(btnContinue);
        panel.add(customPerson);
        panel.add(defaultPerson);
        panel.add(headingLabel);
        panel.add(nameLabel); panel.add(nameText);
        panel.add(typeLabel); panel.add(typeComboBox);
        panel.add(levelLabel); panel.add(levelText);
        panel.add(atkLabel); panel.add(attackText);
        panel.add(defLabel); panel.add(defenseText);
        panel.add(hpLabel); panel.add(hpText);
        panel.add(errorLabel);
    }

    private  boolean errors(String level, String atk, String def, String hp)
    {
        if (!(level.matches("-?\\d+") && atk.matches("-?\\d+") && def.matches("-?\\d+") &&
                hp.matches("-?\\d+") ))
        {
            errorLabel.setText("non-integer in level, attack, defense or hp");
            return (true);
        }
        return (false);
    }

    private  void setListeners()
    {
        btnContinue.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (customPerson.isSelected())
                {
                    if (!errors(levelText.getText(), attackText.getText(), defenseText.getText(), hpText.getText()))
                    {
                        try {
                            characterController.createPerson(nameText.getText(), typeComboBox.getSelectedItem().toString(), Integer.parseInt(levelLabel.getText()),
                                    Integer.parseInt(attackText.getText()), Integer.parseInt(defenseText.getText()),
                                    Integer.parseInt(hpText.getText()));
                        } catch (Exception ex) {
                            log.error(ex.getMessage());
                        }
                    }

                }
                else
                {
                    try {
                        characterController.createDefaultPerson(nameText.getText(), typeComboBox.getSelectedItem().toString());
                        frame.dispose();
                    } catch (IllegalInputException ex) {
                        log.error(ex.getMessage());
                    }
                }

            }
        });

        btnBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
                gamePlayController.setInput("b");
            }
        });

        customPerson.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setCustomPersonBounds();
            }
        });

        defaultPerson.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setdefaultPersonBounds();
            }
        });
    }

    private  void setColors()
    {
        headingLabel.setForeground(Color.WHITE);
        errorLabel.setForeground(Color.RED);
        nameText.setBackground(Color.DARK_GRAY);
        nameText.setCaretColor(Color.WHITE);
        nameText.setForeground(Color.WHITE);
        nameLabel.setForeground(Color.WHITE);
        typeLabel.setForeground(Color.WHITE);
        levelText.setForeground(Color.WHITE);
        levelText.setCaretColor(Color.WHITE);
        attackText.setForeground(Color.WHITE);
        attackText.setCaretColor(Color.WHITE);
        defenseText.setForeground(Color.WHITE);
        defenseText.setCaretColor(Color.WHITE);
        hpText.setForeground(Color.WHITE);
        hpText.setCaretColor(Color.WHITE);
        levelLabel.setForeground(Color.WHITE);
        atkLabel.setForeground(Color.WHITE);
        defLabel.setForeground(Color.WHITE);
        hpLabel.setForeground(Color.WHITE);
        levelText.setBackground(Color.DARK_GRAY);
        attackText.setBackground(Color.DARK_GRAY);
        defenseText.setBackground(Color.DARK_GRAY);
        hpText.setBackground(Color.DARK_GRAY);
        panel.setBackground(Color.DARK_GRAY);
        customPerson.setBackground(Color.DARK_GRAY);
        customPerson.setForeground(Color.WHITE);
        defaultPerson.setBackground(Color.DARK_GRAY);
        defaultPerson.setForeground(Color.WHITE);
    }

    private  void setdefaultPersonBounds()
    {
        headingLabel.setBounds(140, 10 ,150,20);
        btnBack.setBounds(50, 220 ,130,20);
        btnContinue.setBounds(200, 220 ,130,20);
        errorLabel.setBounds(50, 180,300,40);
        nameText.setBounds(180, 60, 150, 20);
        nameLabel.setBounds(40, 60, 100, 20);
        typeLabel.setBounds(40, 100, 100, 20);
        typeComboBox.setBounds(180, 100, 150, 20);
        defaultPerson.setBounds(40, 140, 140,20);
        customPerson.setBounds(180, 140, 150,20);
        levelText.setVisible(false); levelLabel.setVisible(false);
        attackText.setVisible(false); atkLabel.setVisible(false);
        defenseText.setVisible(false); defLabel.setVisible(false);
        hpText.setVisible(false); hpLabel.setVisible(false);
        errorLabel.setVisible(false);
        frame.setPreferredSize(new Dimension(400, 500));
        frame.setResizable(false);
    }

    private  void setCustomPersonBounds()
    {
        headingLabel.setBounds(140, 10 ,150,20);
        btnBack.setBounds(50, 420,130,20);
        btnContinue.setBounds(200, 420 ,130,20);
        errorLabel.setBounds(50, 340,400,60);
        nameText.setBounds(180, 60, 150, 20);
        nameLabel.setBounds(40, 60, 100, 20);
        typeLabel.setBounds(40, 100, 100, 20);
        typeComboBox.setBounds(180, 100, 150, 20);
        levelLabel.setBounds(40, 140, 100, 20);
        levelText.setBounds(180, 140, 150,20);
        atkLabel.setBounds(40, 180, 100, 20);
        attackText.setBounds(180, 180, 150,20);
        defLabel.setBounds(40, 220, 100, 20);
        defenseText.setBounds(180, 220, 150,20);
        hpLabel.setBounds(40, 260, 150, 20);
        hpText.setBounds(180, 260, 150,20);
        defaultPerson.setBounds(40, 300, 140,20);
        customPerson.setBounds(180, 300, 150,20);

        levelText.setVisible(true); levelLabel.setVisible(true);
        attackText.setVisible(true); atkLabel.setVisible(true);
        defenseText.setVisible(true); defLabel.setVisible(true);
        hpText.setVisible(true); hpLabel.setVisible(true);
        errorLabel.setVisible(true);
    }

    public  void displayCreatePlayerView()
    {
        initCreatePlayerView();
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.pack();
        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);


    }
}
