package de.janheyd.steamcategories;

import java.util.List;
import java.util.Map;

public class AutoTagger {

	private Map<String, List<String>> tags;

	public AutoTagger(Map<String, List<String>> tags) {
		this.tags = tags;
	}

	public void tagGame(Game game) {
		game.setGameTags(tags.get(game.getId()));
	}

}
