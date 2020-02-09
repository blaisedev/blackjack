package com.blaisedev.blackjack;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@Slf4j
public class BlackjackApplication implements CommandLineRunner {

    @Autowired
    private Menu menu;

    public static void main(String[] args) {
        SpringApplication.run(BlackjackApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        menu.getUserOption();
    }
}
