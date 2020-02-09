package com.blaisedev.blackjack.players;

import com.blaisedev.blackjack.Hand;
import org.springframework.stereotype.Component;

@Component
public class Player {

    public Player(Hand hand) {
        this.hand = hand;
    }

    private Hand hand;

    public Hand getHand() {
        return hand;
    }

}
