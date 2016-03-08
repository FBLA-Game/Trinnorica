package org.fbla.game;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;

import org.fbla.game.boards.CreditsBoard;
import org.fbla.game.boards.Game;
import org.fbla.game.boards.GameBoard;
import org.fbla.game.boards.LevelDebugBoard;
import org.fbla.game.boards.MenuBoard;
import org.fbla.game.sprites.Player;
import org.fbla.game.utils.Utils;

import res.Texture;

public class Bridge {

	private static Game game;
	public static Player player;
	public static Font font;
	


	
	
	//Cameron
	//First value is X or Y. 0 = x : 1 = y
	public static HashMap<Integer, Integer> gameBoardSize = new HashMap<>();
	public static boolean DEBUG = false;
	
	public static void main(String[] args) {
		
		setPlayer(new Player(0,0));
		gameBoardSize.put(0, 960);
		gameBoardSize.put(1, 540);
		game = new Game();
		game.init();
		
		File file = new File("C://KANSAS_WELLSVILLE_HIGHSCHOOL/master/");
		if (!file.exists()) {
			Utils.runInstall();
			return;
		}
		Utils.init();
		
		
		
		
		
		
		
		
		
        
		
		

	}
	
	public static void setPlayer(Player p){
		player = p;
	}

	public static Game getGame() {
		return game;
	}

	// Cameron
	// Ends the game from Runtime.
	public static void quit() {
		System.exit(0);
	}

	public static void openLevelDebug(int level) {
		
		
		game.clear();
		
		game = null;
		game = new Game();
		game.setBoard(new LevelDebugBoard(level));
		game.setTitle("Debug: " + level);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);
		game.setIconImage(Texture.loadTexture("logo.png"));
		game.setPreferredSize(new Dimension(960, 540));
		game.pack();
		game.setLocationRelativeTo(null);
		
		
		
		
	}

	public static void start() {
		
		
		Utils.broadcastMessage(player.getPlayerModel());
		
		
		game.clear();
		
		game = null;
		game = new Game();
		game.setBoard(new GameBoard());
		game.setTitle("Trinnorica");
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);
		game.setIconImage(Texture.loadTexture("logo.png"));
		game.setPreferredSize(new Dimension(gameBoardSize.get(0)+6, gameBoardSize.get(1)+28));
		game.pack();
		
		game.setResizable(false);
		game.setLocationRelativeTo(null);
		
		player.level = 1;
		Utils.setPlayerLevel(1);
		((GameBoard) game.board).init();
		
		Utils.broadcastMessage(player.getPlayerModel());
	
	
	}

	public static Player getPlayer() {
		return player;
	}

	public static void setPlayerLocation(int x, int y) {
		player.x = x;
		player.y = y;
	}
	
	public static void restart(){
		
		

		player = new Player(0, 0);
		
		game.clear();
		
		game = null;
		game = new Game();
		game.setBoard(new GameBoard());
		game.setTitle("Upsilon");
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);
		game.setIconImage(Texture.loadTexture("logo.png"));
		game.setPreferredSize(new Dimension(965, 565));
		game.pack();
		
		game.setResizable(false);
		game.setLocationRelativeTo(null);
		
		player.level = 1;
		Utils.setPlayerLevel(1);
		((GameBoard) game.board).init();
	
	
	}
	
	public static void openMenu() {
		game.clear();
		game = null;
		game = new Game();
		game.setPreferredSize(new Dimension(960, 640));
		game.setBoard(new MenuBoard());
		game.pack();
		game.setVisible(true);
		
		
		game.setLocationRelativeTo(null);
	}
	
	public static int getGameBoardSize(int value){
		return gameBoardSize.get(value);
	}

	public static void credits() {
		game.clear();
		game = null;
		game = new Game();
		game.setPreferredSize(new Dimension(960, 640));
		game.setBoard(new CreditsBoard());
		game.pack();
		game.setVisible(true);
		
		
		game.setLocationRelativeTo(null);
	}

}
