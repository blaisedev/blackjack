package com.blaisedev.blackjack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class RuleTest {

    @InjectMocks
    @Autowired
    private Rule rule;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void isHandBust() {
        boolean actual = rule.isHandBust(21);
        assertEquals(false, actual, "Expected hand not to be bust");
    }

    @Test
    void isHandBustNegtive() {
        boolean actual = rule.isHandBust(22);
        assertEquals(true, actual, "Expected hand  to be bust");
    }

    @Test
    void hasPlayerBlackjack() {
        boolean actual = rule.hasPlayerBlackjack(21);
        assertEquals(true, actual, "Expected hand to be blackjack");
    }

    @Test
    void hasPlayerBlackjackNegative() {
        boolean actual = rule.isHandBust(21);
        assertEquals(false, actual, "Expected hand not to be blackjack");
    }

    @Test
    void isGameADraw() {
        boolean actual = rule.isGameADraw(21, 21);
        assertEquals(true, actual, "Expected game to be a draw");
    }

    @Test
    void isGameADrawNegative() {
        boolean actual = rule.isGameADraw(21, 18);
        assertEquals(false, actual, "Expected game to not be a draw");
    }
}