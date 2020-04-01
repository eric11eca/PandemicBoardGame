package gui;

import java.util.Optional;
import java.util.Set;

import javax.swing.JComboBox;

import data.CityOLD;
import gui.view.CityListCellRenderer;

public class CityChooser {
	private Set<CityOLD> options;
	private GUI gui;
	private String dialogTitle;

	public CityChooser(Set<CityOLD> options, GUI gui, String dialogTitle) {
		this.options = options;
		this.gui = gui;
		this.dialogTitle = dialogTitle;
	}

	public Optional<CityOLD> letUserChooseACity() {
		JComboBox<CityOLD> chooser = createCityComboBox();
		if (letUserChoose(chooser).userChoseOK()) {
			return Optional.ofNullable((CityOLD) chooser.getSelectedItem());
		} else {
			return Optional.empty();
		}
	}

	private JComboBox<CityOLD> createCityComboBox() {
		JComboBox<CityOLD> chooser = new JComboBox<CityOLD>(options.toArray(new CityOLD[options.size()]));
		chooser.setRenderer(new CityListCellRenderer());
		return chooser;
	}

	private UserResponseAdapter letUserChoose(JComboBox<CityOLD> chooser) {
		return gui.displayOption(dialogTitle, chooser);
	}
}
