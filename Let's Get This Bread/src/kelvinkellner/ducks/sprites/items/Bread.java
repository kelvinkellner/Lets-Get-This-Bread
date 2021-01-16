package kelvinkellner.ducks.sprites.items;

import kelvinkellner.ducks.sprites.creatures.Player;

public class Bread extends Item {
	
	// Non-Static Variables
	public int value;
	
	// Constructor Methods
	public Bread(int x, int y, int value)
	{
		super(x, y, "Bread");
		this.value = value;
		super.setBoundSkew(1, 40, -3, -40);
	}
	
	// Instance Methods
	public void use(Player p)
	{
		p.bread += value;
		System.out.println("The player collected " + value + " bread.");
		this.selfDestruct();
	}
}
