package de.janheyd.steamcategories;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@Value
public class GameConfigs {

	private static ObjectMapper mapper = new ObjectMapper();
	private List<Game> configs;

	public static GameConfigs from(Iterable<Entry<String, JsonNode>> iterable) {
		List<Game> list = StreamSupport.stream(iterable.spliterator(), false)
			.map(entry -> {
				String key = entry.getKey();
				JsonNode value = entry.getValue();
				try {
					JsonGame jsonGame = mapper.treeToValue(value, JsonGame.class);
					return jsonGame.toGame(key);
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}
			}).collect(toList());
		return new GameConfigs(list);
	}

}
