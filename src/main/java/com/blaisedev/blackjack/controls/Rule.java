package com.blaisedev.blackjack.controls;

import com.blaisedev.blackjack.utils.MessageUtility;
import com.blaisedev.blackjack.players.Dealer;
import com.blaisedev.blackjack.players.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.blaisedev.blackjack.constants.BlackJackConstants.BLACKJACK;
import static com.blaisedev.blackjack.constants.BlackJackConstants.DEALER;
import static com.blaisedev.blackjack.constants.BlackJackConstants.PLAYER;

@Component
public class Rule {

    private static final Logger log = LoggerFactory.getLogger(Rule.class);

    private final MessageUtility messageUtility;
    private final Dealer dealer;
    private final Player player;
    protected boolean hasPlayerBlackjack;
    protected boolean hasDealerBlackJack;

    @Autowired
    public Rule(MessageUtility messageUtility, Dealer dealer, Player player) {
        this.messageUtility = messageUtility;
        this.dealer = dealer;
        this.player = player;
    }

    public boolean isHandBust(int handTotal) {
        return handTotal > BLACKJACK;
    }

    public boolean isHandBlackjack(int playerTotal) {
        return playerTotal == BLACKJACK;
    }

    public boolean isGameADraw(int playerTotal, int dealerTotal) {
        return playerTotal == dealerTotal;
    }

    protected boolean isGameEndingWithBlackJack(){
        hasPlayerOrDealerBlackJack();
        boolean isDraw = hasGameEndedAsInADraw();
        return isPlayerOrDealerBlackJack(isDraw);
    }

    private boolean isPlayerOrDealerBlackJack(boolean isDraw) {
        if(!isDraw) {
            boolean isPlayerWinner = isPlayerWinner();
            if (!isPlayerWinner) {
                return isDealerWinnerByBlackJack();
            }
        }
        return hasPlayerBlackjack || isDraw;
    }

    private boolean isDealerWinnerByBlackJack() {
        if (hasDealerBlackJack) {
            messageUtility.formatWinningMessage(DEALER);
        }
        return hasDealerBlackJack;
    }

    private boolean isPlayerWinner() {
        if(hasPlayerBlackjack) {
            messageUtility.formatWinningMessage(PLAYER);
        }
        return hasPlayerBlackjack;
    }

    private boolean hasGameEndedAsInADraw() {
        boolean draw = hasPlayerBlackjack && hasDealerBlackJack;
        if (draw) {
            messageUtility.formatDrawnGameMessages();
        }
        return draw;
    }

    protected void hasPlayerBlackJack() {
        hasPlayerBlackjack = isHandBlackjack(player.getHand().playerHandTotal());
        if (hasPlayerBlackjack) {
            log.info("Player has hit BlackJack");
        }
    }

    private void hasDealerBlackJack() {
        hasDealerBlackJack = isHandBlackjack(dealer.getHand().dealerHandTotal());
        if (hasDealerBlackJack) {
            log.info("Dealer has hit BlackJack");
        }
    }

    private void hasPlayerOrDealerBlackJack() {
        hasPlayerBlackJack();
        hasDealerBlackJack();
    }

    public boolean determineIfGameIsADraw() {
        boolean isDraw = isGameADraw(player.getHand().playerHandTotal(), dealer.getHand().dealerHandTotal());
        if (isDraw) {
            messageUtility.formatDrawnGameMessages();
        }
        return isDraw;
    }

    public boolean isDealerWinner() {
        boolean isDealerWinner = hasDealerGreaterHand();
        if (isDealerWinner) {
            messageUtility.formatWinningMessage(DEALER);
        }
        return isDealerWinner;
    }

    private boolean hasDealerGreaterHand() {
        int playerHandTotal = player.getHand().playerHandTotal();
        int dealerHandTotal = dealer.getHand().dealerHandTotal();
        return playerHandTotal < dealerHandTotal;
    }

    public boolean checkIfHandBust(int handTotal, String user) {
        boolean bust = isHandBust(handTotal);
        if (bust) {
            messageUtility.determineWinnersMessage(user);

        }
        return bust;
    }

}
