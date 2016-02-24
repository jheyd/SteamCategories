package de.janheyd.steamcategories;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

import de.janheyd.steamcategories.Vdf;

public class VdfTest {

	@Test
	public void testToJson() throws Exception {
		String input = readFileToString(new File("src/test/resources/sharedconfig.vdf"));
		String expectedOutput = readFileToString(new File("src/test/resources/expected"));

		String acutalOutput = new Vdf(input).toJson();

		assertThat(acutalOutput, jsonEquals(expectedOutput));
	}

}
