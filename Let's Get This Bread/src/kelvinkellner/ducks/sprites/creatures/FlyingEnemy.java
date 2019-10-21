package kelvinkellner.ducks.sprites.creatures;

public class FlyingEnemy extends Enemy {

	// Non-Static Variables
	protected int flyHeight;
	
	// Constructor Methods
	protected FlyingEnemy(int x, int y, int health, int strength, double accuracy, int speed, int flyHeight, String type) {
		super(x, y, health, strength, accuracy, speed, type);
		this.flyHeight = flyHeight;
	}

}
