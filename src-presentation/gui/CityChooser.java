package gui;

import java.util.Optional;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import data.City;
import gui.view.CityListCellRenderer;

public class CityChooser {
	private Set<City> options;
	private JComponent parentComponent;
	private String dialogTitle;

	public CityChooser(Set<City> options, JComponent parentComponent, String dialogTitle) {
		this.options = options;
		this.parentComponent = parentComponent;
		this.dialogTitle = dialogTitle;
	}

	public Optional<City> letUserChooseACity() {
		JComboBox<City> chooser = createCityComboBox();
		int choice = showChoosingDialogAndGetChoice(chooser);

		switch (choice) {
		case JOptionPane.CANCEL_OPTION:
			return Optional.empty();
		case JOptionPane.OK_OPTION:
			return Optional.ofNullable((City) chooser.getSelectedItem());
		}

		throw new RuntimeException("Invalid JOptionPane Choice");

	}

	private JComboBox<City> createCityComboBox() {
		JComboBox<City> chooser = new JComboBox<City>(options.toArray(new City[options.size()]));
		chooser.setRenderer(new CityListCellRenderer());
		return chooser;
	}

	private int showChoosingDialogAndGetChoice(JComboBox<City> chooser) {
		return JOptionPane.showConfirmDialog(parentComponent, chooser, dialogTitle, JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
	}
}
