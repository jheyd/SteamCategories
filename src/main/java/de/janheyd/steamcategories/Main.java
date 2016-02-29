package de.janheyd.steamcategories;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

public class Main {

	public static void main(String[] args) {
		try {
			if (args.length < 1)
				return;

			File file = new File(args[0]);

			SteamConfig config = getConfig(file);

			List<Game> games = config.getGameConfigs();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static SteamConfig getConfig(File file) {
		SteamConfig config;
		try {
			config = SteamConfig.fromVdf(file);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("invalid JSON", e);
		} catch (IOException e) {
			throw new RuntimeException("could not open file " + file + ": " + e.getMessage());
		}
		return config;
	}

}
