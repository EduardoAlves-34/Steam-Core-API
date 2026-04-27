package com.steamclone.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class SteamApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SteamApiApplication.class, args);
	}

}
