package de.janheyd.steamcategories;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AutoTagger {

	private Map<String, List<String>> tags;
	private Optional<Integer> numberOfTagsToUse;
	private List<String> knownTags;

	public AutoTagger(Map<String, List<String>> tags) {
		this(tags, empty(), emptyList());
	}

	public AutoTagger(Map<String, List<String>> tags, int numberOfTagsToUse) {
		this(tags, Optional.of(numberOfTagsToUse), emptyList());
	}

	public AutoTagger(Map<String, List<String>> tags, int numberOfTagsToUse, List<String> knownTags) {
		this(tags, Optional.of(numberOfTagsToUse), knownTags);
	}

	public AutoTagger(Map<String, List<String>> tags, List<String> knownTags) {
		this(tags, empty(), knownTags);
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
