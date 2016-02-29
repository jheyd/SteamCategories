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

		autoTagger.tagGame(game);

		assertThat(game.getGameTags(), contains("foo", "bar"));
	}

	@Test
	public void shouldTagGameWithFirstKnownTag() throws Exception {
		Map<String, List<String>> tags = new HashMap<>();
		tags.put("id", asList("foo", "bar", "baz"));
		AutoTagger autoTagger = new AutoTagger(tags, 1, asList("bar", "baz"));

		autoTagger.tagGame(game);

		assertThat(game.getGameTags(), contains("bar"));
	}

	@Test
	public void shouldTagGameWithFirstTag() throws Exception {
		Map<String, List<String>> tags = new HashMap<>();
		tags.put("id", asList("foo", "bar"));
		AutoTagger autoTagger = new AutoTagger(tags, 1);

		autoTagger.tagGame(game);

		assertThat(game.getGameTags(), contains("foo"));
	}

	@Test
	public void shouldTagGameWithKnownTag() throws Exception {
		Map<String, List<String>> tags = new HashMap<>();
		tags.put("id", asList("foo", "bar"));
		AutoTagger autoTagger = new AutoTagger(tags, asList("bar"));

		autoTagger.tagGame(game);

		assertThat(game.getGameTags(), contains("bar"));
	}
}
