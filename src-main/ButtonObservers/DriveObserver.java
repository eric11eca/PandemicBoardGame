package ButtonObservers;

import java.util.Observable;
import java.util.Observer;

import buttonListeners.DriveListener;
import data.Board;


public class DriveObserver implements Observer{
	private Board board;
	private Observable observable;
	
	public DriveObserver(Board board, Observable observable) {
		this.board = board;
		this.observable = observable;
		this.observable.addObserver(this);
	}

	@Override
	public void update(Observable obs, Object arg) {
		if(obs instanceof DriveListener) {
			DriveListener drive = (DriveListener)obs;
			board.driveDestinationName = drive.getCityName();
			board.actionName = Board.ActionName.DRIVE;
		}
	}
}
