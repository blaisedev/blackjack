package com.blaisedev.blackjack;

import com.blaisedev.blackjack.card.Card;
import com.blaisedev.blackjack.card.CardRank;
import com.blaisedev.blackjack.card.CardSuit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeckBuilder {

    private static final Logger log = LoggerFactory.getLogger(DeckBuilder.class);
    private List<Card> deck;

    @Cacheable("deck")
    public List<Card> createDeck() {
        log.info("CREATE DECK CALLED");
        deck = new ArrayList<>();
        for (CardSuit cardSuit : CardSuit.values()) {
            for (CardRank cardRank : CardRank.values()) {
                Card card = new Card(cardSuit, cardRank);
                deck.add(card);
            }
        }
        return deck;
    }

}
