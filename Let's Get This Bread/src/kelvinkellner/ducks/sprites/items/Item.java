package kelvinkellner.ducks.sprites.items;

import kelvinkellner.ducks.sprites.Sprite;

public class Item extends Sprite {

	// Non-Static Variables
	public String type;
	
	// Constructor Methods
	Item(int x, int y, String type) {
		super(x, y);
		this.type = type;
	}

}
