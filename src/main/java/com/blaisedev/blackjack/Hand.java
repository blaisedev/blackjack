package com.blaisedev.blackjack;

import com.blaisedev.blackjack.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Card> hand = new ArrayList<>();

    public List<Card> getHand() {
        return hand;
    }

    public int playerHandTotal(){
        int total = hand.stream().mapToInt(Card::getValue).sum();
        return total;
    }

    public int dealerHandTotal(){
        int total = hand.stream().mapToInt(Card::getValue).sum();
        return total;
    }

    public void addCardToHand(Card card){
        hand.add(card);
    }

    public void clearHand(){
            hand.clear();
    }

    public int showDealersFirstCard() {
        int total = hand.stream().limit(1).mapToInt(Card::getValue).sum();
        return total;
    }

    @Override
    public String toString() {
        return "Hand{" +
                "hand=" + hand +
                '}';
    }
}
