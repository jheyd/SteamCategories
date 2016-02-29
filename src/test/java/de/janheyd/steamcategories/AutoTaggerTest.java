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

	@Test
	public void shouldTagGameWithAllTags() throws Exception {
		Game game = new Game("id", null, null, emptyList());
		Map<String, List<String>> tags = new HashMap<>();
		tags.put("id", asList("foo", "bar"));
		AutoTagger autoTagger = new AutoTagger(tags);

		autoTagger.tagGame(game);

		assertThat(game.getGameTags(), contains("foo", "bar"));

	}

	@Test
	public void shouldTagGameWithFirstTag() throws Exception {
		Game game = new Game("id", null, null, emptyList());
		Map<String, List<String>> tags = new HashMap<>();
		tags.put("id", asList("foo", "bar"));
		AutoTagger autoTagger = new AutoTagger(tags, 1);

		autoTagger.tagGame(game);

		assertThat(game.getGameTags(), contains("foo"));

	}
}
