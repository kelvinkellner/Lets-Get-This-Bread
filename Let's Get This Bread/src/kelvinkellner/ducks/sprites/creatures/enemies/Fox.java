package kelvinkellner.ducks.sprites.creatures.enemies;

import java.util.Random;

import kelvinkellner.ducks.sprites.creatures.WalkingEnemy;
import kelvinkellner.ducks.sprites.items.Grain;
import kelvinkellner.ducks.sprites.items.HealingBerry;

public class Fox extends WalkingEnemy {
	
	private static int health = 20;
	private static int strength = 4;
	private static double accuracy = 0.60;
	private static int speed = 20;
	
	private static int minGrain = 6;
	private static int maxGrain = 16;
	private static final double specialDropChance = 0.05;

	// Constructor Methods
	public Fox(int x, int y) {
		super(x, y, health, strength, accuracy, speed, "Fox"); // Health, Strength, Accuracy, Speed
		Random random = new Random();
		double chance = random.nextDouble();
		if(chance < specialDropChance)
			specialDrop = true;
		else
			specialDrop = false;
		grainDrop = random.nextInt(maxGrain - minGrain + 1) + minGrain;
		
		super.walkMin = 2;
		super.walkMax = 8;
	}
	
	public void kill()
	{
		System.out.println(grainDrop + " grain dropped.");
		super.drop(new Grain(0,0,grainDrop));
		if(specialDrop)
			super.drop(new HealingBerry(0,0)); // CHANGE THE CONSTRUCTOR NAME TO CHANGE SPECIAL DROP
		super.kill();
	}

}
