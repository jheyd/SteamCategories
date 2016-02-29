package de.janheyd.steamcategories;

import static lombok.AccessLevel.PROTECTED;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

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
			throw e; // Why the fuck does JsonProcessingException extend IOException?
		} catch (IOException e) {
			throw new RuntimeException("unexpected IOException during String processing", e);
		}
	}

	public static SteamConfig fromVdf(File file) throws JsonProcessingException, IOException {
		return fromVdf(Vdf.fromFile(file));
	}

	public static SteamConfig fromVdf(Vdf vdf) throws JsonProcessingException {
		return fromJson(vdf.toJson());
	}

	public GameConfigs getGameConfigs() {
		JsonNode apps = jsonConfig.get("UserRoamingConfigStore")
			.get("Software").get("Valve").get("Steam").get("apps");
		Iterable<Entry<String, JsonNode>> iterable = () -> apps.fields();
		return GameConfigs.from(iterable);
	}

}
