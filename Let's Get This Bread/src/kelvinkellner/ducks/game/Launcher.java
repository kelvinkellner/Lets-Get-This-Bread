package kelvinkellner.ducks.game;

public class Launcher {
	
	public static void main(String[] args)
	{
		Game game = new Game("Let's Get This Bread", 800, 600);
		game.start();
	}
}
