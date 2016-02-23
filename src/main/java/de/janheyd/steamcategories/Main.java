package de.janheyd.steamcategories;

import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.UIManager.getSystemLookAndFeelClassName;
import static org.apache.commons.io.FileUtils.readFileToString;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.fasterxml.jackson.core.JsonProcessingException;

public class Main {

	public static void main(String[] args) {
		setLookAndFeel();
		try {
			File file;
			if (args.length >= 1)
				file = new File(args[0]);
			else
				file = load();
			if (file == null)
				return;

			String vdf = loadVdf(file);
			String json = vdfToJson(vdf);

			List<Game> games = getGames(json);
		} catch (Exception e) {
			e.printStackTrace();
			showMessageDialog(null, e);
		}
	}

	private static List<Game> getGames(String json) {
		try {
			return SteamConfig.fromJson(json).getGameConfigs();
		} catch (JsonProcessingException e) {
			throw new RuntimeException("invalid JSON", e);
		}
	}

	private static File load() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(null);
		File loaded = fileChooser.getSelectedFile();
		return loaded;
	}

	private static String loadVdf(File file) {
		String vdf;
		try {
			vdf = readFileToString(file);
		} catch (IOException e) {
			throw new RuntimeException("could not open file " + file + ": " + e.getMessage());
		}
		return vdf;
	}

	private static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
			| UnsupportedLookAndFeelException e) {
			throw new RuntimeException(e);
		}
	}

	private static String vdfToJson(String vdf) {
		String json = vdf
			.replaceAll("\"([^\"]*)\"(\\s*)\\{", "\"$1\" : \\{")
			.replaceAll("\"([^\"]*)\"\\s*\"([^\"]*)\"", "\"$1\": \"$2\",")
			.replaceAll(",(\\s*[}\\]])", "$1")
			.replaceAll("([}\\]])(\\s*)(\"[^\"]*\":\\s*)?([\\{\\[])", "$1,$2$3$4")
			.replaceAll("}(\\s*\"[^\"]*\":)", "},$1")
			.replaceAll("\\}(\\s*\")", "},$1");
		return "{" + json + "}";
	}

}
