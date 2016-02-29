package de.janheyd.steamcategories;

import java.util.List;

import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
public class Game {

	String id;
	Long lastPlayed;
	Integer cloudEnabled;
	List<String> gameTags;

}
