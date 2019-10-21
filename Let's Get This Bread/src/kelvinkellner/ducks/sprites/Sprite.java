package kelvinkellner.ducks.sprites;
import java.awt.Image;
import java.awt.Rectangle;

public class Sprite {
	
	// Non-Static Variables
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int tileSize;
	
	protected Image image;
	protected boolean visible;
	
	public boolean solid;
	private Rectangle bounds;
	public boolean gravity;
	
	public int vx;
	public int vy;
	
	// Constructor Methods
	protected Sprite(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.bounds = new Rectangle(x, y, 64, 64);
		this.width = 64;
		this.height = 64;
		this.setVisible(true);
		this.gravity = true;
		this.vx = 0;
		this.vy = 0;
	}
	
	public void setVisible(boolean state)
	{
		this.visible = state;
	}
	
	public void setBoundSkew(int xshift, int yshift, int widthshift, int heightshift)
	{
		this.bounds = new Rectangle(this.bounds.x + xshift, this.bounds.y + yshift, this.bounds.width + widthshift, this.bounds.height + heightshift);
	}
	
	/*
	public void setBoundsByImage()
	{
		this.bounds = new Rectangle(x, y, TILE_SIZE, TILE_SIZE);
	}
	*/
	
	public void moveBounds(int deltax, int deltay)
	{
		this.bounds = new Rectangle(this.bounds.x + deltax, this.bounds.y + deltay, this.bounds.width, this.bounds.height);
	}
	
	public Rectangle getBounds()
	{
		return bounds;
	}
	
	public void selfDestruct()
	{
		this.setVisible(false);
		this.width = 0;
		this.height = 0;
		this.bounds = new Rectangle(0,0,0,0);
	}
	
	public void move(int deltax, int deltay)
	{
		this.setPosition(x+deltax, y+deltay);
	}
	
	// Class Methods
	public static boolean collides(Sprite a, Sprite b)
	{
		Rectangle aBound = a.getBounds();
		Rectangle bBound = b.getBounds();
		
		if(aBound.intersects(bBound) || aBound.contains(bBound) || bBound.contains(aBound))
			return true;
		return false;
	}
	
	// Setters and Getters
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public boolean isVisible()
	{
		return visible;
	}
	
	public void setPosition(int x, int y)
	{
		this.moveBounds(x-this.x, y-this.y);
		this.x = x;
		this.y = y;
	}
}
