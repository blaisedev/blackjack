package com.blaisedev.blackjack;

import com.blaisedev.blackjack.players.Dealer;
import com.blaisedev.blackjack.players.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.blaisedev.blackjack.constants.BlackJackConstants.DEALER;
import static com.blaisedev.blackjack.constants.BlackJackConstants.PLAYER;

@Component
public class GameManager {

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

    public void startGame(){
       dealer.prepareCardsForNewGame();
       showHandValues();
       checkIfPlayerTotalIsBlackJack();
    }

    public void continueGame(){
        System.out.println("Continuing with game");
        dealer.continuedGame();
        showHandValues();
        checkIfPlayerTotalIsBlackJack();
    }

    private void showHandValues(){
        System.out.println(player.getHand().toString());
        System.out.println("Player total: " +  player.getHand().playerHandTotal());
        System.out.println("Dealer first card: " + dealer.getHand().showDealersFirstCard());
    }


    private void checkIfPlayerTotalIsBlackJack() {
        hasPlayerBlackJack();
        isDealersNextCard = hasPlayerBlackjack || isPlayerSticking;
        determineNextMoveInGame(isDealersNextCard);
    }

    private void determineNextMoveInGame(boolean isDealersNextCard) {
        if(isDealersNextCard) {
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
        System.out.println("Adding card to Dealer hand");
        dealer.dealNewCardToUser(DEALER);
        System.out.println(dealer.getHand().toString());
        System.out.println("Dealer total: " + dealer.getHand().dealerHandTotal());
        compareDealersHandToPlayers();
    }

    private void askPlayerToSelectNextMove() {
        int playerChoice = retrievePlayersNextMove();
        boolean isHitSelected = playerChoice == 1;
        if (isHitSelected) {
            System.out.println("Player Hit!!!!!");
            dealer.dealNewCardToUser(PLAYER);
            System.out.println(player.getHand().toString());
            System.out.println("Player total: " + player.getHand().playerHandTotal());
        } else {
            System.out.println("Player Has Decided to Stick");
            System.out.println("Player total: " + player.getHand().playerHandTotal());
            isPlayerSticking = true;
        }

    }

    private int retrievePlayersNextMove() {
        System.out.println("If you wish to Hit (press 1), Stick (press any other numeral");
        System.out.println("Warning any other key will Terminate");
        return scannerUtility.getScanner().nextInt();
    }

    private void hasPlayerBlackJack() {
        hasPlayerBlackjack = rule.hasPlayerBlackjack(player.getHand().playerHandTotal());
        if (hasPlayerBlackjack){
            System.out.println("Player has hit BlackJack");
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
            System.out.println("Game ended in a DRAW!!!!");
            System.out.println(dealer.getHand().toString());
            System.out.println("Dealer total: " + dealer.getHand().dealerHandTotal());
            System.out.println(player.getHand().toString());
            System.out.println("Player total: " + player.getHand().playerHandTotal());
            revertValuesForNewHands();
        }
        return isDraw;
    }

    private void isDealerWinner() {
        boolean isDealerWinner = player.getHand().playerHandTotal() < dealer.getHand().dealerHandTotal();
        if(isDealerWinner) {
            System.out.println("DEALER WINS");
            System.out.println("Dealer total: " + dealer.getHand().dealerHandTotal());
            System.out.println("Player total: " + player.getHand().playerHandTotal());
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

    private boolean checkIfHandBust(int handTotal, String user){
        boolean bust = rule.isHandBust(handTotal);
        if (bust) {
            determineWinnersMessage(user);
            revertValuesForNewHands();
        }
        return bust;
    }

    private void determineWinnersMessage(String user) {
        if (user.equals(PLAYER)) {
            System.out.println(PLAYER + " Bust!!!");
            System.out.println("Players total: " + player.getHand().playerHandTotal());
            System.out.println(DEALER + " Wins!!");
        } else {
            System.out.println(DEALER + " Bust!!!");
            System.out.println("Dealers total: " + dealer.getHand().dealerHandTotal());
            System.out.println(PLAYER + " Wins!!");
        }
    }

}
