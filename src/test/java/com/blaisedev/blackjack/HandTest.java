package com.blaisedev.blackjack;

import com.blaisedev.blackjack.card.Card;
import com.blaisedev.blackjack.card.CardRank;
import com.blaisedev.blackjack.card.CardSuit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class HandTest {

    @InjectMocks
    @Autowired
    private Hand hand;

    private Card testCard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        testCard = new Card(CardSuit.CLUBS, CardRank.TEN);
        testCard.setValue(10);
        hand.addCardToHand(testCard);
    }
    @Test
    void playerHandTotal() {
        hand.addCardToHand(testCard);
        int actual = hand.playerHandTotal();
        assertEquals(20, actual, "Player hand total not as expected");
        assertEquals(2, hand.getHand().size(), "Unexpected hand size");
    }

    @Test
    void dealerHandTotal() {
        hand.addCardToHand(testCard);
        int actual = hand.dealerHandTotal();
        assertEquals(20, actual, "Dealer hand total not as expected");
        assertEquals(2, hand.getHand().size(), "Unexpected hand size");
    }

    @Test
    void dealersFirstCard() {
        hand.addCardToHand(testCard);
        int actual = hand.showDealersFirstCard();
        assertEquals(10, actual, "Dealer hand total not as expected");
        assertEquals(2, hand.getHand().size(), "Unexpected hand size");
    }

    @Test
    void addCardToHand() {
        Hand hand = setUpHandValuesForTest();
        assertEquals("CLUBS", hand.getHand().get(0).getCardSuit().toString(), "Unexpected card Suit");
        assertEquals("TEN", hand.getHand().get(0).getCardRank().toString(), "Unexpected card Rank");
        assertEquals(1, hand.getHand().size(), "Unexpected hand size");
    }

    @Test
    void clearHand() {
        Hand hand = setUpHandValuesForTest();
        assertEquals(1, hand.getHand().size(), "Unexpected hand size");
        hand.clearHand();
        assertEquals(0, hand.getHand().size(), "Unexpected hand size");
    }

    private Hand setUpHandValuesForTest() {
        Card card = new Card(CardSuit.CLUBS, CardRank.TEN);
        Hand hand = new Hand();
        hand.addCardToHand(card);
        return hand;
    }
}