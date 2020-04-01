package gui.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import data.City;

public class CityListCellRenderer implements ListCellRenderer<City> {

	@Override
	public Component getListCellRendererComponent(JList<? extends City> jlist, City city, int index, boolean isSelected,
			boolean cellHasFocus) {
		JLabel label = new JLabel(String.format("%s [%s]", city.cityName, city.color));
		label.setOpaque(true);
		if (isSelected) {
			label.setBackground(Color.LIGHT_GRAY);
		} else {
			label.setBackground(Color.WHITE);
		}
		return label;
	}

}
