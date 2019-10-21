package kelvinkellner.ducks.sprites.items;

import kelvinkellner.ducks.sprites.creatures.Player;

public class Grain extends Item {
	
	// Non-Static Variables
	public int value;
	
	// Constructor Methods
	public Grain(int x, int y, int value)
	{
		super(x, y, "Grain");
		this.value = value;
		super.setBoundSkew(1, 40, -3, -40);
	}
	
	// Instance Methods
	public void use(Player p)
	{
		p.bread += value;
		System.out.println("The player collected " + value + " grains.");
		this.selfDestruct();
	}
}
