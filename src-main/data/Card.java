package data;

public class Card extends DeckComponent{
	private String name;
	private String color;
	private Board.CardType type;
	
	public Card(String name, String color, Board.CardType type) {
		this.name = name;
		this.color = color;
		this.type = type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getColor() {
		return this.color;
	}
	
	public Board.CardType getType(){
		return this.type;
	}
}
