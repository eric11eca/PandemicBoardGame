package gui.interaction;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SelectCardListener implements MouseListener {
	private UICardChooser chooser;
	private UICard ui;

	public SelectCardListener(UICardChooser chooser, UICard ui) {
		super();
		this.chooser = chooser;
		this.ui = ui;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		ui.setSelected(!ui.isSelected());
		chooser.update();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
