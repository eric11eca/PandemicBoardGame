package gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.function.Supplier;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class UIDeck extends JPanel implements UI {
	private Supplier<Integer> deckSize;
	private JLabel sizeLabel;

	public UIDeck(String name, Color color, Supplier<Integer> deckSize) {
		super();
		this.deckSize = deckSize;
		this.setToolTipText(name);
		setPreferredSize(new Dimension(100, 80));
		this.setLayout(new BorderLayout());
		this.add(new JLabel(name), BorderLayout.NORTH);
		sizeLabel = new JLabel();
		sizeLabel.setFont(getFont().deriveFont(40f));
		this.add(sizeLabel, BorderLayout.CENTER);
		this.setBackground(color);
	}

	@Override
	public void update() {
		sizeLabel.setText(String.valueOf(deckSize.get()));
	}
}
