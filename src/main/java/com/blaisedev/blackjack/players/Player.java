package com.blaisedev.blackjack.players;

import com.blaisedev.blackjack.Hand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Player {

    @Autowired
    public Player(Hand hand) {
        this.hand = hand;
    }

    private final Hand hand;

    public Hand getHand() {
        return hand;
    }

}
