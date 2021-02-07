package kelvinkellner.ducks.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Random;

import kelvinkellner.ducks.graphics.Animation;
import kelvinkellner.ducks.graphics.Assets;
import kelvinkellner.ducks.sprites.Block;
import kelvinkellner.ducks.sprites.Sprite;
import kelvinkellner.ducks.sprites.creatures.Creature;
import kelvinkellner.ducks.sprites.creatures.Enemy;
import kelvinkellner.ducks.sprites.items.Bread;
import kelvinkellner.ducks.sprites.items.HealingBerry;
import kelvinkellner.ducks.sprites.items.Omega3FishOil;
import kelvinkellner.ducks.sprites.items.DuckNip;

public class Game implements Runnable {
	
	private static String stageFile = "map.txt";

	// Display Components
	public Display display;
	private String title;
	private int width, height;
	
	private BufferStrategy bs;
	private Graphics g;
	
	private Thread thread;
	private boolean running = false;
	
	// Rendering Components
	private int baseX;
	private int baseY;
	
	// Game Components
	private Stage stage;
	private Input input;
	
	public Game(String title, int width, int height)
	{
		this.title = title;
		this.width = width;
		this.height = height;
		
		run();
	}
	
	public void init()
	{
		// Initialize Display
		this.display = new Display(title, width, height);
		
		this.baseX = 0;
		this.baseY = 0;
		
		// Initialize Stage
		try
		{
			this.stage = new Stage(stageFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		// Initialize Key Input
		this.input = new Input(stage.player);
		this.display.getFrame().addKeyListener(input);
		
		// Run Game
		this.running = true;
	}
	
	int globalTick = 0;
	
	@Override
	public void run() {
		
		init();
		
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		while(running)
		{
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1)
			{
				//tick();
				render();
				ticks++;
				delta--;
			}
			
			if(timer >= 1000000000)
			{
				//System.out.println("Ticks and Frames: " + ticks);
				ticks = 0;
				timer = 0;
			}
		}
		
		//stop();
	}
	
	public void render()
	{
		// Initialize Graphics
		bs = display.getCanvas().getBufferStrategy();
		if(bs==null)
		{
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		
		// Move Creatures
		moveCreatures();
		gravity();
		
		fixCamera(g);
		
		collisions();
		
		specialDecay();
		
		// Render Graphics
		//renderBounds(g); // render collision boxes
		renderBlocks(g);
		renderItems(g);
		renderCreatures(g);
		renderGUI(g);
		
		// Reset Graphics
		bs.show();
		g.dispose();
	}
	
	private void collisions() {
		
		// Enemy Collisions
		for(int i=0; i<stage.enemies.size(); i++)
		{
			if(Sprite.collides(stage.player, stage.enemies.get(i)))
			{
				//enemies.get(i).attack(player);
				if(stage.player.attacking)
				{
					stage.player.attack(stage.enemies.get(i));
					if(stage.enemies.get(i).getHealth()==0)
					{
						stage.items.add(stage.enemies.get(i).drop(new Bread(0, 0, stage.enemies.get(i).breadDrop)));
						stage.sprites.add(stage.items.get(stage.items.size()-1));
						if(stage.enemies.get(i).specialDrop)
						{
							if(stage.enemies.get(i).type == "Fox")
								stage.items.add(stage.enemies.get(i).drop(new HealingBerry(0,0)));
							else if(stage.enemies.get(i).type == "Eagle")
								stage.items.add(stage.enemies.get(i).drop(new HealingBerry(0,0)));
							else if(stage.enemies.get(i).type == "Crocodile")
								stage.items.add(stage.enemies.get(i).drop(new HealingBerry(0,0)));
							stage.sprites.add(stage.items.get(stage.items.size()-1));
						}
						Creature gone = stage.enemies.get(i);
						stage.enemies.remove(i);
						if(stage.creatures.contains(gone))
							stage.creatures.remove(gone);
						if(stage.sprites.contains(gone))
							stage.sprites.remove(gone);
					}
				}
			}
		}
		
		// Item Collisions
		for(int i=0; i<stage.items.size(); i++)
		{
			if(Sprite.collides(stage.player, stage.items.get(i)))
			{
				if(stage.items.get(i).type == "Bread")
					((Bread) stage.items.get(i)).use(stage.player);
				else if(stage.items.get(i).type == "Omega3FishOil")
					((Omega3FishOil) stage.items.get(i)).use(stage.player);
				else if(stage.items.get(i).type == "DuckNip")
					((DuckNip) stage.items.get(i)).use(stage.player);
				else if(stage.items.get(i).type == "HealingBerry")
					((HealingBerry) stage.items.get(i)).use(stage.player);
			}
		}
	}

	public boolean isOnGround(Sprite s)
	{
		boolean touching = false;
		
		s.moveBounds(0, 1);
		for(int i=0; i<stage.blocks.size(); i++)
		{
			if(Sprite.collides(s, stage.blocks.get(i)))
			{
				if(s.getY() < stage.blocks.get(i).getY())
				{
					if(stage.blocks.get(i).solid)
						touching = true;
				}
			}
		}
		s.moveBounds(0, -1);
		return touching;
	}
	
	// MOVEMENTS
	public void moveCreatures()
	{
		// Accelerate Creature Speeds
		double maxSpeedMult = 2;
		
		for(int i=0; i<stage.creatures.size(); i++)
		{
			Creature c = stage.creatures.get(i);
			
			if(c.vx<0)
				c.facing = -1;
			else if(c.vx>0)
				c.facing = 1;
			
			if(c.facing == c.lastFacing && c.vx != 0)
				c.hold += 0.2;
			else
				c.hold = 0.00;
			
			if(c.hold > maxSpeedMult-1.0)
				c.hold = maxSpeedMult-1.0;
			
			c.lastFacing = c.facing;
			
			if(c.vx != 0)
				c.vx *= 1 + c.hold;
			
			if(c.vx > c.speed/5 || c.vx < -c.speed/5)
				c.vx = c.speed/5 * c.facing;
		}
		
		// Move Player
		if(stage.player.vx != 0)
			stage.player.move(stage.player.vx, 0);
		
		// Move Enemies
		for(int i=0; i<stage.enemies.size(); i++)
		{
			//System.out.println(stage.enemies.get(i).newWalkHoldTime());
			if(stage.enemies.get(i).isVisible())
			{
				// Walking
				if(stage.enemies.get(i).walkHoldTime == 0)
				{
					Random random = new Random();
					if(stage.enemies.get(i).vx == 0)
					{
						double chanceOfFollowing = 0.0;
						
						if(stage.enemies.get(i).type == "Crocodile")
							chanceOfFollowing = 9.5;
						else if(stage.enemies.get(i).type == "Eagle")
							chanceOfFollowing = 9.5;
						else if(stage.enemies.get(i).type == "Fox")
							chanceOfFollowing = 8.0;
						else if(stage.enemies.get(i).type == "SnappingTurtle")
							chanceOfFollowing = 0.0;
						
						// Follow Player
						if(chanceOfFollowing > Math.random())
						{
							int direction = 0;
							if(stage.player.getX() < stage.enemies.get(i).getX())
								direction = -1;
							else
								direction = 1;
							stage.enemies.get(i).vx = direction * (int)stage.enemies.get(i).speed/10;
							stage.enemies.get(i).walkHoldTime = stage.enemies.get(i).getMinWalkTime();
							System.out.println((int)stage.enemies.get(i).speed/10);
						}
						
						// Move Randomly
						else
						{
							stage.enemies.get(i).vx = (int)stage.enemies.get(i).speed/10;
							if(random.nextBoolean()) // Random Direction
								stage.enemies.get(i).vx *= -1;
							stage.enemies.get(i).walkHoldTime = stage.enemies.get(i).newWalkHoldTime();
						}
					}
					else
					{
						// Stop Between Strides
						stage.enemies.get(i).vx = 0;
						stage.enemies.get(i).walkHoldTime = random.nextInt(2*60)+30;
					}
				}
				else
				{
					// If Still in Motion
					stage.enemies.get(i).move(stage.enemies.get(i).vx, stage.enemies.get(i).vy);
					stage.enemies.get(i).walkHoldTime--;
				}
				
				// Attacking
				if(Math.abs(stage.player.getBounds().getX() - stage.enemies.get(i).getBounds().getX()) < 128)
				{
					// Calculate chance of attacking
					if(stage.enemies.get(i).getAccuracy() / 30.0 > Math.random()) {
						stage.enemies.get(i).attacking = true;
						stage.enemies.get(i).attackFrames = 25;
					}
					else
						stage.enemies.get(i).attacking = false;
					
					// Hurt player if enemy is close
					if(stage.enemies.get(i).attacking)
					{
						if(Sprite.collides(stage.player, stage.enemies.get(i)))
							stage.enemies.get(i).attack(stage.player);
					}
				}
			}
		}
		
		// Fix Creature Movement Errors
		for(int i=0; i<stage.creatures.size(); i++)
		{
			Creature c = stage.creatures.get(i);
			// Creature exited the sides of the stage
//			if(stage.creatures.get(i).getBounds().x < 0 || stage.creatures.get(i).getBounds().x+stage.creatures.get(i).getBounds().width > stage.stageWidth)
//				stage.creatures.get(i).move(-(int)(1 + stage.creatures.get(i).hold) * stage.creatures.get(i).vx, 0);
			while(c.getBounds().x < 0) {
				c.move(1, 0);
			}
			while(c.getBounds().x+c.getBounds().width > stage.stageWidth) {
				c.move(-1, 0);
			}
			
			// Creature hit a solid block
			for(int j=0;j<stage.blocks.size();j++)
			{
				Block b = stage.blocks.get(i);
				if(b.solid)
				{
					if(Sprite.collides(c, b))
					{
						c.move(-c.vx, 0);
						//stage.creatures.get(i).move(-(int)(1 + stage.creatures.get(i).hold) * stage.creatures.get(i).vx, 0);
									
						// Break for loop
						j=stage.blocks.size();
					}
				}
			}
		}
	}
	
	int accelerationMax = 8;
	int accelerationDelay = 5;
	int accelerationCount = 0;
	
	public void gravity()
	{
		for(int i=0; i<stage.sprites.size(); i++)
		{
			if(stage.sprites.get(i).gravity)
			{
				if(!isOnGround(stage.sprites.get(i)))
				{
					if(accelerationCount > accelerationDelay)
					{
						accelerationCount = 0;
						stage.sprites.get(i).vy++;
						if(stage.sprites.get(i).vy > accelerationMax)
							stage.sprites.get(i).vy = accelerationMax;
						
					}
					else
						accelerationCount++;
				}
				else
				{
					if(stage.sprites.get(i).vy > 0)
						stage.sprites.get(i).vy--;
				}
				gravityPatch(stage.sprites.get(i));
			}
			
			if(stage.sprites.get(i).vy != 0)
			{
				stage.sprites.get(i).move(0, stage.sprites.get(i).vy);
				
				for(int j=0;j<stage.blocks.size();j++)
				{
					if(Sprite.collides(stage.sprites.get(i), stage.blocks.get(j)))
					{
						//stage.sprites.get(i).move(0, -stage.sprites.get(i).vy);
						
						if(stage.sprites.get(i).vy > 0) {
							stage.sprites.get(i).vy *= -0.33;
						} else {
							stage.sprites.get(i).vy *= 0.88;
						}
						
						// Break for loop
						j=stage.blocks.size();
					}
				}
			}
		}
		
		// Reset Jumps In A Row Counter if the Player lands on the Ground
		if(isOnGround(stage.player))
			stage.player.jumpsInARow = 0;
	}
	
	public void gravityPatch(Sprite s)
	{
		// Prevents a Sprite from glitching through the ground when gravity is active
		while(isOnGround(s))
			s.move(0, -1);
		s.move(0, 1);
	}
	
	Rectangle camerawindow;
	
	public void fixCamera(Graphics g)
	{	
		int padding = (int)((this.width+this.height)/2 /2.25);
		
		Rectangle camera = new Rectangle(baseX + padding, baseY + (int)(0.5*padding), this.width - 2*padding, this.height - (int)(1.05*padding));
		camerawindow = camera;
		
		if(!camera.contains(stage.player.getBounds()))
		{
			if(stage.player.getX()<camera.x) // Off Left
			{
				if(stage.player.vx<0) // Moving Left
					baseX += stage.player.vx;
			}
			else if(stage.player.getX()+stage.player.getWidth()>camera.x+camera.width) // Off Right
			{
				if(stage.player.vx>0) // Moving Right
					baseX += stage.player.vx;
			}
			if(stage.player.getY()<camera.y) // Up Above
			{
				if(stage.player.vy<0) // Moving Up
					baseY += stage.player.vy +1;
				else
					baseY -= 1;
			}
			if(stage.player.getY()+stage.player.getHeight()>camera.y+camera.height) // Down Below
			{
				if(stage.player.vy>0) // Moving Down
					baseY += stage.player.vy+1;
				else
					baseY += 1;
			}
		}
		else
		{
			//baseX += stage.player.vx;
			//baseY += stage.player.vy;
		}
		
		// Prevent the Camera from fixing beyond the stage area
		if(baseX < 0)
			baseX = 0;
		else if(baseX+this.width > stage.stageWidth)
			baseX = stage.stageWidth - this.width;
		if(baseY+this.height > stage.stageHeight)
			baseY = stage.stageHeight - this.height;
	}
	
	// RENDERING
	public void renderBounds(Graphics g)
	{
		g.setColor(Color.yellow);
		
		for(int i=0; i<stage.sprites.size(); i++)
			g.fillRect(stage.sprites.get(i).getBounds().x - baseX, stage.sprites.get(i).getBounds().y - baseY, stage.sprites.get(i).getBounds().width, stage.sprites.get(i).getBounds().height);
	}
	
	public void renderItems(Graphics g)
	{
		for(int i=0; i<stage.items.size(); i++)
		{
			if(stage.items.get(i).type == "Bread")
			{
				renderAnySprite(g, stage.items.get(i), Assets.bread);
//				if(stage.items.get(i).vy == 0)
//					renderAnySprite(g, stage.items.get(i), Assets.breadDrop);
//				else
//					renderAnySprite(g, stage.items.get(i), Assets.breadDropFalling);
			}
			else if(stage.items.get(i).type == "Omega3FishOil")
				renderAnySprite(g, stage.items.get(i), Assets.omega3FishOil);
			else if(stage.items.get(i).type == "DuckNip")
				renderAnySprite(g, stage.items.get(i), Assets.duckNip);
			else if(stage.items.get(i).type == "HealingBerry")
				renderAnySprite(g, stage.items.get(i), Assets.healingBerry);
		}
	}
	
	public void renderCreatures(Graphics g)
	{
		// Render Player
		if(stage.player.attackFrames > 0)
		{
			if(stage.player.facing == 1)
				renderAnySprite(g, stage.player, Assets.duckAttackRight);
			else
				renderAnySprite(g, stage.player, Assets.duckAttackLeft);
			stage.player.attackFrames--;
		}
		else
		{
			if(stage.player.vy==0)
			{
				if(stage.player.vx == 0)
				{
					if(stage.player.facing == 1)
						renderAnySprite(g, stage.player, Assets.duckIdleRight);
					else
						renderAnySprite(g, stage.player, Assets.duckIdleLeft);
				}
				else if(stage.player.vx > 0)
					renderAnySprite(g, stage.player, Assets.duckRight);
				else if(stage.player.vx < 0)
					renderAnySprite(g, stage.player, Assets.duckLeft);
			}
			else
			{
				if(stage.player.facing == 1)
					renderAnySprite(g, stage.player, Assets.duckJumpRight);
				else
					renderAnySprite(g, stage.player, Assets.duckJumpLeft);
			}
		}
		
		// Render enemies
		for(int i=0;i<stage.enemies.size();i++)
		{
			String type;
			Enemy e = stage.enemies.get(i);
			
			// Find enemy type
			if(stage.enemies.get(i).type == "Fox") {
				if(e.attackFrames > 0)
				{
					if(e.facing == 1)
						renderAnySprite(g, e, Assets.foxAttackRight);
					else
						renderAnySprite(g, e, Assets.foxAttackLeft);
					e.attackFrames--;
				}
				else
				{
					if(e.vy==0)
					{
						if(e.vx == 0)
						{
							if(e.facing == 1)
								renderAnySprite(g, e, Assets.foxIdleRight);
							else
								renderAnySprite(g, e, Assets.foxIdleLeft);
						}
						else if(e.vx > 0)
							renderAnySprite(g, e, Assets.foxRight);
						else if(e.vx < 0)
							renderAnySprite(g, e, Assets.foxLeft);
					}
					else
					{
						if(e.facing == 1)
							renderAnySprite(g, e, Assets.foxJumpRight);
						else
							renderAnySprite(g, e, Assets.foxJumpLeft);
					}
				}
			}
			/*
			else if(stage.enemies.get(i).type == "Eagle")
				renderAnySprite(g, stage.enemies.get(i), Assets.fox);
			else if(stage.enemies.get(i).type == "Crocodile")
				renderAnySprite(g, stage.enemies.get(i), Assets.fox);
			else if(stage.enemies.get(i).type == "Eagle")
				renderAnySprite(g, stage.enemies.get(i), Assets.fox);
				*/
		}
	}
	
	public void renderBlocks(Graphics g)
	{
		for(int i=0; i<stage.blocks.size(); i++)
		{
			if(stage.blocks.get(i).type == "Grass")
				renderAnySprite(g, stage.blocks.get(i), Assets.grass);
			else if(stage.blocks.get(i).type == "Dirt")
				renderAnySprite(g, stage.blocks.get(i), Assets.dirt);
		}
	}
	
	public void renderGUI(Graphics g)
	{
		int padding = 5;
		int widthRatio = 4;
		int heightRatio = 20;
		int arc = 8;
		
		g.setColor(Color.RED);
		g.fillRoundRect(padding, padding, this.width/widthRatio, this.height/heightRatio, arc, arc);
		g.setColor(Color.GREEN);
		g.fillRoundRect(padding, padding, (int)(((double)(this.width/widthRatio))*((double)stage.player.getHealth()/(double)stage.player.getMaxHealth())), this.height/heightRatio, arc, arc);
		g.setColor(Color.DARK_GRAY);
		g.drawRoundRect(padding, padding, this.width/widthRatio, this.height/heightRatio, arc, arc);
		
		g.drawString("Health: " + stage.player.getHealth() + "/" + stage.player.getMaxHealth(), padding + 5 + (this.width/widthRatio)/4, padding + 10 + 5);
		
		g.drawString("Bread: " + stage.player.getBread(), 3*padding + this.width/widthRatio, padding + 15);
		
		g.drawString("Jumps left: " + (5-stage.player.jumpsInARow), 3*padding + this.width/widthRatio, padding + 30);
		
		if(stage.player.boostTime>0)
		{
			g.drawImage(Assets.boost, padding, padding + 40, null);
			g.drawString(stage.player.boostTime/60+"", padding + 28, padding + 57);
		}
		
		//System.out.println(camerawindow.width +","+ camerawindow.height);
		//g.fillRect(camerawindow.x, camerawindow.y, camerawindow.width, camerawindow.height);
	}
	
	public void renderAnySprite(Graphics g, Sprite sprite, Animation a)
	{
		if(sprite.isVisible())
		{
			if(sprite.getX()-baseX<this.width && sprite.getY()-baseY<this.height && sprite.getX()+sprite.getWidth()>baseX && sprite.getY()+sprite.getHeight()>baseY)	
				g.drawImage(a.nextFrame(), sprite.getX()-baseX, sprite.getY()-baseY, null);
		}
	}
	
	public void specialDecay()
	{
		if(stage.player.boostTime>0)
			stage.player.boostTime--;
		else if(stage.player.boostTime==0)
		{
			stage.player.boostTime = -1;
			stage.player.speed -= Omega3FishOil.getSpeedBoost();
			stage.player.jumpSpeed -= Omega3FishOil.getJumpBoost();
		}
	}
	
	public synchronized void start()
	{
		if(!running)
			return;
		running = false;
		try
		{
			thread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
