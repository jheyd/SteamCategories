package de.janheyd.steamcategories;

import java.util.List;

import lombok.Value;

@Value
public class Game {

	String id;
	Long lastPlayed;
	Integer cloudEnabled;
	List<String> gameTags;

}
