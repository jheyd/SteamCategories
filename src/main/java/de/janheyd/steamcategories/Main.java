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
			String json = new Vdf(vdf).toJson();
			SteamConfig config;
			try {
				config = SteamConfig.fromJson(json);
			} catch (JsonProcessingException e) {
				throw new RuntimeException("invalid JSON", e);
			}

			List<Game> games = config.getGameConfigs();
		} catch (Exception e) {
			e.printStackTrace();
			showMessageDialog(null, e);
		}
	}

	private static File load() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(null);
		return fileChooser.getSelectedFile();
	}

	private static String loadVdf(File file) {
		try {
			return readFileToString(file);
		} catch (IOException e) {
			throw new RuntimeException("could not open file " + file + ": " + e.getMessage());
		}
	}

	private static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
			| UnsupportedLookAndFeelException e) {
			throw new RuntimeException(e);
		}
	}

}
