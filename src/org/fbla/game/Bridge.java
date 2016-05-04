package org.fbla.game;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
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
import org.fbla.game.utils.Sound;
import org.fbla.game.utils.Utils;

import res.Audio;
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
		game.setIconImage(Texture.loadTexture("logo.png"));
		game.init();
		
		File file = new File("C://KANSAS_WELLSVILLE_HIGHSCHOOL/master/");
		if (!file.exists()) {
			Utils.runInstall();
			return;
		}
		Utils.init();
		
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		Audio.playBackground(Sound.BACKGROUND_MENU);
		
		font = new Font("Helvetica", Font.BOLD, 20);
		
		
	   
        
		
		

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
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setResizable(false);
		game.setLocationRelativeTo(null);
		
		
		
		player.level = 1;
		Utils.setPlayerLevel(1);
		
		((GameBoard) game.board).drawLoading(game.getGraphics());
		
		
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
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		player.level = 1;
		Utils.setPlayerLevel(1);
		((GameBoard) game.board).init();
	
	
	}
	
	public static void openMenu() {
		game.clear();
		game = null;
		game = new Game();
		game.setPreferredSize(new Dimension(960, 640));
		game.setIconImage(Texture.loadTexture("logo.png"));
		game.setBoard(new MenuBoard());
		game.pack();
		game.setVisible(true);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		game.setLocationRelativeTo(null);
		
		Audio.playBackground(Sound.BACKGROUND_MENU);
	}
	
	public static int getGameBoardSize(int value){
		return gameBoardSize.get(value);
	}

	public static void credits() {
		Audio.playBackground(Sound.BACKGROUND_CREDITS);
		game.clear();
		game = null;
		game = new Game();
		game.setIconImage(Texture.loadTexture("logo.png"));
		game.setPreferredSize(new Dimension(960, 640));
		game.setBoard(new CreditsBoard());
		game.pack();
		game.setVisible(true);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		game.setLocationRelativeTo(null);
	}
	
	public static Point rotatePoint(Point pt, Point center, double angleDeg){
	    double angleRad = (angleDeg/180)*Math.PI;
	    double cosAngle = Math.cos(angleRad);
	    double sinAngle = Math.sin(angleRad);
	    double dx = (pt.x-center.x);
	    double dy = (pt.y-center.y);

	    pt.x = center.x + (int) (dx*cosAngle-dy*sinAngle);
	    pt.y = center.y + (int) (dx*sinAngle+dy*cosAngle);
	    return pt;
	}

}
