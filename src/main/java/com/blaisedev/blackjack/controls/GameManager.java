package com.blaisedev.blackjack.controls;

import com.blaisedev.blackjack.utils.MessageUtility;
import com.blaisedev.blackjack.utils.ScannerUtility;
import com.blaisedev.blackjack.players.Dealer;
import com.blaisedev.blackjack.players.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.blaisedev.blackjack.constants.BlackJackConstants.DEALER;
import static com.blaisedev.blackjack.constants.BlackJackConstants.PLAYER;

@Component
public class GameManager {
    private static final Logger log = LoggerFactory.getLogger(GameManager.class);

    private final Dealer dealer;
    private final Player player;
    private final Rule rule;
    private final ScannerUtility scannerUtility;
    private final MessageUtility messageUtility;
    private boolean isPlayerSticking = false;
    private boolean isDealersNextCard;


    @Autowired
    public GameManager(Dealer dealer, Player player, Rule rule, ScannerUtility scannerUtility, MessageUtility messageUtility) {
        this.dealer = dealer;
        this.player = player;
        this.rule = rule;
        this.scannerUtility = scannerUtility;
        this.messageUtility = messageUtility;
    }

    public void startGame() {
        dealer.prepareCardsForNewGame();
        showHandValues();
        determineNextMoveIfNotBlackJack();
    }

    public void continueGame() {
        log.info("Continuing with game");
        dealer.continuedGame();
        showHandValues();
        determineNextMoveIfNotBlackJack();
    }

    private void showHandValues() {
        log.info(player.getHand().toString());
        log.info("Player total: " + player.getHand().playerHandTotal());
        log.info("Dealer first card: " + dealer.getHand().showDealersFirstCard());
    }

    private void determineNextMoveIfNotBlackJack() {
        boolean isEnding = rule.isGameEndingWithBlackJack();
        if (!isEnding) {
            determineNextMoveInGame();
        }
        revertValuesForNewHands();
    }

    private void determineNextMoveInGame() {
        isDealersNextCard = isPlayerSticking || rule.hasPlayerBlackjack;
        if (isDealersNextCard) {
            boolean isBust = checkIfHandBust(dealer.getHand().dealerHandTotal(), DEALER);
            if (!isBust) {
                checkIfDealerWins();
            }
        } else {
            determinePlayersMove();
        }
    }

    private void checkIfDealerWins() {
        boolean isDealerWinner = isDealerWinner();
        if (!isDealerWinner) {
            newCardToDealerIfNotADraw();
        }
    }

    private void newCardToDealerIfNotADraw() {
        boolean isDraw = determineIfGameIsADraw();
        if (!isDraw) {
            proceedWithDealersNextMove();
        }
    }

    private void determinePlayersMove() {
        boolean isBust = checkIfHandBust(player.getHand().playerHandTotal(), PLAYER);
        if (!isBust) {
            askPlayerToSelectNextMove();
            determineNextMoveInGame();
        }
    }

    private void proceedWithDealersNextMove() {
        log.info("Adding card to Dealer hand!");
        dealer.dealNewCardToUser(DEALER);
        log.info(dealer.getHand().toString());
        log.info("Dealer total: " + dealer.getHand().dealerHandTotal());
        determineNextMoveInGame();
    }

    private void askPlayerToSelectNextMove() {
        int playerChoice = retrievePlayersNextMove();
        boolean isHitSelected = playerChoice == 1;
        if (isHitSelected) {
            log.info("Player Hit!!!!");
            dealer.dealNewCardToUser(PLAYER);
            log.info(player.getHand().toString());
            log.info("Player total: " + player.getHand().playerHandTotal());
            rule.hasPlayerBlackJack();
        } else {
            log.info("Player Has Decided to Stick");
            log.info("Player total: " + player.getHand().playerHandTotal());
            isPlayerSticking = true;
        }

    }

    private int retrievePlayersNextMove() {
        log.info("If you wish to Hit (press 1), Stick (press any other numeral)");
        log.warn("Warning any other key will Terminate");
        return scannerUtility.getScanner().nextInt();
    }

    private boolean determineIfGameIsADraw() {
        boolean isDraw = rule.isGameADraw(player.getHand().playerHandTotal(), dealer.getHand().dealerHandTotal());
        if (isDraw) {
            messageUtility.formatDrawnGameMessages();
            revertValuesForNewHands();
        }
        return isDraw;
    }

    private boolean isDealerWinner() {
        int playerHandTotal = player.getHand().playerHandTotal();
        int dealerHandTotal = dealer.getHand().dealerHandTotal();
        boolean isDealerWinner = playerHandTotal < dealerHandTotal;
        if (isDealerWinner) {
            messageUtility.formatWinningMessage(DEALER);
            revertValuesForNewHands();
        }
        return isDealerWinner;
    }

    private void revertValuesForNewHands() {
        dealer.clearHands();
        isPlayerSticking = false;
        isDealersNextCard = false;
        rule.hasPlayerBlackjack = false;
        rule.hasPlayerBlackjack = false;
    }

    private boolean checkIfHandBust(int handTotal, String user) {
        boolean bust = rule.isHandBust(handTotal);
        if (bust) {
            messageUtility.determineWinnersMessage(user);
            revertValuesForNewHands();
        }
        return bust;
    }

}

