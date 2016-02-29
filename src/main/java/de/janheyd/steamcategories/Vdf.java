package de.janheyd.steamcategories;

import static org.apache.commons.io.FileUtils.readFileToString;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Vdf {

	String content;

	public static Vdf fromFile(File file) throws IOException {
		return new Vdf(readFileToString(file));
	}

	public static Vdf fromJson(String json) {
		String s = json;
		s = s.replaceAll("\r?\n", "\n");
		s = removeBrackets(s);
		s = convertFieldsToVdf(s);
		s = removeCommas(s);
		s = s + "\n";
		return new Vdf(s);
	}

	private static String convertComplexFieldsToVdf(String s) {
		String result = s;
		result = removeColons(result);
		result = fixIndentation(result);
		return result;
	}

	private static String convertFieldsToVdf(String s) {
		String result = s;
		result = convertSimpleFieldsToVdf(result);
		result = convertComplexFieldsToVdf(result);
		return result;
	}

	private static String convertSimpleFieldsToVdf(String s) {
		return s.replaceAll("\"([^\"]*)\"\\s*\\:\\s*([\"])", "\"$1\"\t\t$2");
	}

	private static int countLeadingTabs(String line) {
		String trimmedLine = line.replaceAll("^\\t+", "");
		return line.length() - trimmedLine.length();
	}

	private static String fixIndentation(String result) {
		List<String> resultLines = new ArrayList<>();
		int previousIndent = 0;
		for (String line : result.split("\n"))
			if (line.startsWith("{"))
				resultLines.add(tabs(previousIndent) + line);
			else {
				previousIndent = countLeadingTabs(line);
				resultLines.add(line);
			}
		return String.join("\n", resultLines);
	}

	private static String removeBrackets(String s) {
		return s.substring(1, s.length() - 1);
	}

	private static String removeColons(String result) {
		return result.replaceAll("\"([^\"]*)\"\\s*\\:\\s*([\\{])", "\"$1\"\n$2");
	}

	private static String removeCommas(String s) {
		return s.replaceAll("([\\}\"]),", "$1");
	}

	private static String tabs(int count) {
		StringBuffer tabs = new StringBuffer();
		for (int i = 0; i < count; i++ )
			tabs.append("\t");
		return tabs.toString();
	}

	public String toJson() {
		String s = content;
		s = convertDictionaryKeys(s);
		s = addCommas(s);
		return "{" + s + "}";
	}

	@Override
	public String toString() {
		return content;
	}

	private String addCommas(String s) {
		return s.replaceAll("([\\}\"])(\\s*\")", "$1,$2");
	}

	private String convertDictionaryKeys(String s) {
		return s.replaceAll("\"([^\"]*)\"\\s*([\\{\"])", "\"$1\" : $2");
	}

}
