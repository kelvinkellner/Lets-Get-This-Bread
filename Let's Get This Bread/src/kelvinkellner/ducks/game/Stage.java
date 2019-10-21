package kelvinkellner.ducks.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import kelvinkellner.ducks.sprites.Block;
import kelvinkellner.ducks.sprites.Sprite;
import kelvinkellner.ducks.sprites.creatures.Creature;
import kelvinkellner.ducks.sprites.creatures.Enemy;
import kelvinkellner.ducks.sprites.creatures.Player;
import kelvinkellner.ducks.sprites.creatures.enemies.Crocodile;
import kelvinkellner.ducks.sprites.creatures.enemies.Eagle;
import kelvinkellner.ducks.sprites.creatures.enemies.Fox;
import kelvinkellner.ducks.sprites.creatures.enemies.SnappingTurtle;
import kelvinkellner.ducks.sprites.items.DuckNip;
import kelvinkellner.ducks.sprites.items.Grain;
import kelvinkellner.ducks.sprites.items.HealingBerry;
import kelvinkellner.ducks.sprites.items.Item;
import kelvinkellner.ducks.sprites.items.Omega3FishOil;

public class Stage {
	
	private static final int TILE_SIZE = 64;
	
	public int stageWidth = 0;
	public int stageHeight = 0;
	
	public ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	
	public Player player;
	public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public ArrayList<Creature> creatures = new ArrayList<Creature>();
	public ArrayList<Item> items = new ArrayList<Item>();
	public ArrayList<Block> blocks = new ArrayList<Block>();
	
	Stage(String file) throws IOException
	{
		parse(file);
	}
	
	private void parse(String file) throws IOException
	{
		FileReader fileIn = new FileReader(file);
		BufferedReader read = new BufferedReader(fileIn);
		
		boolean end = false;
		int line = 0;
		
		while(!end)
		{
			String text = read.readLine();
			if(text==null)
				end = true;
			else
			{
				stageHeight = line*TILE_SIZE;
				if(text.length()*TILE_SIZE>stageWidth)
					stageWidth = text.length()*TILE_SIZE;
				
				for(int character=0;character<text.length();character++)
				{
					char letter = text.charAt(character);
					
					int x = character*TILE_SIZE;
					int y = line*TILE_SIZE;
					
					// Player
					if(letter=='P')
					{
						player = new Player(x, y, 0);
						creatures.add(player);
						sprites.add(player);
					}
					
					// Enemies
					else if(letter=='F')
					{
						enemies.add(new Fox(x, y));
						creatures.add(enemies.get(enemies.size()-1));
						sprites.add(enemies.get(enemies.size()-1));
					}
					else if(letter=='E')
					{
						enemies.add(new Eagle(x, y));
						creatures.add(enemies.get(enemies.size()-1));
						sprites.add(enemies.get(enemies.size()-1));
					}
					else if(letter=='C')
					{
						enemies.add(new Crocodile(x, y));
						creatures.add(enemies.get(enemies.size()-1));
						sprites.add(enemies.get(enemies.size()-1));
					}
					else if(letter=='T')
					{
						enemies.add(new SnappingTurtle(x, y));
						creatures.add(enemies.get(enemies.size()-1));
						sprites.add(enemies.get(enemies.size()-1));
					}
					
					// Items
					else if(letter=='B')
					{
						items.add(new Grain(x, y, 5));
						sprites.add(items.get(items.size()-1));
					}
					else if(letter=='O')
					{
						items.add(new Omega3FishOil(x, y));
						sprites.add(items.get(items.size()-1));
					}
					else if(letter=='N')
					{
						items.add(new DuckNip(x, y));
						sprites.add(items.get(items.size()-1));
					}
					else if(letter=='H')
					{
						items.add(new HealingBerry(x, y));
						sprites.add(items.get(items.size()-1));
					}
					
					// Blocks
					else if(letter=='G')
					{
						blocks.add(new Block(x, y, "Grass"));
						sprites.add(blocks.get(blocks.size()-1));
					}
					else if(letter=='D')
					{
						blocks.add(new Block(x, y, "Dirt"));
						sprites.add(blocks.get(blocks.size()-1));
					}
				}
			}
			line++;
		}
		
		System.out.println("Stage successfully loaded.");
		System.out.println(sprites.size() + " sprites in total:");
		System.out.println("The player");
		System.out.println(enemies.size() + " enemies");
		System.out.println(items.size() + " items");
		System.out.println(blocks.size() + " blocks");
		
		read.close();
	}
	
}
