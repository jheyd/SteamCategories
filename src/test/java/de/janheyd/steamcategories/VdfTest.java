package de.janheyd.steamcategories;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

public class VdfTest {

	private static final File VDF_FILE = new File("src/test/resources/sharedconfig.vdf");
	private static final File JSON_FILE = new File("src/test/resources/sharedconfig.json");

	@Test
	public void testFromJson() throws Exception {
		String input = readFileToString(JSON_FILE);
		String expectedOutput = readFileToString(VDF_FILE);

		String actualOutput = Vdf.fromJson(input).toString();

		assertThat(actualOutput, is(expectedOutput));
	}

	@Test
	public void testToJson() throws Exception {
		String input = readFileToString(VDF_FILE);
		String expectedOutput = readFileToString(JSON_FILE);

		String actualOutput = new Vdf(input).toJson();

		assertThat(actualOutput, jsonEquals(expectedOutput));
	}

}
