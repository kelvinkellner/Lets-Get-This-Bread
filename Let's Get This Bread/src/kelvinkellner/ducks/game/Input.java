package kelvinkellner.ducks.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import kelvinkellner.ducks.sprites.creatures.Player;

public class Input implements KeyListener {

	// Static Variables
	private static final int LEFT = KeyEvent.VK_A;
	private static final int RIGHT = KeyEvent.VK_D;
	
	private static final int JUMP = KeyEvent.VK_W;
	private static final int ATTACK = KeyEvent.VK_SPACE;
	
	private static final int JUMPS_MAX = 5;
	
	// Non-Static Variables
	public Player player;
	
	// Constructor Methods
	public Input(Player player)
	{
		this.player = player;
	}
	
	// KeyListener Methods
	@Override
	public synchronized void keyPressed(KeyEvent e) {
		{
			if(player.isVisible())
			{
				if(e.getKeyCode() == LEFT)
				{
					player.vx = -player.speed/10;
					player.facing = -1;
				}
				else if(e.getKeyCode() == RIGHT)
				{
					player.vx = player.speed/10;
					player.facing = 1;
				}
				else if(e.getKeyCode() == JUMP)
				{
					if(player.jumpsInARow<JUMPS_MAX)
					{
						player.vy = -player.jumpSpeed;
						player.jumpsInARow++;
					}
				}
				else if(e.getKeyCode() == ATTACK)
				{
					int maxFrame = 24;
					if(player.attackFrames == 0)
					{
						player.attacking = true;
						player.attackFrames = maxFrame;
					}
					else if(player.attackFrames != maxFrame)
						player.attacking = false;
				}
			}
		}
	}

	@Override
	public synchronized void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == LEFT)
		{
			if(player.vx < 0)
				player.vx = 0;
		}
		else if(e.getKeyCode() == RIGHT)
		{
			if(player.vx > 0)
				player.vx = 0;
		}
		else if(e.getKeyCode() == ATTACK)
			player.attacking = false;
		else if(e.getKeyCode() == JUMP)
			player.vy--;
	}

	@Override
	public synchronized void keyTyped(KeyEvent e) {
		
	}

}
