package kelvinkellner.ducks.sprites.creatures;

import java.util.Random;

public class Enemy extends Creature {
	
	// Non-Static Variables
	
	public int grainDrop;
	public boolean specialDrop;
	public String type;
	
	protected int walkMax;
	protected int walkMin;
	public int walkHoldTime;
	
	// Constructor Methods
	Enemy(int x, int y, int health, int strength, double accuracy, int speed, String type) {
		super(x, y, health, strength, accuracy, speed);
		this.type = type;
	}
	
	public int newWalkHoldTime()
	{
		Random random = new Random();
		return random.nextInt(60*walkMax - 60*walkMin + 1) + 60*walkMin;
	}
	
	public int getMinWalkTime()
	{
		return 60*walkMin;
	}

}
