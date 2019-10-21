 package kelvinkellner.ducks.sprites.items;

import kelvinkellner.ducks.sprites.creatures.Player;

public class Omega3FishOil extends Item {
	
	// Static Variables
	private static int speedBoost = 5;
	private static int jumpBoost = 1;
	private static int boostTime = 31;
	
	// Constructor Methods
	public Omega3FishOil(int x, int y)
	{
		super(x, y, "Omega3FishOil");
		super.setBoundSkew(18, 28, -36, -28);
	}
	
	// Instance Methods
	public void use(Player p)
	{
		p.changeSpeed(speedBoost);
		p.jumpSpeed += jumpBoost;
		p.boostTime = 60*boostTime;
		System.out.println("The player collected Omega 3 Fish Oil and received a speed and jump boost.");
		this.selfDestruct();
	}
	
	public static int getSpeedBoost()
	{
		return speedBoost;
	}
	
	public static int getJumpBoost()
	{
		return jumpBoost;
	}
}
