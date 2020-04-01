package gui.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import data.CityOLD;

public class CityListCellRenderer implements ListCellRenderer<CityOLD> {

	@Override
	public Component getListCellRendererComponent(JList<? extends CityOLD> jlist, CityOLD city, int index,
			boolean isSelected, boolean cellHasFocus) {
		JLabel label = new JLabel(String.format("%s [%s]", city.getName(), city.getColor()));
		label.setOpaque(true);
		if (isSelected) {
			label.setBackground(Color.LIGHT_GRAY);
		} else {
			label.setBackground(Color.WHITE);
		}
		return label;
	}

}
