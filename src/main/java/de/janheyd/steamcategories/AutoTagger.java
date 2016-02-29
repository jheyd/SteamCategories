package de.janheyd.steamcategories;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AutoTagger {

	private Map<String, List<String>> tags;
	private Optional<Integer> numberOfTagsToUse = Optional.empty();

	public AutoTagger(Map<String, List<String>> tags) {
		this.tags = tags;
	}

	public AutoTagger(Map<String, List<String>> tags, int numberOfTagsToUse) {
		this(tags);
		this.numberOfTagsToUse = Optional.of(numberOfTagsToUse);
	}

	public void tagGame(Game game) {
		game.setGameTags(getTagsFor(game));
	}

	private List<String> getTagsFor(Game game) {
		List<String> gameTags = tags.get(game.getId());
		List<String> filtered = filterGameTags(gameTags);
		return filtered;
	}

	private List<String> filterGameTags(List<String> gameTags) {
		if (numberOfTagsToUse.isPresent())
			return gameTags.subList(0, numberOfTagsToUse.get());
		return gameTags;
	}

}
