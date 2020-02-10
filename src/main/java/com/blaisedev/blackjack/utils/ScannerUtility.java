package com.blaisedev.blackjack.utils;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ScannerUtility {

    private Scanner scanner;

    public ScannerUtility() {
        scanner = new Scanner(System.in);
    }

    public Scanner getScanner() {
        return scanner;
    }
}
