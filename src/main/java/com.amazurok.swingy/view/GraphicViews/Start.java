package com.amazurok.swingy.view.GraphicViews;

import com.amazurok.swingy.controller.GamePlayController;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Start {

    GamePlayController controller;

    private JRadioButton create;
    @Getter
    private JPanel startPanel;
    private JRadioButton select;
    private JButton btnContinue;


    public Start() {
        btnContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
