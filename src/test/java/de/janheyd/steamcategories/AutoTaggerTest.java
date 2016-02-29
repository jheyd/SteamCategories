package de.janheyd.steamcategories;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class AutoTaggerTest {

	private Game game = new Game("id", null, null, emptyList());

	@Test
	public void shouldTagGameWithAllTags() throws Exception {
		Map<String, List<String>> tags = new HashMap<>();
		tags.put("id", asList("foo", "bar"));
		AutoTagger autoTagger = new AutoTagger(tags);

		Game taggedGame = autoTagger.tagGame(game);

		assertThat(taggedGame.getGameTags(), contains("foo", "bar"));
	}

	@Test
	public void shouldTagGameWithFirstKnownTag() throws Exception {
		Map<String, List<String>> tags = new HashMap<>();
		tags.put("id", asList("foo", "bar", "baz"));
		AutoTagger autoTagger = new AutoTagger(tags, 1, asList("bar", "baz"));

		Game taggedGame = autoTagger.tagGame(game);

		assertThat(taggedGame.getGameTags(), contains("bar"));
	}

	@Test
	public void shouldTagGameWithFirstTag() throws Exception {
		Map<String, List<String>> tags = new HashMap<>();
		tags.put("id", asList("foo", "bar"));
		AutoTagger autoTagger = new AutoTagger(tags, 1);

		Game taggedGame = autoTagger.tagGame(game);

		assertThat(taggedGame.getGameTags(), contains("foo"));
	}

	@Test
	public void shouldTagGameWithKnownTag() throws Exception {
		Map<String, List<String>> tags = new HashMap<>();
		tags.put("id", asList("foo", "bar"));
		AutoTagger autoTagger = new AutoTagger(tags, asList("bar"));

		Game taggedGame = autoTagger.tagGame(game);

		assertThat(taggedGame.getGameTags(), contains("bar"));
	}
}
