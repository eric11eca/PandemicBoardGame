package gui.interaction;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gui.view.UIBoard;

public class BoardMouseListener implements MouseListener {
	private UIBoard ui;

	public BoardMouseListener(UIBoard ui) {
		super();
		this.ui = ui;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!ui.getPopupArea().contains(e.getPoint()))
			ui.hideMessage();
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
