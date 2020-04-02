package gui;

import java.util.Optional;
import java.util.Set;

import javax.swing.JComboBox;

import game.city.City;
import gui.view.CityListCellRenderer;

public class CityChooser {
	private Set<City> options;
	private GameGUI gui;
	private String dialogTitle;

	public CityChooser(Set<City> options, GameGUI gui, String dialogTitle) {
		this.options = options;
		this.gui = gui;
		this.dialogTitle = dialogTitle;
	}

	public Optional<City> letUserChooseACity() {
		JComboBox<City> chooser = createCityComboBox();
		if (letUserChoose(chooser).userChoseOK()) {
			return Optional.ofNullable((City) chooser.getSelectedItem());
		} else {
			return Optional.empty();
		}
	}

	private JComboBox<City> createCityComboBox() {
		JComboBox<City> chooser = new JComboBox<City>(options.toArray(new City[options.size()]));
		chooser.setRenderer(new CityListCellRenderer());
		return chooser;
	}

	private UserResponseAdapter letUserChoose(JComboBox<City> chooser) {
		return gui.displayOption(dialogTitle, chooser);
	}
}
