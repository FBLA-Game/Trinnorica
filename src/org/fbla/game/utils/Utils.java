package org.fbla.game.utils;

import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.Area;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.fbla.game.Bridge;
import org.fbla.game.boards.GameBoard;
import org.fbla.game.sprites.Player;
import org.fbla.game.spriteutils.Sprite;

import res.Audio;

public class Utils {

	private static List<Integer> ids = new ArrayList<>();
	private static File config;
	private static File score;
	private static int player_level;
	public static int player_score;
	private static String root;
	private static File rootFile;
	public static boolean firsttime = false;
	public static HashMap<Integer, Background> backgrounds = new HashMap<>();
	public static HashMap<Integer, int[]> spawns = new HashMap<>();
	public static String[] hexvalues = new String[] {"a","b","c","d","e","f","1","2","3","4","5","6","7","8","9","0"};

	public static void init() {
		root = "C://KANSAS_WELLSVILLE_HIGHSCHOOL/master/";
		config = new File(root + "/config.txt");
		score = new File(root + "/score.txt");
		
		
		
		backgrounds.put(1, Background.SKY);
		backgrounds.put(2, Background.SKY);
		backgrounds.put(3, Background.SKY);
		backgrounds.put(4, Background.SKY);
		backgrounds.put(5, Background.ENTERING_CAVE);
		backgrounds.put(6, Background.CAVE);
		backgrounds.put(7, Background.CAVE);
		backgrounds.put(8, Background.CAVE);
		backgrounds.put(9, Background.CAVE);
		backgrounds.put(10, Background.CAVE);
		backgrounds.put(11, Background.ENTERING_CAVE);
		
		spawns.put(1, new int []{21,470});
		spawns.put(2, new int []{21,470});
		spawns.put(3, new int []{21,410});
		spawns.put(4, new int []{21,470});
		spawns.put(5, new int []{1,50});
		spawns.put(6, new int []{932,170});
		spawns.put(7, new int []{15,440});
		spawns.put(8, new int []{21,470});
		spawns.put(9, new int []{21,470});
		spawns.put(10, new int []{15,440});
		spawns.put(11, new int []{15,470});
		
		

		int length = (int) config.length();
		byte[] bytes = new byte[length];
		FileInputStream in;
		try {
			in = new FileInputStream(config);
			try {
				in.read(bytes);
			} catch (IOException e) {

				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		}

		

		String contents = new String(bytes);
		String[] data = contents.split("-");
		for (String info : data) {
			if (info.contains("level"))
				player_level = Integer.parseInt(info.split(":")[1]);
			
			
		}

		
		
		int length1 = (int) score.length();
		byte[] bytes1 = new byte[length1];
		FileInputStream in1;
		try {
			in1 = new FileInputStream(score);
			try {
				in1.read(bytes1);
			} catch (IOException e) {

				e.printStackTrace();
			} finally {
				try {
					in1.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		}

		

		String contents1 = new String(bytes1);
		player_score = Integer.parseInt(contents1.split(":")[1]);
		Bridge.getPlayer().setScore(player_score);
		Bridge.getPlayer().level = (player_level);
		
		broadcastMessage("Score: " + player_score);
		broadcastMessage("Level: " + player_level);
			
			

	}


	public static void broadcastMessage(String message) {
		System.out.println(message);
		System.out.println();
	}
	
	public static int[] getSpawnPoint(int level){
		return spawns.get(level);
	}

	public static void displayMessage(int id, String message, int x, int y, int time, String color, int size,Font font) {
		((GameBoard)Bridge.getGame().getBoard()).setFont(font);
		((GameBoard)Bridge.getGame().getBoard()).getGraphics().setFont(font);
		((GameBoard)Bridge.getGame().getBoard()).messages.put(id,message + ":"+ ((x - ((GameBoard)Bridge.getGame().getBoard()).getFontMetrics(font).stringWidth(message)/ 2)) + ":" + y + ":" + time + ":" + color + ":" + size);
	}

	public static void addPlayerMessage(int id, String message, int xmod, int ymod, int time, String color, int size) {
		((GameBoard)Bridge.getGame().getBoard()).messages_player.put(id,
				message + ":" + xmod + ":" + ymod + ":" + time + ":" + color + ":" + size);
	}

	public static int getNextID() {
		try {
			int i = ids.size();
			ids.add(i);
			return i;
		} catch (IndexOutOfBoundsException ex) {
			ids.add(0);
			return 0;
		}
	}
	
	public static long gcm(long a, long b) {
	    return b == 0 ? a : gcm(b, a % b);
	}

	public static String asFraction(int a, int b) {
	    long gcm = gcm(a, b);
	    return (a / gcm) + "/" + (b / gcm);
	}

	
	

	public static void savePlayerInfo(Player player) {
		
//		try {
//			PrintWriter writer = new PrintWriter(config);
//			writer.write("level:" + player.level);
//			writer.close();
//		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
//		}
//		
//		try {
//			PrintWriter writer = new PrintWriter(score);
//			writer.write("score:" + player.getScore());
//			writer.close();
//		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
//		}
		
		
		try {
			player_level = player.level;
			player_score = player.getScore();
		} catch (NullPointerException ex) {
		}
		
		try {
			BufferedWriter writer = new BufferedWriter(new PrintWriter(config));
			writer.write("");
			writer.write("level:" + player.getLevel());
			writer.close();
		} catch(Exception ex){
			ex.printStackTrace();
		}
		try {
			BufferedWriter writer = new BufferedWriter(new PrintWriter(score));
			writer.write("");
			writer.write("score:" + player.getScore());
			writer.close();
		} catch(Exception ex){
			ex.printStackTrace();
		}

//		BufferedWriter out = null;
//		try {
//			out = new BufferedWriter(new FileWriter(config, true));
//			out.write("level:" + player_level);
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		} finally {
//			try {
//				out.close();
//			} catch (IOException e) {
//
//				e.printStackTrace();
//			}
//		}
//		
//		BufferedWriter out1 = null;
//		try {
//			out1 = new BufferedWriter(new FileWriter(score, true));
//			out1.append("score:" + player.getScore());
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		} finally {
//			try {
//				out1.close();
//			} catch (IOException e) {
//
//				e.printStackTrace();
//			}
//		}

		
		

	}

	public static void initPlayer(Player player) {
		player.level = player_level;
		player.setScore(player_score);

	}

	public static void runInstall() {
		
		broadcastMessage("installing...");
		
		firsttime = true;
		root = "C://KANSAS_WELLSVILLE_HIGHSCHOOL/master/";
		rootFile = new File(root);
		if (!rootFile.exists())
			rootFile.mkdirs();
		config = new File(root + "/config.txt");
		score =  new File(root + "/score.txt");
		

		try {
			config.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (!config.getParentFile().mkdirs()) {
			
		}
		
		try {
			score.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (!score.getParentFile().mkdirs()) {
			
		}

		try {
			BufferedWriter writer = new BufferedWriter(new PrintWriter(config));
			writer.write("");
			writer.write("level:1");
			writer.close();
		} catch(Exception ex){
			ex.printStackTrace();
		}
		
		try {
			BufferedWriter writer = new BufferedWriter(new PrintWriter(score));
			writer.write("");
			writer.write("score:0");
			writer.close();
		} catch(Exception ex){
			ex.printStackTrace();
		}
		
		
		

		init();

	}

	public static void checkPlayerInfo(Player player) {
		player.level = player_level;
	}



	

	
	
	public static boolean intersects(Shape shape1, Shape shape2){
		Area a1 = new Area(shape1);
		Area a2 = new Area(shape2);
		a1.intersect(a2);
		return !a1.isEmpty();
	}
	
	public static Background getBackground(int level){
		return backgrounds.get(level);
	}
	
	public static void setPlayerLevel(int level){
		player_level = level;
		try{
			if(Bridge.getGame().getBoard().getType().equals(BoardType.GAME_BOARD)){
				Bridge.getGame().loadLevel();
			}
		} catch(NullPointerException ex){}
		
	}

	public static int getPlayerLevel() {
		return player_level;
	}


	public static void startRandomColorTimer(final Sprite sprite) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			
			@Override
			public void run(){
				sprite.loadImage(Images.createColorImage(sprite, Utils.generateRandomHEXColor()));
			}
		}, 100L, 100L);
	}
	
	
	
	public static String generateRandomHEXColor(){
		String color = "#";
		
		for(int i=0;i!=6;i++){
			color = color + hexvalues[new Random().nextInt(hexvalues.length)];
		}
		return color;
		
	}


	public static LevelType getLevelType(int level) {
		switch(level){
		case 1:
			return LevelType.GRASS;
		case 2:
			return LevelType.GRASS;
		case 3:
			return LevelType.GRASS;
		case 4:
			return LevelType.GRASS;
		case 5:
			return LevelType.GRASS;
		case 6:
			return LevelType.CAVE;
		case 7:
			return LevelType.CAVE;
		case 8:
			return LevelType.CAVE;
		case 9:
			return LevelType.CAVE;
		case 10:
			return LevelType.BOSS;
		case 11:
			return LevelType.CAVE;
		default:
			return null;
			
		}
	}


	public static void drawOutlineString() {
		// TODO Auto-generated method stub
		
	}
	




}
