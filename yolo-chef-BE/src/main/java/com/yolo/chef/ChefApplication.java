package com.yolo.chef;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class ChefApplication {
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();

		String dbUrl = dotenv.get("DB_URL");
		String dbUsername = dotenv.get("DB_USERNAME");
		String dbPassword = dotenv.get("DB_PASSWORD");
		String apiKey = dotenv.get("ApiKey");
		String geminiUrl = dotenv.get("GeminiUrl");
		String jwtIssuerUri = dotenv.get("JwtIssuerURI");
		String jwtSetUri = dotenv.get("JwtSetURI");

		if (dbUrl == null || dbUsername == null || dbPassword == null) {
			throw new IllegalStateException("Missing required environment variables: DB_URL, DB_USERNAME, or DB_PASSWORD.");
		}

		System.setProperty("DB_URL", dbUrl);
		System.setProperty("DB_USERNAME", dbUsername);
		System.setProperty("DB_PASSWORD", dbPassword);
		System.setProperty("ApiKey", apiKey);
		System.setProperty("GeminiUrl", geminiUrl);
		System.setProperty("JwtIssuerURI", jwtIssuerUri);
		System.setProperty("JwtSetURI", jwtSetUri);

		SpringApplication.run(ChefApplication.class, args);
	}
}