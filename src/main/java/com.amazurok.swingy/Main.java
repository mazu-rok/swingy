package com.amazurok.swingy;

import com.amazurok.swingy.controller.GamePlayController;
import com.amazurok.swingy.exceptions.IllegalInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) throws IllegalInputException {
        Logger log = LoggerFactory.getLogger(Main.class);

        if (args.length == 1) {
            GamePlayController gamePlayController = new GamePlayController(args[0]);
            gamePlayController.displayGame();
        } else {
            log.error("Only one argument is allowed");
        }
    }
}
