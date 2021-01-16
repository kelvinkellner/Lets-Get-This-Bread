package kelvinkellner.ducks.sprites.creatures.enemies;

import java.util.Random;

import kelvinkellner.ducks.sprites.creatures.WalkingEnemy;
import kelvinkellner.ducks.sprites.items.Bread;

public class SnappingTurtle extends WalkingEnemy {
	
	private static int health = 10;
	private static int strength = 2;
	private static double accuracy = 0.50;
	private static int speed = 10;
	
	private static int minGrain = 2;
	private static int maxGrain = 8;

	// Constructor Methods
	public SnappingTurtle(int x, int y) {
		super(x, y, health, strength, accuracy, speed, "SnappingTutle"); // Health, Strength, Accuracy, Speed
		Random random = new Random();
		breadDrop = random.nextInt(maxGrain - minGrain + 1) + minGrain;
		
		super.walkMin = 4;
		super.walkMax = 8;
	}
	
	public void kill()
	{
		super.drop(new Bread(0,0,breadDrop));
		super.kill();
	}

}
