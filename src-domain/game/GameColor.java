package game;

import lang.I18n;

/**
 * Colors used in the game mechanics, not to be mixed with color used in
 * rendering
 *
 */
public enum GameColor {
	RED, BLUE, YELLOW, BLACK;

	public String getName() {
		return I18n.format("color." + name());
	}
}
