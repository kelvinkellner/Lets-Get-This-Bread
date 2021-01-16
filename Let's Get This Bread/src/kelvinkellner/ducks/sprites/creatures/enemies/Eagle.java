package kelvinkellner.ducks.sprites.creatures.enemies;

import java.util.Random;

import kelvinkellner.ducks.sprites.creatures.FlyingEnemy;
import kelvinkellner.ducks.sprites.items.Bread;
import kelvinkellner.ducks.sprites.items.HealingBerry;

public class Eagle extends FlyingEnemy {
	
	private static int health = 40;
	private static int strength = 6;
	private static double accuracy = 0.60;
	private static int speed = 10;
	
	private static int flyHeight = 20;
	
	private static int minGrain = 8;
	private static int maxGrain = 20;
	private static final double specialDropChance = 0.10;

	// Constructor Methods
	public Eagle(int x, int y) {
		super(x, y, health, strength, accuracy, speed, flyHeight, "Eagle"); // Health, Strength, Accuracy, Speed
		Random random = new Random();
		double chance = random.nextDouble();
		if(chance < specialDropChance)
			specialDrop = true;
		else
			specialDrop = false;
		breadDrop = random.nextInt(maxGrain - minGrain + 1) + minGrain;
		
		super.walkMin = 6;
		super.walkMax = 6;
	}
	
	public void kill()
	{
		super.drop(new Bread(0,0,breadDrop));
		if(specialDrop)
			super.drop(new HealingBerry(0,0)); // CHANGE THE CONSTRUCTOR NAME TO CHANGE SPECIAL DROP
		super.kill();
	}

}