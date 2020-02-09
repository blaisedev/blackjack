package com.blaisedev.blackjack.card;


import com.blaisedev.blackjack.constants.BlackJackConstants;
import com.blaisedev.blackjack.Hand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.blaisedev.blackjack.constants.BlackJackConstants.ACE_VALUE_DETERMINATION_MAX;

public class Card {

    private static final Logger log = LoggerFactory.getLogger(Card.class);
    private CardSuit cardSuit;
    private CardRank cardRank;
    private int value;

    public CardSuit getCardSuit() {
        return cardSuit;
    }

    public CardRank getCardRank() {
        return cardRank;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Card(CardSuit cardSuit, CardRank cardRank){
        this.cardSuit = cardSuit;
        this.cardRank = cardRank;
    }

    public void applyCardValue(Hand hand, String user){
        switch (this.getCardRank()){
            case TWO: setValue(BlackJackConstants.NUMBER_TWO); break;
            case THREE: setValue(BlackJackConstants.NUMBER_THREE); break;
            case FOUR: setValue(BlackJackConstants.NUMBER_FOUR); break;
            case FIVE: setValue(BlackJackConstants.NUMBER_FIVE); break;
            case SIX: setValue(BlackJackConstants.NUMBER_SIX); break;
            case SEVEN: setValue(BlackJackConstants.NUMBER_SEVEN); break;
            case EIGHT: setValue(BlackJackConstants.NUMBER_EIGHT); break;
            case NINE: setValue(BlackJackConstants.NUMBER_NINE); break;
            case TEN: setValue(BlackJackConstants.NUMBER_TEN); break;
            case JACK: setValue(BlackJackConstants.NUMBER_TEN); break;
            case QUEEN: setValue(BlackJackConstants.NUMBER_TEN); break;
            case KING: setValue(BlackJackConstants.NUMBER_TEN); break;
            case ACE: setValue(determineUserToRetrieveAceValue(hand, user)); break;
        }
    }

    private int determineUserToRetrieveAceValue(Hand hand, String user) {
        if (user.equals(BlackJackConstants.PLAYER)) {
            return determineAceValue(hand.playerHandTotal());
        }
        return determineAceValue(hand.dealerHandTotal());
    }

    private int determineAceValue(int handValue) {
        if (handValue >= ACE_VALUE_DETERMINATION_MAX) {
            log.info("1 Selected as Ace Value");
            return BlackJackConstants.NUMBER_ONE;
        } else {
            log.info("11 Selected as Ace Value");
            return BlackJackConstants.NUMBER_ELEVEN;
        }
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardSuit=" + cardSuit +
                ", cardRank=" + cardRank +
                '}';
    }
}
