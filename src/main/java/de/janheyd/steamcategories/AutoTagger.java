package de.janheyd.steamcategories;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class AutoTagger {

	private Map<String, List<String>> tags;
	private Optional<Integer> numberOfTagsToUse = Optional.empty();
	private List<String> knownTags = emptyList();

	public AutoTagger(Map<String, List<String>> tags) {
		this.tags = tags;
	}

	public AutoTagger(Map<String, List<String>> tags, int numberOfTagsToUse) {
		this(tags);
		this.numberOfTagsToUse = Optional.of(numberOfTagsToUse);
	}

	public AutoTagger(Map<String, List<String>> tags, int numberOfTagsToUse, List<String> knownTags) {
		this(tags);
		this.numberOfTagsToUse = Optional.of(numberOfTagsToUse);
		this.knownTags = knownTags;
	}

	public AutoTagger(Map<String, List<String>> tags, List<String> knownTags) {
		this(tags);
		this.knownTags = knownTags;
	}

	public void tagGame(Game game) {
		game.setGameTags(getTagsFor(game));
	}

	private List<String> filterGameTags(List<String> gameTags) {
		Stream<String> stream = gameTags.stream();
		if (!knownTags.isEmpty())
			stream = stream.filter(knownTags::contains);
		if (numberOfTagsToUse.isPresent())
			stream = stream.limit(numberOfTagsToUse.get());
		return stream.collect(toList());
	}

	private List<String> getTagsFor(Game game) {
		List<String> gameTags = tags.get(game.getId());
		List<String> filtered = filterGameTags(gameTags);
		return filtered;
	}

}
