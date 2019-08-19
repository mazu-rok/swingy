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

    GamePlayController controller;

    private JFrame frame = new JFrame("Swingy");
    private  ButtonGroup btnGroup = new ButtonGroup();
    private  JButton btnContinue = new JButton("Continue");
    private  JPanel panelWelcome = new JPanel();
    private  JLabel labelWelcome = new JLabel("Welcome to Swingy");
    private  JRadioButton createHero = new JRadioButton("Create a hero");
    private  JRadioButton selectHero = new JRadioButton("Select a previous hero.");

    public GraphicStartView(GamePlayController controller)
    {
        this.controller = controller;

    }


    private  void initStartView()
    {
        setBounds();
        setListeners();
        addToPanel();
    }

    private  void addToPanel()
    {
        panelWelcome.add(btnContinue);
        panelWelcome.add(createHero);
        panelWelcome.add(selectHero);
        panelWelcome.add(labelWelcome);
        createHero.setSelected(true);
        btnGroup.add(createHero);
        btnGroup.add(selectHero);
    }

    private  void setListeners()
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

    private  void setBounds()
    {
        labelWelcome.setBounds(140, 10 ,200,20);
        createHero.setBounds(20,  60,200,40);
        selectHero.setBounds(200, 60 ,200,40);
        btnContinue.setBounds(160, 120 ,100,20);

    }

    public  void displayStartView()
    {
        initStartView();

        frame.setContentPane(panelWelcome);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 200));
        frame.setResizable(false);
        frame.setLayout(null);
        frame.pack();
        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }
}
