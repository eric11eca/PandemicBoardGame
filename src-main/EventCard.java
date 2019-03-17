
public class EventCard implements PCard {
	String name;
	public EventCard(String string) {
		name = string;
	}

	@Override
	public String getName() {
		return name;
	}

}
