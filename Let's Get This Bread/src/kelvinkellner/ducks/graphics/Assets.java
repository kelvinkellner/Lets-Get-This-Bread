package kelvinkellner.ducks.graphics;

import java.awt.image.BufferedImage;

public class Assets {
	
	// Duck
	public static Animation duckIdleRight = SpriteSheet.createAnimation("DuckIdleRight", 15);
	public static Animation duckIdleLeft = SpriteSheet.createAnimation("DuckIdleLeft", 15);
	
	public static Animation duckRight = SpriteSheet.createAnimation("DuckRight", 5);
	public static Animation duckLeft = SpriteSheet.createAnimation("DuckLeft", 5);
	
	public static Animation duckJumpRight = SpriteSheet.createAnimation("DuckJumpRight", 15);
	public static Animation duckJumpLeft = SpriteSheet.createAnimation("DuckJumpLeft", 15);
	
	public static Animation duckAttackRight = SpriteSheet.createAnimation("DuckAttackRight", 5);
	public static Animation duckAttackLeft = SpriteSheet.createAnimation("DuckAttackLeft", 5);
	
	// Fox
	public static Animation foxIdleRight = SpriteSheet.createAnimation("FoxIdleRight", 15);
	public static Animation foxIdleLeft = SpriteSheet.createAnimation("FoxIdleLeft", 15);
	
	public static Animation foxRight = SpriteSheet.createAnimation("FoxRight", 5);
	public static Animation foxLeft = SpriteSheet.createAnimation("FoxLeft", 5);
	
	public static Animation foxJumpRight = SpriteSheet.createAnimation("FoxJumpRight", 5);
	public static Animation foxJumpLeft = SpriteSheet.createAnimation("FoxJumpLeft", 5);
	
	public static Animation foxAttackRight = SpriteSheet.createAnimation("FoxAttackRight", 5);
	public static Animation foxAttackLeft = SpriteSheet.createAnimation("FoxAttackLeft", 5);
	
	// Creatures
	public static Animation crocodile = SpriteSheet.createAnimation("test", 60);
	public static Animation eagle = SpriteSheet.createAnimation("test", 60);
	public static Animation turtle = SpriteSheet.createAnimation("test", 60);
	
	// Bread
	public static Animation breadDrop = SpriteSheet.createAnimation("BreadDrop", 6000);
	public static Animation breadDropFalling = SpriteSheet.createAnimation("BreadDropFalling", 60);
	
	// Items
	public static Animation omega3FishOil = SpriteSheet.createAnimation("Omega3FishOil", 6000);
	public static Animation duckNip = SpriteSheet.createAnimation("DuckNip", 6000);
	public static Animation healingBerry = SpriteSheet.createAnimation("HealingBerry", 45);
	
	// Blocks
	public static Animation grass = SpriteSheet.createAnimation("Grass", 6000);
	public static Animation dirt = SpriteSheet.createAnimation("Dirt", 6000);
	
	// Icons
	public static BufferedImage boost = SpriteSheet.loadSprite("Energy");
	
}
