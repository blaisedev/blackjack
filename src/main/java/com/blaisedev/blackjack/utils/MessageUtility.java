package com.blaisedev.blackjack.utils;


import com.blaisedev.blackjack.players.Dealer;
import com.blaisedev.blackjack.players.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.blaisedev.blackjack.constants.BlackJackConstants.DEALER;
import static com.blaisedev.blackjack.constants.BlackJackConstants.PLAYER;


@Component
public class MessageUtility {

    private static final Logger log = LoggerFactory.getLogger(MessageUtility.class);

    private final Dealer dealer;
    private final Player player;

    @Autowired
    public MessageUtility(Dealer dealer, Player player) {
        this.dealer = dealer;
        this.player = player;
    }

    public void formatWinningMessage(String user) {
        log.info(user + " WINS");
        log.info(player.getHand().toString());
        log.info("Player total: " + player.getHand().playerHandTotal());
        log.info(dealer.getHand().toString());
        log.info("Dealer total: " + dealer.getHand().dealerHandTotal());
    }

    public void formatDrawnGameMessages() {
        log.info("Game ended in a DRAW!!!!");
        log.info(dealer.getHand().toString());
        log.info("Dealer total: " + dealer.getHand().dealerHandTotal());
        log.info(player.getHand().toString());
        log.info("Player total: " + player.getHand().playerHandTotal());
    }

    public void determineWinnersMessage(String user) {
        if (user.equals(PLAYER)) {
            log.info(PLAYER + " Bust!!");
            formatWinningMessage(DEALER);
        } else {
            log.info(DEALER + " Bust!!");
            formatWinningMessage(PLAYER);
        }
    }
}