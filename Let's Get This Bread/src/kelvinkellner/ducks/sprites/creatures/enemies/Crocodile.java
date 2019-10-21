package kelvinkellner.ducks.sprites.creatures.enemies;

import java.util.Random;

import kelvinkellner.ducks.sprites.creatures.WalkingEnemy;
import kelvinkellner.ducks.sprites.items.Grain;
import kelvinkellner.ducks.sprites.items.HealingBerry;

public class Crocodile extends WalkingEnemy {
	
	private static int health = 40;
	private static int strength = 6;
	private static double accuracy = 0.60;
	private static int speed = 10;
	
	private static int minGrain = 8;
	private static int maxGrain = 20;
	private static final double specialDropChance = 0.20;

	// Constructor Methods
	public Crocodile(int x, int y) {
		super(x, y, health, strength, accuracy, speed, "Crocodile"); // Health, Strength, Accuracy, Speed
		Random random = new Random();
		double chance = random.nextDouble();
		if(chance < specialDropChance)
			specialDrop = true;
		else
			specialDrop = false;
		grainDrop = random.nextInt(maxGrain - minGrain + 1) + minGrain;
		
		super.walkMin = 0;
		super.walkMax = 1;
	}
	
	public void kill()
	{
		super.drop(new Grain(0,0,grainDrop));
		if(specialDrop)
			super.drop(new HealingBerry(0,0)); // CHANGE THE CONSTRUCTOR NAME TO CHANGE SPECIAL DROP
		super.kill();
	}

}