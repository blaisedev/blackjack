package com.blaisedev.blackjack;

import org.springframework.stereotype.Component;

import static com.blaisedev.blackjack.constants.BlackJackConstants.BLACKJACK;

@Component
class Rule{

    public boolean isHandBust(int handTotal) {
        return handTotal > BLACKJACK;
    }

    public boolean hasPlayerBlackjack(int playerTotal) {
        return playerTotal == BLACKJACK;
    }

    public boolean isGameADraw(int playerTotal, int dealerTotal) {return playerTotal == dealerTotal; }

}
