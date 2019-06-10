package ButtonObservers;

import java.util.Observable;
import java.util.Observer;

import buttonListeners.CharterFlightListener;
import data.Board;

public class CharterFlightObserver implements Observer {
	private Board board;
	private Observable observable;
	
	public CharterFlightObserver(Board board, Observable observable) {
		this.board = board;
		this.observable = observable;
		observable.addObserver(this);
	}

	@Override
	public void update(Observable obs, Object arg) {
		if(obs instanceof CharterFlightListener) {
			CharterFlightListener cflight = (CharterFlightListener)obs;
			board.cityCardNameCharter = cflight.chosenCity;
			board.actionName = Board.ActionName.CHARTERFLIGHT;
		}
	}

}
