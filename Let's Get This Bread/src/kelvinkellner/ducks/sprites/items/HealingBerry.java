package kelvinkellner.ducks.sprites.items;

import kelvinkellner.ducks.sprites.creatures.Player;

public class HealingBerry extends Item {

	// Constructor Methods
	public HealingBerry(int x, int y)
	{
		super(x, y, "HealingBerry");
		super.setBoundSkew(18, 1, -36, -1);
	}
		
	// Instance Methods
	public void use(Player p)
	{
		p.healToMax();
		System.out.println("The player collected a healing berry. Their health is now full.");
		this.selfDestruct();
	}
	
}
