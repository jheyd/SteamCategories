package de.janheyd.steamcategories;

import static org.apache.commons.io.FileUtils.readFileToString;

import java.io.File;
import java.io.IOException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Vdf {

	String content;

	public static Vdf fromFile(File file) throws IOException {
		return new Vdf(readFileToString(file));
	}

	public String toJson() {
		String s = content;
		s = convertDictionaryKeys(s);
		s = addCommas(s);
		return "{" + s + "}";
	}

	private String addCommas(String s) {
		return s.replaceAll("([\\}\"])(\\s*\")", "$1,$2");
	}

	private String convertDictionaryKeys(String s) {
		return s.replaceAll("\"([^\"]*)\"\\s*([\\{\"])", "\"$1\" : $2");
	}

}
