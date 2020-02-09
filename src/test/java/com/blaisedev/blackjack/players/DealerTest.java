package com.blaisedev.blackjack.players;

import com.blaisedev.blackjack.*;
import com.blaisedev.blackjack.card.Card;
import com.blaisedev.blackjack.card.CardRank;
import com.blaisedev.blackjack.card.CardSuit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.blaisedev.blackjack.constants.BlackJackConstants.DEALER;
import static com.blaisedev.blackjack.constants.BlackJackConstants.PLAYER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class DealerTest {

    @InjectMocks
    @Autowired
    private Dealer dealer;

    @Mock
    private DeckBuilder deckBuilder;
    @Mock
    private Hand hand;
    @Mock
    private Player player;
    @Mock
    private Card card;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        doNothing().when(hand).addCardToHand(any(Card.class));

    }

    @Test
    void prepareThreeDecks() {
        List<Card> deck = new ArrayList<>();
        deck.add(new Card(CardSuit.CLUBS, CardRank.ACE));
        when(deckBuilder.createDeck()).thenReturn(deck);
        doNothing().when(card).applyCardValue(any(Hand.class), any(String.class));
        when(player.getHand()).thenReturn(hand);
        dealer.prepareCardsForNewGame();
        List<Card> expected = dealer.getDecks();
        assertEquals(3, expected.size(), "Expected number of cards not equal to 3");
        assertEquals(3, dealer.getCurrentCard(), "Current card not 3");
    }

    @Test
    void continuedGame() {
        Card card1 = new Card(CardSuit.CLUBS, CardRank.TEN);
        Card card2 = new Card(CardSuit.HEARTS, CardRank.TWO);
        Card card3 = new Card(CardSuit.SPADES, CardRank.EIGHT);
        Card card4 = new Card(CardSuit.DIAMONDS, CardRank.SIX);
        List<Card> deck = Arrays.asList(card1, card2, card3, card4);
        when(player.getHand()).thenReturn(hand);
        dealer.setDecks(deck);
        dealer.continuedGame();
        verify(hand, atLeast(4)).addCardToHand(any());
    }

    @Test
    void dealNewCardToUser() {
        dealNewCardToUserTestSetup();
        dealer.dealNewCardToUser(PLAYER);
        assertEquals(1, dealer.getCurrentCard(), "Unexpected current card");

    }

    @Test
    void dealNewCardToUserDealer() {
        dealNewCardToUserTestSetup();
        dealer.dealNewCardToUser(DEALER);
        assertEquals(1, dealer.getCurrentCard(), "Unexpected current card");

    }

    private void dealNewCardToUserTestSetup() {
        List<Card> decks = new ArrayList<>();
        decks.add(new Card(CardSuit.CLUBS, CardRank.TEN));
        doNothing().when(card).applyCardValue(any(Hand.class), any(String.class));
        when(player.getHand()).thenReturn(hand);
        dealer.setDecks(decks);
    }

    @Test
    void clearHands() {
        Card testCard = new Card(CardSuit.CLUBS, CardRank.TEN);
        when(player.getHand()).thenReturn(hand);
        player.getHand().addCardToHand(testCard);
        hand.addCardToHand(testCard);
        dealer.clearHands();
        assertEquals(0, player.getHand().getHand().size(), "Expecting size to be zero");
        assertEquals(0, hand.getHand().size(), "Expecting size to be zero");
    }
}