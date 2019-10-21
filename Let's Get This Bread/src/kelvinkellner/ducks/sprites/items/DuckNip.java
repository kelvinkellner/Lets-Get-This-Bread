package kelvinkellner.ducks.sprites.items;

import kelvinkellner.ducks.sprites.creatures.Player;

public class DuckNip extends Item {
	
	// Static Variables
	private static int healthBoost = 5;
	private static int strengthBoost = 1;
	
	// Constructor Methods
	public DuckNip(int x, int y)
	{
		super(x, y, "DuckNip");
		super.setBoundSkew(12, 42, -24, -42);
	}
	
	// Instance Methods
	public void use(Player p)
	{
		p.boostMaxHealth(healthBoost);
		p.boostStrength(strengthBoost);
		System.out.println("The player collected Duck Nip. Their strength and maximum health have been boosted.");
		this.selfDestruct();
	}
}
