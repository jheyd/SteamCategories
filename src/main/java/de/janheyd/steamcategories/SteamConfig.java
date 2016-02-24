package de.janheyd.steamcategories;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PROTECTED;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor(access = PROTECTED)
public class SteamConfig {

	private static ObjectMapper mapper = new ObjectMapper();

	private JsonNode jsonConfig;

	public static SteamConfig fromJson(String json) throws JsonProcessingException {
		try {
			return new SteamConfig(mapper.readTree(json));
		} catch (JsonProcessingException e) {
			throw e;
		} catch (IOException e) {
			throw new RuntimeException("unexpected IOException during String processing", e);
		}
	}

	public static SteamConfig fromVdf(Vdf vdf) throws JsonProcessingException {
		return fromJson(vdf.toJson());
	}

	public List<Game> getGameConfigs() {
		JsonNode apps = jsonConfig.get("UserRoamingConfigStore")
			.get("Software").get("Valve").get("Steam").get("apps");
		Iterable<Entry<String, JsonNode>> iterable = () -> apps.fields();
		return StreamSupport.stream(iterable.spliterator(), false)
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
	}

}
