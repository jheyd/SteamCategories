package de.janheyd.steamcategories;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Vdf {

	String content;

	public String toJson() {
		String s = content;
		s = convertDictionaryKeysForComplexFields(s);
		s = convertSimpleFields(s);
		s = addCommasAfterComplexFields(s);
		s = removeUnnecessaryCommas(s);
		return "{" + s + "}";
	}

	private String addCommasAfterComplexFields(String s) {
		return s.replaceAll("\\}(\\s*\")", "},$1");
	}

	private String convertDictionaryKeysForComplexFields(String s) {
		return s.replaceAll("\"([^\"]*)\"(\\s*)\\{", "\"$1\" : \\{");
	}

	private String convertSimpleFields(String s) {
		return s.replaceAll("\"([^\"]*)\"\\s*\"([^\"]*)\"", "\"$1\": \"$2\",");
	}

	private String removeUnnecessaryCommas(String s) {
		return s.replaceAll(",(\\s*[}\\]])", "$1");
	}

}
