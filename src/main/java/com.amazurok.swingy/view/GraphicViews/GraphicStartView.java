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
    private  JRadioButton rbtnNewHero = new JRadioButton("Create a hero");
    private  JRadioButton rbtnPreviousHero = new JRadioButton("Select a previous hero.");

    public GraphicStartView(GamePlayController controller)
    {
        this.controller = controller;
    }


    private  void initStartView()
    {
        setColors();
        setBounds();
        setListeners();
        addToPanel();
    }

    private  void addToPanel()
    {
        panelWelcome.add(btnContinue);
        panelWelcome.add(rbtnNewHero);
        panelWelcome.add(rbtnPreviousHero);
        panelWelcome.add(labelWelcome);
        rbtnNewHero.setSelected(true);
        btnGroup.add(rbtnNewHero);
        btnGroup.add(rbtnPreviousHero);
    }

    private  void setListeners()
    {
        btnContinue.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

            }
        });
    }

    private  void setBounds()
    {
        labelWelcome.setBounds(180, 10 ,200,20);
        btnContinue.setBounds(200, 120 ,100,20);
        rbtnNewHero.setBounds(100,  60,200,40);
        rbtnPreviousHero.setBounds(300, 60 ,200,40);
    }

    private  void setColors()
    {
        labelWelcome.setForeground(Color.WHITE);
        rbtnNewHero.setBackground(Color.DARK_GRAY);
        rbtnPreviousHero.setBackground(Color.DARK_GRAY);
        rbtnNewHero.setForeground(Color.WHITE);
        rbtnPreviousHero.setForeground(Color.WHITE);
        panelWelcome.setBackground(Color.DARK_GRAY);
    }


    public  void displayStartView()
    {
        initStartView();

        frame.setContentPane(panelWelcome);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 200));
        frame.setResizable(false);
        frame.setLayout(null);
        frame.pack();
        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }
}
