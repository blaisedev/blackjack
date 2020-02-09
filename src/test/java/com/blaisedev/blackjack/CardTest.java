package com.blaisedev.blackjack;

import com.blaisedev.blackjack.card.Card;
import com.blaisedev.blackjack.card.CardRank;
import com.blaisedev.blackjack.card.CardSuit;
import com.blaisedev.blackjack.constants.BlackJackConstants;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class CardTest {

    @InjectMocks
    @Autowired
    private Card card;

    @Mock
    @Autowired
    private Hand hand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void determineCardValueTwo() {
        card = new Card(CardSuit.CLUBS, CardRank.TWO);
        card.applyCardValue(new Hand(), BlackJackConstants.PLAYER);
        assertEquals(2, card.getValue(), "Expected Value not equal to 2");
    }

    @Test
    void determineCardValueThree() {
        card = new Card(CardSuit.CLUBS, CardRank.THREE);
        card.applyCardValue(new Hand(), BlackJackConstants.PLAYER);
        assertEquals(3, card.getValue(), "Expected Value not equal to 3");
    }

    @Test
    void determineCardValueFour() {
        card = new Card(CardSuit.CLUBS, CardRank.FOUR);
        card.applyCardValue(new Hand(), BlackJackConstants.PLAYER);
        assertEquals(4, card.getValue(), "Expected Value not equal to 4");
    }

    @Test
    void determineCardValueFive() {
        card = new Card(CardSuit.CLUBS, CardRank.FIVE);
        card.applyCardValue(new Hand(), BlackJackConstants.PLAYER);
        assertEquals(5, card.getValue(), "Expected Value not equal to 5");
    }

    @Test
    void determineCardValueSix() {
        card = new Card(CardSuit.CLUBS, CardRank.SIX);
        card.applyCardValue(new Hand(), BlackJackConstants.PLAYER);
        assertEquals(6, card.getValue(), "Expected Value not equal to 6");
    }

    @Test
    void determineCardValueSeven() {
        card = new Card(CardSuit.CLUBS, CardRank.SEVEN);
        card.applyCardValue(new Hand(), BlackJackConstants.PLAYER);
        assertEquals(7, card.getValue(), "Expected Value not equal to 7");
    }

    @Test
    void determineCardValueEight() {
        card = new Card(CardSuit.CLUBS, CardRank.EIGHT);
        card.applyCardValue(new Hand(), BlackJackConstants.PLAYER);
        assertEquals(8, card.getValue(), "Expected Value not equal to 8");
    }

    @Test
    void determineCardValueNine() {
        card = new Card(CardSuit.CLUBS, CardRank.NINE);
        card.applyCardValue(new Hand(), BlackJackConstants.PLAYER);
        assertEquals(9, card.getValue(), "Expected Value not equal to 9");
    }

    @Test
    void determineCardValueTen() {
        card = new Card(CardSuit.CLUBS, CardRank.TEN);
        card.applyCardValue(new Hand(), BlackJackConstants.PLAYER);
        assertEquals(10, card.getValue(), "Expected Value not equal to 10");
    }

    @Test
    void determineCardValueJack() {
        card = new Card(CardSuit.CLUBS, CardRank.JACK);
        card.applyCardValue(new Hand(), BlackJackConstants.PLAYER);
        assertEquals(10, card.getValue(), "Expected Value not equal to 10");
    }

    @Test
    void determineCardValueQueen() {
        card = new Card(CardSuit.CLUBS, CardRank.QUEEN);
        card.applyCardValue(new Hand(), BlackJackConstants.PLAYER);
        assertEquals(10, card.getValue(), "Expected Value not equal to 10");
    }

    @Test
    void determineCardValueKing() {
        card = new Card(CardSuit.CLUBS, CardRank.KING);
        card.applyCardValue(new Hand(), BlackJackConstants.PLAYER);
        assertEquals(10, card.getValue(), "Expected Value not equal to 10");
    }

    @Test
    @Ignore
    void determineCardValueAceOne() {
        card = new Card(CardSuit.CLUBS, CardRank.ACE);
        Hand testHand = new Hand();
        Card test1 =new Card(CardSuit.CLUBS, CardRank.TEN);
        test1.setValue(10);
        testHand.addCardToHand(test1);
        testHand.addCardToHand(test1);
        when(hand.playerHandTotal()).thenReturn(15);
        card.applyCardValue(testHand, BlackJackConstants.PLAYER);
        assertEquals(1, card.getValue(), "Expected Value not equal to 1");
    }

    @Test
    void determineCardValueAceEleven() {
        card = new Card(CardSuit.CLUBS, CardRank.ACE);
        card.applyCardValue(new Hand(), BlackJackConstants.PLAYER);
        assertEquals(11, card.getValue(), "Expected Value not equal to 1");
    }
}
