package game;

import data.GameProperty;

public class GameState {

	private boolean lost;
	private boolean won;
	private int outbreakLevel;
	private int infectionIndex;

	public GameState() {
		lost = false;
		won = false;
		outbreakLevel = 0;
		infectionIndex = 0;
	}

	public void triggerWin() {
		won = true;
	}

	public void triggerLose() {
		lost = true;
	}

	public boolean isLost() {
		return lost;
	}

	public boolean isWon() {
		return won;
	}

	public void increaseOutbreakLevel(int count) {
		outbreakLevel += count;
		if (outbreakLevel >= 8) {
			triggerLose();
		}
	}

	public int getOutbreakLevel() {
		return outbreakLevel;
	}

	public int getInfectionRate() {
		int[] infectionRates = GameProperty.getInstance().getIntArray("INFECTION_RATES");
		return infectionRates[infectionIndex];
	}

	public void increaseInfectionRate() {
		infectionIndex++;
	}

}
