package kelvinkellner.ducks.sprites.creatures;

import kelvinkellner.ducks.sprites.items.Item;

// java.awt.event.KeyEvent;

public class Player extends Creature {
	
	// Non-Static Variables
	public Item[] items;
	public int bread;
	
	public int jumpSpeed;
	public int jumpsInARow;
	
	public int boostTime = -1;
	
	// Constructor Methods
	public Player(int x, int y, int bread)
	{
		super(x, y, 10, 2, 0.80, 15); // Health, Strength, Accuracy, Speed
		this.bread = bread;
		this.jumpSpeed = 5;
		this.items = new Item[0];
		super.setBoundSkew(10, 12, -20, -12);
	}
	
	
	// Getters and Setters
	public int getBread()
	{
		return this.bread;
	}
	
	/*
	Player(int x, int y, int health, int strength, int accuracy, int speed, int bread)
	{
		super(x, y, health, strength, accuracy, speed); // Health, Strength, Accuracy, Speed
		this.bread = bread;
		this.items = new Item[0];
	}
	*/
}
