package de.janheyd.steamcategories;

import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.UIManager.getSystemLookAndFeelClassName;

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

			SteamConfig config;
			try {
				config = SteamConfig.fromVdf(file);
			} catch (JsonProcessingException e) {
				throw new RuntimeException("invalid JSON", e);
			} catch (IOException e) {
				throw new RuntimeException("could not open file " + file + ": " + e.getMessage());
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

	private static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
			| UnsupportedLookAndFeelException e) {
			throw new RuntimeException(e);
		}
	}

}
