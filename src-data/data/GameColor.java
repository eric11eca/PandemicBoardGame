package data;

/**
 * Colors used in the game mechanics, not to be mixed with color used in
 * rendering
 *
 */
public enum GameColor {
	RED("RED"), BLUE("BLUE"), YELLOW("YELLOW"), BLACK("BLACK");

	@Deprecated // This will be removed once all color mechanics in game use GameColor instead
				// of String
	public final String compatibility_ColorString;

	GameColor(String color) {
		compatibility_ColorString = color;
	}

	@Deprecated
	public static GameColor compatibility_getByName(String name) {
		for (GameColor c : GameColor.values()) {
			if (c.compatibility_ColorString.equals(name))
				return c;
		}
		throw new RuntimeException("Invalid GameColor");
	}
}
