package main;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.GameProperty;
import lang.I18n;

@SuppressWarnings("serial")
public class StartGUI extends JFrame {
	private JPanel panel;
	private BiConsumer<Integer, Integer> startAction;
	private JComboBox<String> languageChooser;
	private JLabel playerCountLabel;
	private JComboBox<Integer> playerCountChooser;
	private JLabel difficultyLabel;
	private JComboBox<String> difficultyChooser;
	private Map<String, Locale> localeMap;
	private Map<String, Integer> difficultyMap;

	private JButton startButton;

	public StartGUI(BiConsumer<Integer, Integer> startAction) {
		this.startAction = startAction;
		panel = new JPanel();
		initLanguageChooser();
		initPlayerCount();
		initDifficulty();

		initStartButton();

		add(panel);
		this.setSize(350, 120);
		this.setResizable(false);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		initUI();
	}

	private void initStartButton() {
		startButton = new JButton();
		startButton.addActionListener(e -> {
			dispose();
			startAction.accept((Integer) playerCountChooser.getSelectedItem(),
					difficultyMap.get(difficultyChooser.getSelectedItem()));
		});
		panel.add(startButton);
	}

	private void initDifficulty() {
		difficultyLabel = new JLabel();
		panel.add(difficultyLabel);
		difficultyChooser = new JComboBox<>();
		panel.add(difficultyChooser);
	}

	private void initPlayerCount() {
		playerCountLabel = new JLabel();
		panel.add(playerCountLabel);
		playerCountChooser = new JComboBox<>(new Integer[] { 2, 3, 4 });
		panel.add(playerCountChooser);
	}

	private void initLanguageChooser() {
		String[] locales = GameProperty.getInstance().getStringArray("LANGUAGES");
		String[] languages = new String[locales.length];
		localeMap = new HashMap<>();
		for (int i = 0; i < locales.length; i++) {
			Locale locale = Locale.forLanguageTag(locales[i]);
			languages[i] = locale.getDisplayLanguage(locale);
			localeMap.put(languages[i], locale);
		}
		languageChooser = new JComboBox<>(languages);
		languageChooser.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String language = (String) e.getItem();
					Locale locale = localeMap.get(language);
					I18n.setLocale(locale);
					Locale.setDefault(locale);
					JOptionPane.setDefaultLocale(locale);
					initUI();
				}

			}
		});
		panel.add(languageChooser);
	}

	private void initUI() {
		this.setTitle(I18n.format("start.title"));
		playerCountLabel.setText(I18n.format("start.player_count"));
		difficultyLabel.setText(I18n.format("start.difficulty"));
		startButton.setText(I18n.format("start.start"));

		difficultyMap = new HashMap<>();
		String[] difficulties = { I18n.format("start.difficulty.easy"), I18n.format("start.difficulty.medium"),
				I18n.format("start.difficulty.hard") };
		difficultyMap.put(difficulties[0], 4);
		difficultyMap.put(difficulties[1], 5);
		difficultyMap.put(difficulties[2], 6);
		ComboBoxModel<String> difficultyModel = new DefaultComboBoxModel<>(difficulties);
		int index = difficultyChooser.getSelectedIndex();
		difficultyChooser.setModel(difficultyModel);
		difficultyChooser.setSelectedIndex(index < 0 ? 0 : index);
	}
}
