package de.janheyd.steamcategories;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonGame {

	@JsonProperty("LastPlayed") Long lastPlayed;
	@JsonProperty("cloudenabled") Integer cloudEnabled;
	@JsonProperty("tags") Map<Integer, String> tags;

	public static JsonGame fromGame(Game game) {
		JsonGame result = new JsonGame();
		result.setCloudEnabled(game.getCloudEnabled());
		result.setLastPlayed(game.getLastPlayed());
		result.setGameTags(game.getGameTags());
		return result;
	}

	public Game toGame(String key) {
		List<String> gameTags = getGameTags();
		return new Game(key, lastPlayed, cloudEnabled, gameTags);
	}

	private List<String> getGameTags() {
		if (tags != null)
			return tags.entrySet().stream()
				.sorted((entry1, entry2) -> Integer.compare(entry1.getKey(), entry2.getKey()))
				.map(Entry::getValue)
				.collect(toList());
		else
			return new ArrayList<>();
	}

	private void setGameTags(List<String> gameTags) {
		Map<Integer, String> newTags = new HashMap<>(gameTags.size());
		for (int i = 0; i < gameTags.size(); i++ )
			newTags.put(i, gameTags.get(i));
		tags = newTags;
	}
}
