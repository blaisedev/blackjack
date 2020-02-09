package com.blaisedev.blackjack;

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
    private boolean isPlayerSticking = false;
    private boolean isDealersNextCard;
    private boolean hasPlayerBlackjack;

    @Autowired
    public GameManager(Dealer dealer, Player player, Rule rule, ScannerUtility scannerUtility) {
        this.dealer = dealer;
        this.player = player;
        this.rule = rule;
        this.scannerUtility = scannerUtility;
    }

    public void startGame() {
        dealer.prepareCardsForNewGame();
        showHandValues();
        checkIfPlayerTotalIsBlackJack();
    }

    public void continueGame() {
        log.info("Continuing with game");
        dealer.continuedGame();
        showHandValues();
        checkIfPlayerTotalIsBlackJack();
    }

    private void showHandValues() {
        log.info(player.getHand().toString());
        log.info("Player total: " + player.getHand().playerHandTotal());
        log.info("Dealer first card: " + dealer.getHand().showDealersFirstCard());
    }

    private void checkIfPlayerTotalIsBlackJack() {
        hasPlayerBlackJack();
        isDealersNextCard = hasPlayerBlackjack || isPlayerSticking;
        determineNextMoveInGame(isDealersNextCard);
    }

    private void determineNextMoveInGame(boolean isDealersNextCard) {
        if (isDealersNextCard) {
            //TODO check first if bust then is winner then draw
            boolean isDraw = determineIfGameIsADraw();
            if (!isDraw) {
                proceedWithDealersNextMove();
            }
        } else {
            boolean isBust = checkIfHandBust(player.getHand().playerHandTotal(), PLAYER);
            if (!isBust) {
                askPlayerToSelectNextMove();
                checkIfPlayerTotalIsBlackJack();
            }
        }
    }

    private void proceedWithDealersNextMove() {
        log.info("Adding card to Dealer hand");
        dealer.dealNewCardToUser(DEALER);
        log.info(dealer.getHand().toString());
        log.info("Dealer total: " + dealer.getHand().dealerHandTotal());
        compareDealersHandToPlayers();
    }

    private void askPlayerToSelectNextMove() {
        int playerChoice = retrievePlayersNextMove();
        boolean isHitSelected = playerChoice == 1;
        if (isHitSelected) {
            log.info("Player Hit!!!!!");
            dealer.dealNewCardToUser(PLAYER);
            log.info(player.getHand().toString());
            log.info("Player total: " + player.getHand().playerHandTotal());
        } else {
            log.info("Player Has Decided to Stick");
            log.info("Player total: " + player.getHand().playerHandTotal());
            isPlayerSticking = true;
        }

    }

    private int retrievePlayersNextMove() {
        log.info("If you wish to Hit (press 1), Stick (press any other numeral");
        log.info("Warning any other key will Terminate");
        return scannerUtility.getScanner().nextInt();
    }

    private void hasPlayerBlackJack() {
        hasPlayerBlackjack = rule.hasPlayerBlackjack(player.getHand().playerHandTotal());
        if (hasPlayerBlackjack) {
            log.info("Player has hit BlackJack");
        }
    }

    private void compareDealersHandToPlayers(){
        boolean isBust = checkIfHandBust(dealer.getHand().dealerHandTotal(), DEALER);
        if (!isBust) {
            isDealerWinner();
        }
    }

    private boolean determineIfGameIsADraw() {
        boolean isDraw = rule.isGameADraw(player.getHand().playerHandTotal(), dealer.getHand().dealerHandTotal());
        if (isDraw) {
            log.info("Game ended in a DRAW!!!!");
            log.info(dealer.getHand().toString());
            log.info("Dealer total: " + dealer.getHand().dealerHandTotal());
            log.info(player.getHand().toString());
            log.info("Player total: " + player.getHand().playerHandTotal());
            revertValuesForNewHands();
        }
        return isDraw;
    }

    private void isDealerWinner() {
        boolean isDealerWinner = player.getHand().playerHandTotal() < dealer.getHand().dealerHandTotal();
        if (isDealerWinner) {
            log.info("DEALER WINS");
            log.info("Dealer total: " + dealer.getHand().dealerHandTotal());
            log.info("Player total: " + player.getHand().playerHandTotal());
            revertValuesForNewHands();
        } else {
            determineNextMoveInGame(isDealersNextCard);
        }
    }

    private void revertValuesForNewHands() {
        dealer.clearHands();
        isPlayerSticking = false;
        isDealersNextCard = false;
    }

    private boolean checkIfHandBust(int handTotal, String user) {
        boolean bust = rule.isHandBust(handTotal);
        if (bust) {
            determineWinnersMessage(user);
            revertValuesForNewHands();
        }
        return bust;
    }

    private void determineWinnersMessage(String user) {
        if (user.equals(PLAYER)) {
            log.info(PLAYER + " Bust!!!");
            log.info("Players total: " + player.getHand().playerHandTotal());
            log.info(DEALER + " Wins!!");
        } else {
            log.info(DEALER + " Bust!!!");
            log.info("Dealers total: " + dealer.getHand().dealerHandTotal());
            log.info(PLAYER + " Wins!!");
        }
    }

}
