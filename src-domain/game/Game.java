package game;

import parse.InfectionRateLoader;

public class Game {

	private boolean lost;
	private boolean won;
	private int outbreakLevel;
	private int infectionIndex;
	private int[] infectionRates;

	public Game() {
		lost = false;
		won = false;
		outbreakLevel = 0;
		infectionRates = new InfectionRateLoader().getInfectionRates();
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
		return infectionRates[infectionIndex];
	}

	public void increaseInfectionRate() {
		infectionIndex++;
	}

}
