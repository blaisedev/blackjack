package com.blaisedev.blackjack;

import com.blaisedev.blackjack.card.Card;
import com.blaisedev.blackjack.card.CardSuit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class DeckBuilderTest {

    @InjectMocks
    @Autowired
    private DeckBuilder deckBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }



    @Test
    void createDeck() {
        List<Card> expected = deckBuilder.createDeck();
        assertEquals(52, expected.size(), "Expected Deck Size Not 52");
        assertEquals(13, checkNumberOfCardsPerSuit(expected, CardSuit.CLUBS), "Expected Clubs not equal to 13");
        assertEquals(13, checkNumberOfCardsPerSuit(expected, CardSuit.DIAMONDS), "Expected Diamonds not equal to 13");
        assertEquals(13, checkNumberOfCardsPerSuit(expected, CardSuit.SPADES), "Expected Spades not equal to 13");
        assertEquals(13, checkNumberOfCardsPerSuit(expected, CardSuit.HEARTS), "Expected Hearts not equal to 13");
    }

    private int checkNumberOfCardsPerSuit(List<Card> deck, CardSuit cardSuit) {
        Set<Card> filteredSuit = deck
                                    .stream()
                                    .filter(card -> cardSuit.equals(card.getCardSuit()))
                                    .collect(Collectors.toSet());
        return filteredSuit.size();
    }
}