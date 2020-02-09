package com.blaisedev.blackjack;

import com.blaisedev.blackjack.players.Dealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BlackjackApplication implements CommandLineRunner {

	@Autowired
	Menu menu;

	@Autowired
	private Dealer dealer;

	public static void main(String[] args) {
		SpringApplication.run(BlackjackApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		menu.getUserOption();
	}
}
