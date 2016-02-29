package de.janheyd.steamcategories;

import static org.apache.commons.io.FileUtils.readLines;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SteamConfigTest {

	@Test
	public void testGetGameConfigs() throws Exception {
		SteamConfig steamConfig = SteamConfig.fromVdf(new File("src/test/resources/sharedconfig.vdf"));
		GameConfigs expected = expected();

		GameConfigs actual = steamConfig.getGameConfigs();

		assertThat(actual, is(expected));
	}

	private GameConfigs expected() throws IOException, JsonProcessingException {
		List<String> lines = readLines(new File("src/test/resources/sharedconfig.json")).subList(6, 30);
		String join = String.join("", lines);
		JsonNode readTree = new ObjectMapper().readTree("{" + join + "}");
		GameConfigs expected = GameConfigs.from(() -> readTree.fields());
		return expected;
	}

}
