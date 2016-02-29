package de.janheyd.steamcategories;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

public class VdfTest {

	@Test
	public void testFromJson() throws Exception {
		String input = readFileToString(new File("src/test/resources/sharedconfig.json"));
		String expectedOutput = readFileToString(new File("src/test/resources/sharedconfig.vdf"));

		String actualOutput = Vdf.fromJson(input).toString();

		assertThat(actualOutput, is(expectedOutput));
	}

	@Test
	public void testToJson() throws Exception {
		String input = readFileToString(new File("src/test/resources/sharedconfig.vdf"));
		String expectedOutput = readFileToString(new File("src/test/resources/sharedconfig.json"));

		String actualOutput = new Vdf(input).toJson();

		assertThat(actualOutput, jsonEquals(expectedOutput));
	}

}
