package kelvinkellner.ducks.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet
{
	//private static BufferedImage spriteSheet;
	private static final int TILE_SIZE = 64;
	
	// Class Methods
	public static BufferedImage loadSprite(String file)
	{
        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(new File("img/" + file + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }

    public static BufferedImage getSprite(BufferedImage spriteSheet, int xGrid, int yGrid)
    {
        return spriteSheet.getSubimage(xGrid * TILE_SIZE, yGrid * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
    
    public static BufferedImage[] getAllFrames(BufferedImage spriteSheet)
    {
    	BufferedImage[] frames = new BufferedImage[spriteSheet.getWidth()/TILE_SIZE*spriteSheet.getHeight()/TILE_SIZE];
    	
    	int count = 0;
    	for(int j=0; j<spriteSheet.getHeight()/TILE_SIZE; j++)
		{
    		for(int i=0; i<spriteSheet.getWidth()/TILE_SIZE; i++)
        	{
    			frames[count] = getSprite(spriteSheet, i, j);
    			count++;
        	}
		}
    	
    	
    	return frames;
    }
    
    public static Animation createAnimation(String file, int frameDelay)
    {
    	return new Animation(getAllFrames(loadSprite(file)), frameDelay);
    }
}
