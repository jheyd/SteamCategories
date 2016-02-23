package de.janheyd.steamcategories;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Vdf {

	String content;

	public String toJson() {
		String json = content
			.replaceAll("\"([^\"]*)\"(\\s*)\\{", "\"$1\" : \\{")
			.replaceAll("\"([^\"]*)\"\\s*\"([^\"]*)\"", "\"$1\": \"$2\",")
			.replaceAll(",(\\s*[}\\]])", "$1")
			.replaceAll("([}\\]])(\\s*)(\"[^\"]*\":\\s*)?([\\{\\[])", "$1,$2$3$4")
			.replaceAll("}(\\s*\"[^\"]*\":)", "},$1")
			.replaceAll("\\}(\\s*\")", "},$1");
		return "{" + json + "}";
	}

}
