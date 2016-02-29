package de.janheyd.steamcategories;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Game {

	String id;
	Long lastPlayed;
	Integer cloudEnabled;
	List<String> gameTags;

}
