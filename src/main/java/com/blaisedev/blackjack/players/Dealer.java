package com.blaisedev.blackjack.players;

import com.blaisedev.blackjack.*;
import com.blaisedev.blackjack.card.Card;
import com.blaisedev.blackjack.constants.BlackJackConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.blaisedev.blackjack.constants.BlackJackConstants.DEALER;
import static com.blaisedev.blackjack.constants.BlackJackConstants.PLAYER;
import static com.blaisedev.blackjack.constants.BlackJackConstants.START_OF_DECK;

@Component
public class Dealer {

    private final DeckBuilder deckBuilder;
    private final Player player;
    private List<Card> decks;
    private Hand hand;
    private int currentCard;

    public void setDecks(List<Card> decks) {
        this.decks = decks;
    }

    public int getCurrentCard() {
        return currentCard;
    }

    @Autowired
    public Dealer(Hand hand, DeckBuilder deckBuilder, Player player) {
        this.hand = hand;
        this.deckBuilder = deckBuilder;
        this.player = player;
    }

    public List<Card> getDecks() {
        return decks;
    }

    public Hand getHand() {
        return hand;
    }

    public void prepareCardsForNewGame() {
        prepareThreeDecks();
        shuffleDecks();
        dealFirstCards();
    }

    public void continuedGame() {
        dealFirstCards();
    }

    public void dealFirstCards() {
        System.out.println("Dealing Cards");
        List<Card> firstFour = extractFourCardsFromCurrentCard();
        currentCard = firstFour.size();
        for (int i = 0; i < firstFour.size(); i++) {
            Card card = firstFour.get(i);
            boolean isDealersCard = i == 1 || i == 3;
            if (isDealersCard) {
                card.applyCardValue(hand, BlackJackConstants.DEALER);
                hand.addCardToHand(card);
            } else {
                card.applyCardValue(player.getHand(), PLAYER);
                player.getHand().addCardToHand(card);
            }
        }
        System.out.println("Cards Dealt");
    }

    private List<Card> extractFourCardsFromCurrentCard() {
        return decks.stream().skip(currentCard).limit(4).collect(Collectors.toList());
    }

    private void shuffleDecks() {
        System.out.println("SHUFFLING ALL DECKS");
        revertCurrentCardToStart();
        Collections.shuffle(decks);
        System.out.println("SHUFFLING COMPLETE");
    }

    private void revertCurrentCardToStart() {
        currentCard = START_OF_DECK;
    }

    private void prepareThreeDecks() {
        decks = new ArrayList<>();
        decks = Stream.generate(() -> deckBuilder.createDeck()).limit(3).flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println("DECKS PREPPED FOR SHUFFLING");
    }

    public void dealNewCardToUser(String user) {
        Card newCard = incrementCurrentCardWhenAccessed();
        if (user.equals(PLAYER)) {
            newCard.applyCardValue(player.getHand(), PLAYER);
            player.getHand().addCardToHand(newCard);
        } else {
            newCard.applyCardValue(hand, DEALER);
            hand.addCardToHand(newCard);
        }
    }

    private Card incrementCurrentCardWhenAccessed() {
        Card newCard = decks.get(currentCard);
        currentCard++;
        return newCard;
    }

    public void clearHands() {
        player.getHand().clearHand();
        hand.clearHand();
    }

    private void insertCutCardToDecks(){
        //TODO createrandom number over 10
        // insert into pack
    }

    private void checkIfCutCard(){
        // TODO check if cut card if it is call shuffle
    }
}
