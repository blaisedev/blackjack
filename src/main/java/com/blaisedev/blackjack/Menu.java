package com.blaisedev.blackjack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;

@Component
public class Menu {

    private static final Logger log = LoggerFactory.getLogger(Menu.class);
    private boolean keepRunning = true;
    private final GameManager gameManager;
    private final ScannerUtility scannerUtility;
    private boolean isFirstGame = true;

    @Autowired
    public Menu(GameManager gameManager, ScannerUtility scannerUtility) {
        this.gameManager = gameManager;
        this.scannerUtility = scannerUtility;
    }

    public void getUserOption() {
        while (keepRunning) {
            try {
                determineIfFirstGame();
            } catch (InputMismatchException ex) {
                log.info("Terminating application!!");
                keepRunning = false;
            }
        }
    }

    private void determineIfFirstGame() {
        if (isFirstGame) {
            buildHeaderForNewGame();
            determinePlayerSelection();
            isFirstGame = false;
        } else {
            buildHeaderForContinuedGame();
            gameManager.continueGame();
            log.info("Continue game");
            System.out.print(scannerUtility.getScanner().next());
            //TODO maybe have a method to leave table
        }
    }

    private void determinePlayerSelection() {
        if (scannerUtility.getScanner().nextInt() == 1) {
            gameManager.startGame();
        } else {
            log.info("Terminating game");
            keepRunning = false;
        }
    }

    private void buildHeaderForNewGame() {
        StringBuffer sb = buildGameBanner();
        sb.append("1. Start New Game\n");
        sb.append("2. Terminate\n");

        sb.append("Enter Option [1-2]>");
        log.info(sb.toString());
    }

    private void buildHeaderForContinuedGame() {
        StringBuffer sb = buildGameBanner();
        sb.append("New cards being dealt");

        log.info(sb.toString());
    }

    private StringBuffer buildGameBanner() {
        StringBuffer sb = new StringBuffer();
        sb.append("-------------------------------\n");
        sb.append("\t\tBlackJack\n");
        sb.append("-------------------------------\n");
        return sb;
    }
}
