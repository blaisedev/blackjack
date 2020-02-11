package com.blaisedev.blackjack.controls;

import com.blaisedev.blackjack.Hand;
import com.blaisedev.blackjack.players.Dealer;
import com.blaisedev.blackjack.players.Player;
import com.blaisedev.blackjack.utils.MessageUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RuleTest {

    @InjectMocks
    @Autowired
    private Rule rule;

    @Mock
    private Player player;

    @Mock
    private Hand hand;

    @Mock
    private Dealer dealer;

    @Mock
    private MessageUtility messageUtility;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        rule.hasPlayerBlackjack = false;
        rule.hasDealerBlackJack = false;
    }

    @Test
    void isHandBust() {
        boolean actual = rule.isHandBust(21);
        assertEquals(false, actual, "Expected hand not to be bust");
    }

    @Test
    void isHandBustNegtive() {
        boolean actual = rule.isHandBust(22);
        assertEquals(true, actual, "Expected hand  to be bust");
    }

    @Test
    void hasPlayerBlackjack() {
        boolean actual = rule.isHandBlackjack(21);
        assertEquals(true, actual, "Expected hand to be blackjack");
    }

    @Test
    void hasPlayerBlackjackNegative() {
        boolean actual = rule.isHandBust(21);
        assertEquals(false, actual, "Expected hand not to be blackjack");
    }

    @Test
    void isGameADraw() {
        boolean actual = rule.isGameADraw(21, 21);
        assertEquals(true, actual, "Expected game to be a draw");
    }

    @Test
    void isGameADrawNegative() {
        boolean actual = rule.isGameADraw(21, 18);
        assertEquals(false, actual, "Expected game to not be a draw");
    }

    @Test
    void isGameEndingWithBlackJack_Draw() {
        gameEndingTestSetup();
        when(hand.playerHandTotal()).thenReturn(21);
        when(hand.dealerHandTotal()).thenReturn(21);

        boolean actual = rule.isGameEndingWithBlackJack();

        assertEquals(true, rule.hasDealerBlackJack, "Expected dealer to have blackjack");
        assertEquals(true, rule.hasPlayerBlackjack, "Expected player to have blackjack");
        assertEquals(true, actual, "Expected game to end by blackJack");
    }

    @Test
    void isGameEndingWithBlackJack_Player_Win() {
        gameEndingTestSetup();
        when(hand.playerHandTotal()).thenReturn(21);
        when(hand.dealerHandTotal()).thenReturn(11);
        boolean actual = rule.isGameEndingWithBlackJack();

        assertEquals(false, rule.hasDealerBlackJack, "Expected dealer to not have blackjack");
        assertEquals(true, rule.hasPlayerBlackjack, "Expected player to have blackjack");
        assertEquals(true, actual, "Expected game to end by blackJack");

    }

    @Test
    void isGameEndingWithBlackJack_Dealer_Win() {
        gameEndingTestSetup();
        when(hand.playerHandTotal()).thenReturn(11);
        when(hand.dealerHandTotal()).thenReturn(21);
        boolean actual = rule.isGameEndingWithBlackJack();

        assertEquals(true, rule.hasDealerBlackJack, "Expected dealer to have blackjack");
        assertEquals(false, rule.hasPlayerBlackjack, "Expected player not to have blackjack");
        assertEquals(true, actual, "Expected game to end by blackJack");

    }

    @Test
    void isGameEndingWithBlackJack_Negative() {
        gameEndingTestSetup();
        when(hand.playerHandTotal()).thenReturn(11);
        when(hand.dealerHandTotal()).thenReturn(11);
        boolean actual = rule.isGameEndingWithBlackJack();

        assertEquals(false, rule.hasDealerBlackJack, "Expected dealer to not have blackjack");
        assertEquals(false, rule.hasPlayerBlackjack, "Expected player to not have blackjack");
        assertEquals(false, actual, "Expected game to continue");

    }

    @Test
    void determineIfGameIsADraw() {
        preparePlayerDealerHands();
        when(hand.playerHandTotal()).thenReturn(21);
        when(hand.dealerHandTotal()).thenReturn(21);
        boolean actual = rule.determineIfGameIsADraw();
        verify(messageUtility, atLeast(1)).formatDrawnGameMessages();
        assertEquals(true, actual, "Expected game to be draw");
    }

    @Test
    void isDealerWinner() {
        preparePlayerDealerHands();
        when(hand.playerHandTotal()).thenReturn(11);
        when(hand.dealerHandTotal()).thenReturn(20);
        boolean actual = rule.isDealerWinner();
        verify(messageUtility, atLeast(1)).formatWinningMessage(any());
        assertEquals(true, actual, "Expected dealer to be winner");
    }

    @Test
    void checkIfHandBust() {
        boolean actual = rule.checkIfHandBust(23, "DEALER");
        verify(messageUtility, atLeast(1)).determineWinnersMessage(any());
        assertEquals(true, actual, "Expected hand to be bust");

    }

    private void gameEndingTestSetup() {
        preparePlayerDealerHands();

        doNothing().when(messageUtility).formatDrawnGameMessages();
        doNothing().when(messageUtility).formatWinningMessage(any());
    }

    private void preparePlayerDealerHands() {
        when(player.getHand()).thenReturn(hand);
        when(dealer.getHand()).thenReturn(hand);
    }
}