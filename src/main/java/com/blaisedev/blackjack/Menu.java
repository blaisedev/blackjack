package com.blaisedev.blackjack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;


@Component
public class Menu {


    private boolean keepRunning = true;
    @Autowired
    private GameManager gameManager;
    @Autowired
    private ScannerUtility scannerUtility;
    private boolean isFirstGame = true;

    public void getUserOption() {
        while (keepRunning) {
            try {
                determineIfFirstGame();
            } catch (InputMismatchException ex) {
                System.out.println("Terminating application!!");
                keepRunning = false;
            }
        }
    }

    private void determineIfFirstGame() {
        if(isFirstGame) {
            buildHeaderForNewGame();
            determinePlayerSelection();
            isFirstGame = false;
        } else {
            buildHeaderForContinuedGame();
            gameManager.continueGame();
            System.out.println("Continue game");
            System.out.print(scannerUtility.getScanner().next());
            //TODO maybe have a method to leave table
        }
    }

    private void determinePlayerSelection() {
        if (scannerUtility.getScanner().nextInt() == 1) {
            gameManager.startGame();
        } else {
            System.out.println("Terminating game");
            keepRunning = false;
        }
    }

    private void buildHeaderForNewGame() {
        StringBuffer sb = buildGameBanner();
        sb.append("1. Start New Game\n");
        sb.append("2. Terminate\n");


        sb.append("Enter Option [1-2]>");
        System.out.println(sb.toString());
    }

    private void buildHeaderForContinuedGame() {
        StringBuffer sb = buildGameBanner();
        sb.append("New cards being dealt");

        System.out.println(sb.toString());
    }

    private StringBuffer buildGameBanner() {
        StringBuffer sb = new StringBuffer();
        sb.append("-------------------------------\n");
        sb.append("\t\tBlackJack\n");
        sb.append("-------------------------------\n");
        return sb;
    }
}
