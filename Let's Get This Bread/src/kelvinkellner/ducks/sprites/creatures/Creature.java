package kelvinkellner.ducks.sprites.creatures;

import java.util.Random;

import kelvinkellner.ducks.sprites.Sprite;
import kelvinkellner.ducks.sprites.items.Item;

public class Creature extends Sprite {

	// Non-Static Variables
	protected int health;
	protected int maxHealth;
	protected int strength;
	protected double accuracy;
	public int speed;
	
	public boolean attacking;
	public int attackFrames;
	public int facing; // -1 Left, 1 Right
	public int lastFacing;
	public double hold = 0.000;
	
	// Constructor Methods
	Creature(int x, int y, int health, int strength, double accuracy, int speed)
	{
		super(x, y);
		this.health = health;
		this.maxHealth = health;
		this.strength = strength;
		this.accuracy = accuracy;
		this.speed = speed;
		this.attacking = false;
		this.facing = 1;
		this.lastFacing = this.facing;
		this.attackFrames = 0;
	}
	
	// Instance Methods
	public void takeDamage(int amount)
	{
		this.health -= amount;
		if(this.health < 0)
			this.health = 0;
		if(this.health == 0)
		{
			this.kill();
			System.out.println("A creature has been killed.");
		}
	}
	
	public void heal(int amount)
	{
		this.health += amount;
		if(this.health > this.maxHealth)
			this.healToMax();
	}
	
	public void healToMax()
	{
		this.health = this.maxHealth;
	}
	
	public void boostMaxHealth(int amount)
	{
		this.maxHealth += amount;
		this.healToMax();
	}
	
	public void boostStrength(int amount)
	{
		this.strength += amount;
	}
	
	public Item drop(Item drop)
	{
		drop.setPosition(this.x+this.width/2, this.y+this.height/2);
		drop.setVisible(true);
		return drop;
	}
	
	public void kill()
	{
		super.selfDestruct();
	}
	
	public void attack(Creature victim)
	{
		Random random = new Random();
		
		int damage = 0;
		
		boolean hit = false;
		double chance = random.nextDouble() - 0.00001;
		
		boolean negativeGain = false;
		
		if(chance<this.accuracy)
			hit = true;
		
		if(hit)
		{
			int max = (int) Math.round(((double)this.strength)/5.0 + random.nextDouble() - random.nextDouble());
			if(max<=0)
			{
				negativeGain=true;
				max=0;
			}
			int gain = random.nextInt(2*max + 1) - max;
			if(negativeGain)
				gain=-gain;
			if(random.nextBoolean())
				gain += (int) Math.round(((double)(this.strength + this.health + this.speed))/20.0 * (random.nextDouble() + 0.75));
			damage = this.strength + gain;
			if(damage<0)
				damage=0;
		}
		
		System.out.println(damage + " damage was dealt.");
		victim.takeDamage(damage);
	}
	
	// Getters and Setters
	public void changeSpeed(int amount)
	{
		this.speed += amount;
	}
	
	public int getHealth()
	{
		return this.health;
	}
	
	public int getMaxHealth()
	{
		return this.maxHealth;
	}
	
	public double getAccuracy()
	{
		return this.accuracy;
	}
}
