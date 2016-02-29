package de.janheyd.steamcategories;

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static org.apache.commons.io.FileUtils.readFileToString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class VdfTest {

	private String json;
	private String vdf;

	@Before
	public void setUp() throws Exception {
		json = readFileToString(new File("src/test/resources/sharedconfig.json"));
		vdf = readFileToString(new File("src/test/resources/sharedconfig.vdf"));
	}

	@Test
	public void testFromJson() throws Exception {
		assertThat(Vdf.fromJson(json).toString(), is(vdf));
	}

	@Test
	public void testToJson() throws Exception {
		assertThat(new Vdf(vdf).toJson(), jsonEquals(json));
	}

}
