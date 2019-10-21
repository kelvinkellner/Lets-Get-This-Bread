package kelvinkellner.ducks.sprites;

public class Block extends Sprite {

	// Non-Static Variables
	private int hitsLeft;
	public String type;
	
	// Constructor Methods
	public Block(int x, int y, String type)
	{
		super(x, y);
		this.type = type;
		this.hitsLeft = -1;
		super.solid = true;
		super.gravity = false;
	}
	
	Block(int x, int y, int hitsNeeded)
	{
		super(x, y);
		this.hitsLeft = hitsNeeded;
	}
	
	// Instance Methods
	public void hit()
	{
		this.hitsLeft--;
		if(hitsLeft==0)
			super.selfDestruct();
	}
	
}
