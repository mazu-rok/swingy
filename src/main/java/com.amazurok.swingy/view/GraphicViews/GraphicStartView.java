package com.amazurok.swingy.view.GraphicViews;

import com.amazurok.swingy.controller.GamePlayController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicStartView {
    Logger log = LoggerFactory.getLogger(GraphicStartView.class);

    private GamePlayController controller;

    private JFrame frame = new JFrame("Swingy");
    private  ButtonGroup btnGroup = new ButtonGroup();
    private  JButton btnContinue = new JButton("Continue");
    private  JPanel panelStart = new JPanel();
    private  JLabel labelWelcome = new JLabel("Welcome to Swingy");
    private  JRadioButton createHero = new JRadioButton("Create a hero");
    private  JRadioButton selectHero = new JRadioButton("Select a previous hero.");

    public GraphicStartView(GamePlayController controller)
    {
        this.controller = controller;

        // Set size and position
        labelWelcome.setBounds(140, 10 ,200,20);
        createHero.setBounds(20,  40,200,40);
        selectHero.setBounds(200, 40 ,200,40);
        btnContinue.setBounds(150, 90 ,100,40);

        // Put items to panel
        panelStart.add(btnContinue);
        panelStart.add(createHero);
        panelStart.add(selectHero);
        panelStart.add(labelWelcome);
        createHero.setSelected(true);
        btnGroup.add(createHero);
        btnGroup.add(selectHero);

        // Set up frame
        frame.setContentPane(panelStart);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 160));
        frame.setResizable(false);
        frame.setLayout(null);
        frame.pack();
        frame.setLocation(500, 500);

        startListeners();

    }

    private void startListeners()
    {
        btnContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (createHero.isSelected()) {
                    controller.handleInput("1");
                } else {
                    controller.handleInput("2");
                }
                controller.displayGame();
                frame.dispose();
            }
        });
    }



    public  void displayStartView()
    {
        frame.setVisible(true);
    }
}
