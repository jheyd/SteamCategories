package de.janheyd.steamcategories;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Vdf {

	String content;

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
