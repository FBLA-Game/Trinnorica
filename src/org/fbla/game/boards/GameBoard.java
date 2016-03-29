package org.fbla.game.boards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.Timer;

import org.fbla.game.Bridge;
import org.fbla.game.sprites.Boss;
import org.fbla.game.sprites.Competitor;
import org.fbla.game.sprites.Door;
import org.fbla.game.sprites.FallingFloor;
import org.fbla.game.sprites.Floor;
import org.fbla.game.sprites.Gate;
import org.fbla.game.sprites.Gold;
import org.fbla.game.sprites.Ladder;
import org.fbla.game.sprites.Player;
import org.fbla.game.sprites.Switch;
import org.fbla.game.sprites.Wall;
import org.fbla.game.sprites.tools.Arrow;
import org.fbla.game.sprites.tools.Bow;
import org.fbla.game.sprites.tools.Key;
import org.fbla.game.spriteutils.Clickable;
import org.fbla.game.spriteutils.Entity;
import org.fbla.game.spriteutils.FloorBottom;
import org.fbla.game.spriteutils.GateType;
import org.fbla.game.spriteutils.Keyable;
import org.fbla.game.spriteutils.Moveable;
import org.fbla.game.spriteutils.Rotation;
import org.fbla.game.spriteutils.Sprite;
import org.fbla.game.spriteutils.SpriteType;
import org.fbla.game.spriteutils.State;
import org.fbla.game.spriteutils.Tool;
import org.fbla.game.utils.Background;
import org.fbla.game.utils.Board;
import org.fbla.game.utils.BoardType;
import org.fbla.game.utils.Button;
import org.fbla.game.utils.ButtonMethod;
import org.fbla.game.utils.Direction;
import org.fbla.game.utils.InteractionMethod;
import org.fbla.game.utils.Utils;

import res.Texture;

public class GameBoard extends Board implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Timer timer;
	public boolean debug;
	public boolean won;
	public boolean loaded = false;
	public boolean ingame;
	public boolean inv = false;
	public String gameStatus;
	public final int B_WIDTH = Bridge.getGameBoardSize(0);
	public final int B_HEIGHT = Bridge.getGameBoardSize(1);
	public final int DELAY = 10;
	private boolean hitboxes = false;
	public int l = 0;
	public int mx = 0;
	public int my = 0;
	public Sprite sprite = null;
	public boolean in = false;
	private boolean fs = false;
	public double extra = 1;
	private int e = 0;
	private int d=15;
	private Board board;
	boolean mouse = true;
	
	private GraphicsDevice vc;
	

	public boolean paused = false;
	public Map<Integer, String> messages = new HashMap<>();
	public Map<Integer, String> messages_player = new HashMap<>();
	public ArrayList<Sprite> sprites = new ArrayList<>();
	public List<Clickable> clickables = new ArrayList<>();
	public List<Moveable> moveables = new ArrayList<>();

	public List<Moveable> moveables_temp = new ArrayList<>();
	public List<Sprite> removedSprites = new ArrayList<>();
	public List<Sprite> sprite_temp = new ArrayList<>();
	public List<String> strings_temp = new ArrayList<>();
	public List<String> strings_temp_player = new ArrayList<>();
	public List<Sprite> tools = new ArrayList<>();
	
	ArrayList<Sprite> level1 = new ArrayList<>();
	ArrayList<Sprite> level2 = new ArrayList<>();
	ArrayList<Sprite> level3 = new ArrayList<>();
	ArrayList<Sprite> level4 = new ArrayList<>();
	ArrayList<Sprite> level5 = new ArrayList<>();
	ArrayList<Sprite> level6 = new ArrayList<>();
	ArrayList<Sprite> level7 = new ArrayList<>();
	ArrayList<Sprite> level8 = new ArrayList<>();
	ArrayList<Sprite> level9 = new ArrayList<>();
	ArrayList<Sprite> level10 = new ArrayList<>();
	private HashMap<Integer, ArrayList<Sprite>> levels = new HashMap<>();

	public static int maxlives = 5;

	public GameBoard() {
		setType(BoardType.GAME_BOARD);
		board = this;

	}
	
	public void startDebug(int i){
		ingame = true;
		gameStatus = "ingame";
		loadLevels(true, i);
	}

	public void init() {

		ingame = true;

		gameStatus = "ingame";

		Utils.savePlayerInfo(Bridge.getPlayer());

		addKeyListener(new TAdapter());
		addMouseMotionListener(new MMListener());
		addMouseListener(new MListener());
		setFocusable(true);
		setBackground(Color.RED);

		loadLevels(false, 0);

		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

		timer = new Timer(DELAY, this);
		timer.start();

		won = false;

		update();
		loadLevel();
		
		vc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

	}
	
	@Override
	public void disable(){
		timer.stop();
		timer = null;
		
		debug = false;
		won = false;
		loaded = false;
		ingame = false;
		gameStatus = "";
		l = 0;
		messages.clear();
		messages_player.clear();
		sprites.clear();
		clickables.clear();
		moveables.clear();
		moveables_temp.clear();
		removedSprites.clear();
		strings_temp.clear();
		strings_temp_player.clear();
		tools.clear();
		level1.clear();
		level2.clear();
		level3.clear();
		level4.clear();
		level5.clear();
		level6.clear();
		level7.clear();
		level8.clear();
		level9.clear();
		level10.clear();
		levels.clear();
		
		
	}
	
	

	public void loadLevels(boolean debug, int i) {
		levels.clear();
		level1.clear();
		level2.clear();
		level3.clear();
		level4.clear();
		level5.clear();
		level6.clear();	
		level7.clear();
		level8.clear();
		level9.clear();
		level10.clear();
		
		if(debug){
			if(i==1) loadLevel1(true);
			if(i==2) loadLevel2(true);
			if(i==3) loadLevel3(true);
			if(i==4) loadLevel4(true);
			if(i==5) loadLevel5(true);
			if(i==6) loadLevel6(true);
			if(i==7) loadLevel7(true);
			if(i==8) loadLevel8(true);
			if(i==9) loadLevel9(true);
			if(i==10) loadLevel10(true);
			
			return;
			
		}
		
		loadLevel1(false);
		loadLevel2(false);
		loadLevel3(false);
		loadLevel4(false);
		loadLevel5(false);
		loadLevel6(false);
		loadLevel7(false);
		loadLevel8(false);
		loadLevel9(false);
		loadLevel10(false);

		
		

		

		

		
	
		
		
		
		
		
		

	}

	//Learn about ladders
	private void loadLevel1(boolean debug) {
		
		
		
		
		
		for(int x=3;x!=20;x++){
			level1.add(new Floor(x*30,6*30,Floor.GRASS,FloorBottom.GRASS));
		}
		
		for(int x=2;x!=31;x++){
			level1.add(new Floor(x*30, 12*30,Floor.GRASS,FloorBottom.GRASS));
		}
		
		for(int x=0;x!=32;x++){
			level1.add(new Floor(x*30, 17*30, Floor.GRASS,FloorBottom.GRASS));
		}
		
		for(int x=0;x!=10;x++){
			level1.add(new Gold((x*30), 480));
		}
		
		for(int y=11;y!=17;y++){
			level1.add(new Ladder(31*30,y*30));
		
		}
		
		
		
		
		for(int y=5;y!=12;y++){
			level1.add(new Ladder(2*30, (y*30)));
		}
	
		
		
		
//		level1.add(new Door(18 * 15, (7* 15)+10, 1));
//		level1.add(new Bow(9 * 30, 20 * 15));
//		level1.add(new Key(15 * 30, 20 * 15, 1));
		level1.add(new Gate(19*30, (5*30)-1, GateType.FLAG));
		if(!debug) level1.add(Bridge.getPlayer());

		levels.put(1, level1);
	}
	
	
	//Expand on ladders
	private void loadLevel2(boolean debug) {
		
		for(int i=0;i!=32;i++){
			if(i>=20)
				level2.add(new Floor(i*30,8*30,Floor.GRASS,FloorBottom.DIRT));
			level2.add(new Floor(i*30,17*30,Floor.GRASS));
			if(i<=16 && i>=7 && (i & 1) == 0)
				level2.add(new Ladder(17*30,i*30));
		}
		
		level2.add(new Gate(31*30,7*30,Gate.FLAG));
		if(!debug) level2.add(Bridge.getPlayer());

		levels.put(2, level2);
	}
	
	//Learn about falling floors & 1 block gaps & sprinting
	private void loadLevel3(boolean debug) {
		
		
		
		for(int x=0;x!=32;x++){
			if(x>=5 && x<=25){
				if((x & 1) == 0) level3.add(new FallingFloor(x*30, 15*30,Floor.GRASS));
			}
			else level3.add(new Floor(x*30, 15*30,Floor.GRASS));
		
		}
		
		level3.add(new Gate((30 * 30)+10, (14* 30)-2, GateType.FLAG));
		if(!debug) level3.add(Bridge.getPlayer());

		levels.put(3, level3);
	}
	
	//Expand on Falling Floors and Ladders
	private void loadLevel4(boolean debug) {
		
		for(int x=0;x!=32;x++){
			if(x>2&&x<31){
				if(x>3&&x<15)
					level4.add(new Floor((x*30),(4*30),Floor.BLUE_STONE));
				level4.add(new Floor((x*30),(8*30),Floor.BLUE_STONE));
			}

			if(x>=8 && x<=17)
				level4.add(new FallingFloor((x*30), (13*30),Floor.GRASS,FloorBottom.DIRT));
			else
			level4.add(new Floor((x*30), 17*30,Floor.GRASS));
			
		
				
			
		}
		
		
		for(int y = 3;y!=17;y++){
			if(y<8)
				level4.add(new Ladder((3*30),(y*30)));
			if(y>6){
				level4.add(new Ladder((31*30),(y*30)));
				if(y>11){
					level4.add(new Ladder((7*30),(y*30)));
					level4.add(new Ladder((18*30),(y*30)));
				}
			}
		}
		
		
		
		
		
		
		
		
		level4.add(new Gate((14 * 30), (3 * 30),GateType.FLAG));
		if(!debug) level4.add(Bridge.getPlayer());

		levels.put(4, level4);
		
	}
	
	//Learn about switches
	private void loadLevel5(boolean debug) {
		
		for(int x=0;x!=32;x++){
			if(x<=20){
				if(x>6) level5.add(new Floor(x*30,(int)(3*30+x*(x*0.5)),Floor.GRASS,FloorBottom.GRASS));
				else level5.add(new Floor(x*30,(int) (3*30+x*(x*0.5)),Floor.GRASS,FloorBottom.STONE));
			}
			level5.add(new Floor(x*30,16*30,Floor.GRASS));
			
		}
		level5.add(new Switch(63*15, 15*30, new Wall(7*30,16*30,-(12*30),State.VERTICAL), level5, Rotation.LEFT, InteractionMethod.DISAPPEAR));
		
		
		level5.add(new Gate(0*30, 15*30,GateType.FLAG));
		if(!debug) level5.add(Bridge.getPlayer());

		levels.put(5, level5);
	
	}
	
	//Expand on switches and learn Projectiles & Bows
	private void loadLevel6(boolean debug) {
		
		Switch s = new Switch(35*30, 35*30,new Sprite[]{ 
				new Wall(3*30-5,0*30,7*30, State.VERTICAL)
		}, level6, Rotation.LEFT, InteractionMethod.DISAPPEAR);
	
		if(!debug) s.interact();
		
		Switch s2 = new Switch(35*30, 35*30, new Sprite[]{
				new Wall(3*30,7*30,60,State.HORIZONTAL)
				
		},level6, Rotation.LEFT, InteractionMethod.DISAPPEAR);
		
			
		
											
		level6.add(new Switch(0, 6*30-7,new Sprite[] {
				s,
				s2
		},level6,Rotation.RIGHT,InteractionMethod.TRIGGER));
		
		for(int x=0;x!=32;x++){
			if(!(x==3)&&!(x==4))
				level6.add(new Floor(x*30,7*30,Floor.BLUE_STONE));
			level6.add(new Floor(x*30,16*30,Floor.GRAY_STONE));
		}
		
		
		level6.add(new Gate(31*30, (15* 30),GateType.FLAG));
		
		level6.add(new Bow(16*30, 5*30));

		if(!debug) level6.add(Bridge.getPlayer());

		levels.put(6, level6);
		
	}
	
	//Learn keys & tools.
	private void loadLevel7(boolean debug) {
		
			level7.add(new Wall(0, 10*30,16*30,State.HORIZONTAL));
		
		
		for(int x=25;x!=32;x++){
			level7.add(new Floor(x*30,-30,Floor.GRAY_STONE,FloorBottom.BLUE_STONE));
		}
		
		
		for(int x=17;x!=32;x++){
			level7.add(new Floor(x*30, 5*30,Floor.GRAY_STONE));
		}
		
		
		
		
		for(int x=0;x!=32;x++){
			level7.add(new Floor(x*30, 16*30, Floor.GRAY_STONE, FloorBottom.STONE));
		}
		
		for(int y=4;y!=16;y++){
			level7.add(new Ladder(16*30, y*30));
		}
		
		for(int x=0;x!=16;x++){
			if(x==0)
				level7.add(new Key(x*30, 19*15, 1));
			else level7.add(new Gold(x*30+11, 10*30-11));
		}
		
		
		
		level7.add(new Door(25*30, (4*15)+3, 1));
		
		level7.add(new Gate(31*30, 8*15, GateType.FLAG));
		

		

		if(!debug) level7.add(Bridge.getPlayer());
		
		levels.put(7, level7);
		
	}
	
	
	//Expand keys (multiple) learn inventory.
	private void loadLevel8(boolean debug){
		
		level8.add(new Wall(16*30, 8*30, 9*30, State.VERTICAL, "111111"));
		level8.add(new Wall(20*30, 5*30, 9*30, State.VERTICAL, "111111"));
		
		for(int i=0;i!=32;i++){
			if(i<19&&i!=7){
				level8.add(new Floor(i*30,8*30,Floor.GRAY_STONE));
			}
			
			if(i>=7 && i<17){
				level8.add(new Ladder(7*30,i*30));
				level8.add(new Ladder(19*30,i*30));
			}
			
			level8.add(new Floor(i*30,17*30,Floor.GRAY_STONE));
		}
		
		level8.add(new Door(16*30, 5*30+3, 1));
		level8.add(new Door(22*30, 14*30+3, 2));
		level8.add(new Key(0*30, 7*30+3, 2));
		level8.add(new Key(1*30, 7*30+3, 1));
		
		level8.add(new Gate(31*30,16*30));
		
		

		if(!debug) level8.add(Bridge.getPlayer());
		
		levels.put(8, level8);
	}
	
	
	private void loadLevel9(boolean debug){
		
		level9.add(new Wall(0, 10*30, 15*30,State.HORIZONTAL));
		level9.add(new Wall(17*30, 10*30, 15*30,State.HORIZONTAL));
		
		level9.add(new Wall(15*30,10*30,8*30,State.VERTICAL));
		level9.add(new Wall(17*30,10*30,8*30,State.VERTICAL));
		
		
		for(int y=8;y!=19;y++){
			level9.add(new Gold(16*30,y*30));
		}
		
		level9.add(new Gate(15*30-12, 37*15, 0));
		level9.add(new Gate(16*30-12, 37*15, 0));
		level9.add(new Gate(17*30-12, 37*15, 0));
		
		if(!debug) level9.add(Bridge.getPlayer());
		
		levels.put(9, level9);
		
	}
	
	private void loadLevel10(boolean debug){
		
		
		for(int x=25;x!=32;x++){
			level10.add(new Floor(x*30,-30,Floor.GRAY_STONE,FloorBottom.BLUE_STONE));
		}
		
		
		for(int x=17;x!=32;x++){
			level10.add(new Floor(x*30, 5*30,Floor.GRAY_STONE));
		}
		
		for(int x=0;x!=16;x++){
			level10.add(new FallingFloor(x*30, 10*30,Floor.RED_STONE));
		}
		
		
		for(int x=0;x!=32;x++){
			level10.add(new Floor(x*30, 16*30, Floor.GRAY_STONE, FloorBottom.STONE));
		}
		
		for(int y=4;y!=16;y++){
			level10.add(new Ladder(16*30, y*30));
		}
		
		for(int x=0;x!=16;x++){
			if(x==0)
				level10.add(new Key(x*30, 19*15, 1));
			else level10.add(new Gold(x*30+11, 10*30-11));
		}
		
		level10.add(new Door(25*30, (4*15)+3, 1));
		
		level10.add(new Gate(31*30, 8*15, GateType.FLAG));
		

		

		if(!debug) level10.add(Bridge.getPlayer());
		
		levels.put(10, level10);
		
	}
	

	public ArrayList<Sprite> getLevel(int level) {
		

		try{
			return levels.get(level);
		} catch(NullPointerException ex){
			return level1;
		}
		
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (inv) {
			drawInventory(g);
			return;
		}

		if (won) {
			drawWin(g);
		
			
			
			return;
		}

		if (ingame){
				drawObjects(g);
			} else {
				if(loaded)
				drawGameOver(g);
		}

		Toolkit.getDefaultToolkit().sync();

	}

	public void loadLevel() {
		levels.clear();
		level1.clear();
		level2.clear();
		level3.clear();
		level4.clear();
		level5.clear();
		level6.clear();
		level7.clear();
		level8.clear();
		level9.clear();
		level10.clear();
		
		Bridge.player.inventory.clear();
		if(Bridge.player.hasTool()) Bridge.player.removeTool(Bridge.player.getTool());
		
		Bridge.player.health = Bridge.player.maxhealth;
		
		messages.clear();
		messages_player.clear();
		strings_temp.clear();
		strings_temp_player.clear();
		
		sprite_temp.clear();
		moveables_temp.clear();
		sprites.clear();
		moveables.clear();
		tools.clear();
		clickables.clear();
		
		loadLevels(false, 0);
		
		
		
		ingame = false;
		loaded = false;
		if (Utils.getPlayerLevel() > levels.size()) {
			won = true;
			return;
		}
		
		try {
			for (Sprite sprite : levels.get(Utils.getPlayerLevel())) {
				sprites.add(sprite);

				if (sprite instanceof Moveable) {
					sprites.remove(sprite);
					if (!(sprite instanceof Player))
						moveables.add((Moveable) sprite);
				}

				if (sprite instanceof Player) {
					sprites.remove(sprite);

				}
				if (sprite instanceof Tool) {
					tools.add(sprite);
				}

			}
		} catch (NullPointerException ex) {
			for (Sprite sprite : getLevel(1)) {
				sprites.add(sprite);

				if (sprite instanceof Moveable) {
					sprites.remove(sprite);
					if (!(sprite instanceof Player))
						moveables.add((Moveable) sprite);
				}

				if (sprite instanceof Player) {
					sprites.remove(sprite);

				}
				if (sprite instanceof Tool) {
					sprites.remove(sprite);
					tools.add(sprite);
				}

			}
		}
		
		Bridge.player.x = Utils.getSpawnPoint(Bridge.getPlayer().getLevel())[0];
		Bridge.player.y = Utils.getSpawnPoint(Bridge.getPlayer().getLevel())[1];

		ingame = true;

		loaded = true;
		
		if(Utils.getPlayerLevel() == 1 && Utils.firsttime){
			loadHelp();
		}
		
		if(Utils.getPlayerLevel() == 3){
			Utils.displayMessage(55, "You can run across one block gaps! (ctrl)", B_WIDTH/2, 100, -1, "#FFFFFF", 30, new Font(Font.SANS_SERIF,Font.PLAIN,30));
		}
		if(Utils.getPlayerLevel() == 8){
			Utils.displayMessage(55, "Don't touch these guys! They will kill you instantly. /nTry shooting them instead?", (int) (B_WIDTH*0.6), 100, -1, "#FFFFFF", 30, new Font(Font.SANS_SERIF,Font.PLAIN,30));
		}

	}

	public void loadHelp() {

		paused = true;
		Utils.displayMessage(13, "(Press ESC to play)", B_WIDTH / 2, 100, -1, "#FFFFFF", 20,getFont());


		Utils.displayMessage(4, "<--- This is your HUD --->", B_WIDTH / 2, 15, -1, "#FFFFFF", 10, getFont());

		Utils.displayMessage(5, "Controls", B_WIDTH / 2, (B_HEIGHT / 2) - 30, -1, "#FFFFFF", 10, getFont());
		Utils.displayMessage(6, "Left-> \"A\" or the left arrow", B_WIDTH / 2, (B_HEIGHT / 2) - 20, -1, "#FFFFFF", 10, getFont());
		Utils.displayMessage(7, "Right -> \"D\" or the right arrow", B_WIDTH / 2, (B_HEIGHT / 2) - 10, -1, "#FFFFFF",
				10, getFont());
		Utils.displayMessage(8, "Up -> \"W\" or the up arrow", B_WIDTH / 2, B_HEIGHT / 2, -1, "#FFFFFF", 10, getFont());
		Utils.displayMessage(9, "Down -> \"S\" or the down arrow", B_WIDTH / 2, (B_HEIGHT / 2) + 10, -1, "#FFFFFF", 10, getFont());
		Utils.displayMessage(10, "Jump -> SPACE", B_WIDTH / 2, (B_HEIGHT / 2) + 20, -1, "#FFFFFF", 10, getFont());
		Utils.displayMessage(11, "Open Inventory -> \"E\"", B_WIDTH/2, (B_HEIGHT / 2) + 30, -1, "#FFFFFF", 10, getFont());
		Utils.displayMessage(12, "Use tool -> SHIFT", B_WIDTH / 2, (B_HEIGHT / 2) + 40, -1, "#FFFFFF", 10, getFont());
		

	}

	private void drawInventory(Graphics g) {
		try {
			g.drawImage(Background.WIN.getImage(), 0, 0, Bridge.getGameBoardSize(0), Bridge.getGameBoardSize(1), null);

			String inv = "Inventory";

			String close = "Close";

			String select = "Select";

			Font small = new Font("Helvetica", Font.BOLD, 14);

			Font large = new Font("Helvetica", Font.BOLD, 25);

			FontMetrics fmlarge = getFontMetrics(large);

			g.setColor(Color.WHITE);

			g.setFont(small);

			g.drawString(inv, (B_WIDTH / 2) - (fmlarge.stringWidth(inv) / 2), 30);

			g.drawImage(Bridge.getPlayer().inventory.get(l).getImage(), B_WIDTH / 2, B_HEIGHT / 2, 50, 50, this);

			clickables.add(new Button(close, B_WIDTH / 4, (B_HEIGHT / 2 + B_HEIGHT) / 2, B_WIDTH / 6, 18, Color.GRAY,
					Color.WHITE, small, ButtonMethod.CLOSE_INVENTORY));

			clickables.add(new Button(select, (B_WIDTH / 2 + B_WIDTH) / 2, (B_HEIGHT / 2 + B_HEIGHT) / 2, B_WIDTH / 6,
					18, Color.GRAY, Color.WHITE, small, ButtonMethod.SELECT_TOOL));

			for (Clickable clickable : clickables) {
				clickable.drawPolygon(g);
				
			}
			
			if (mouse) g.drawImage(Texture.loadTexture("pointer.png"), mx, my, this);

		} catch (IndexOutOfBoundsException ex) {
			inv = false;
			Utils.addPlayerMessage(new Random().nextInt(), "Your inventory is empty", 0, 15, 10, "#FFFFFF", 10);
		}

	}

	private void drawWin(Graphics g) {
		Button restart = new Button("Try again", B_WIDTH / 2, B_HEIGHT / 2, B_WIDTH / 6, B_HEIGHT / 10,
				Color.decode("#44cc44"), Color.white, new Font("Helvetica", Font.PLAIN, 15), ButtonMethod.RESTART);
		clickables.clear();
		clickables.add(restart);

		g.drawImage(Background.WIN.getImage(), 0, 0, Bridge.getGameBoardSize(0), Bridge.getGameBoardSize(1), this);

		String won = "You won!";

		Font small = new Font("Helvetica", Font.BOLD, 14);
		Font large = new Font("Helvetica", Font.BOLD, 25);
		// FontMetrics fmsmall = getFontMetrics(small);
		FontMetrics fmlarge = getFontMetrics(large);

		g.setColor(Color.white);
		g.setFont(large);
		g.drawString(won, (B_WIDTH - fmlarge.stringWidth(won)) / 2, B_HEIGHT / 3);
		g.setColor(Color.red);
		g.setFont(small);
		for (Clickable clickable : clickables) {
			clickable.drawPolygon(g);
		}
		
		if (mouse) g.drawImage(Texture.loadTexture("pointer.png"), mx, my, this);

		

	}
	
	public void drawLoading(Graphics g) {

		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,35));
		g.setColor(Color.WHITE);
//		Image i = Texture.loadTexture("loading.gif");
//		g.drawImage(i, B_WIDTH/2, B_WIDTH/2, this);
		g.drawString("LOADING...\nPlease wait..", B_WIDTH/2 - g.getFontMetrics().stringWidth("LOADING...\nPlease wait..")/2, B_HEIGHT/2 - g.getFontMetrics().getHeight());
		
	}

	private void drawObjects(Graphics g) {
		
		e=0;
		
//		Utils.broadcastMessage(extra + "");

		g.drawImage(Utils.getBackground(Utils.getPlayerLevel()).getImage(), 0, 0, (int) (960.0*extra), (int) (540.0*extra), this);
		g.setColor(Color.black);
		g.setFont(new Font("Helvetica", Font.BOLD, 20));
		
		
		
		for (Sprite sprite : sprites) {
			
			if(sprite instanceof Entity){
				e = e+1;
				continue;
			}
			if(sprite instanceof Floor){
				Color color = ((Floor) sprite).getBackgroundType().getColor();
				g.setColor(color);
				
				g.fillRect(sprite.x, sprite.y, (int) (30*extra), (((sprite.x+getHeight()))));
				
			}
			if(sprite instanceof Switch){
				g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), this);
				
				if (debug && hitboxes)
					g.drawPolygon(sprite.getPolygon());
				continue;
			}
			g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), (int) (sprite.getWidth()), (int) (sprite.getHeight()), this);
			
			if (debug && hitboxes){
				drawOutlinePolygon(sprite.getPolygon(),g,Color.WHITE,Color.BLACK);
				
			}
		}

		for (Moveable s : moveables) {
			e = e+1;
			Sprite sprite = (Sprite) s;
			SpriteType type = (sprite).getType();
			switch (type) {
			case FALLING_FLOOR:
				
				Color color = ((FallingFloor) sprite).getBackgroundType().getColor();
				g.setColor(color);
				g.fillRect(sprite.x, sprite.y, (int) (30*extra), sprite.x+getHeight());
				Rectangle rec = new Rectangle(sprite.x, sprite.y, (int) (30*extra), sprite.x+getHeight());
				Image breaking = Texture.loadTexture("objects/breaking.png");
				for(int x=0;x!=rec.width/30;x++){
					for(int y=0;y!=rec.height/30;y++){
						g.drawImage(breaking, sprite.x-x*30, sprite.y+y*30, this);
					}
				}
				
				g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), (int) (sprite.getWidth()*extra), (int) (sprite.getHeight()*extra), this);
				break;
			case COMPETITOR:
				Competitor sk = (Competitor) s;
				g.drawImage(sprite.getImage(), (int) (sprite.getX()*extra), (int) (sprite.getY()*extra), (int) (Bridge.getPlayer().getWalkingWidth()), (int) (Bridge.getPlayer().getWalkingHeight()), this);
				((Competitor) s).drawHealthBar(g, sk.x - (50 / 2), sk.y - 20, 50, 5);
				break;
			case ARROW:
				
//				g.drawImage(Images.rotate(sprite.getImage(), Math.asin(((Arrow) sprite).dx)/ Math.asin(((Arrow) sprite).dy)), (int) (sprite.getX()*extra), (int) (sprite.getY()*extra), (int) (16), (int) (4), this);
				try{
					if(((Arrow) sprite).getDirection().equals(Direction.LEFT)) g.drawImage(sprite.getImage(), (int) ((sprite.x + 16)*extra), (int) (sprite.y*extra), -16, 4, this);
					else g.drawImage(sprite.getImage(), (int) (sprite.getX()*extra), (int) (sprite.getY()*extra), (int) (16), (int) (4), this);
					
				} catch(NullPointerException ex){
					
					((Arrow)sprite).draw(g);
					
					
					
//					sprite.width = (int) p.getBounds().getWidth();
//					sprite.height = (int) p.getBounds().getHeight();
					
					
//					g.drawImage(Images.rotate(sprite.getImage(), Math.toDegrees(Math.atan(((Arrow)sprite).dy/((Arrow)sprite).dx))), sprite.x, sprite.y,(int)p.getBounds().getWidth(),(int)p.getBounds().getHeight(),this);
					

				}
				
				break;
			default:
				g.drawImage(sprite.getImage(), (int) (sprite.getX()*extra), (int) (sprite.getY()*extra), (int) (sprite.getWidth()), (int) (sprite.getHeight()), this);
				break;
			
			}

			if (debug && hitboxes)
				g.drawPolygon(sprite.getPolygon());
		}
		Player player = Bridge.getPlayer();

		Bridge.getPlayer().drawHealthBar(g, (int) ((player.x - (100 / 2))*extra), (int) ((player.y - 20)*extra), 100, 5);
		
		e=e+1;
		
		if (!Bridge.getPlayer().walking) {
			if(player.getFacingDirection().equals(Direction.LEFT))
				g.drawImage(player.getImage(), player.x + player.getRestingWidth(), player.y, -(player.getRestingWidth()), player.getRestingHeight(), this);
			if(player.getFacingDirection().equals(Direction.RIGHT))
				g.drawImage(player.getImage(), player.x, player.y, player.getRestingWidth(), player.getRestingHeight(), this);
		} else {
			if(player.getFacingDirection().equals(Direction.LEFT))
				g.drawImage(player.getImage(), player.x + player.getWalkingWidth(), player.y, -(player.getWalkingWidth()), player.getWalkingHeight(), this);
			if(player.getFacingDirection().equals(Direction.RIGHT))
				g.drawImage(player.getImage(), player.x, player.y, player.getWalkingWidth(), player.getWalkingHeight(), this);
		}
		
		if(Bridge.player.shifting && !(debug) && Bridge.player.hasTool() && Bridge.player.getTool() instanceof Bow){
			g.drawImage(Texture.loadTexture("aim.png"), mx, my, this); 
		}
		
		
		if (Bridge.getPlayer().hasTool()) {
			Tool tool = ((Player) Bridge.getPlayer()).getTool();
			g.drawImage(tool.getImage(), ((B_WIDTH / 2 + B_WIDTH) / 2 + g.getFontMetrics().stringWidth("Tool:")), 25, this);
		} else {
			g.drawImage(Texture.loadTexture("unknown.png"), ((B_WIDTH / 2 + B_WIDTH) / 2 + g.getFontMetrics().stringWidth("Tool:")), 25, this);
		}
		if (debug && hitboxes)
			g.drawPolygon(Bridge.getPlayer().getPolygon());

		for (Sprite sprite : tools) {
			if(sprite instanceof Entity) e=e+1;
			g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), this);
		}

		


		g.setFont(new Font("Helvetica", Font.PLAIN, 20));
		
		drawOutlineString("Score: " + Bridge.getPlayer().getScore(), (B_WIDTH / 2 + B_WIDTH) / 2, 20, g, Color.WHITE, Color.BLACK);
		drawOutlineString("Tool:", (B_WIDTH / 2 + B_WIDTH) / 2, 40, g, Color.WHITE, Color.BLACK);
		drawOutlineString("Level: " + Bridge.getPlayer().level, (B_WIDTH / 2 + B_WIDTH) / 2, 60, g, Color.WHITE, Color.BLACK);
		

		for(int life=0;life!=maxlives ;life++){
			if(life < Bridge.getPlayer().lives){
				g.drawImage(Texture.loadTexture("heart.png"), life*30, 1*15, 30, 30, this);
			} else g.drawImage(Texture.loadTexture("broken_heart.png"), life*30, 1*15, 30, 30, this);
		}
		
		if (paused) {
			g.drawImage(Texture.loadTexture("backgrounds/darken.png"), 0, 0, null);
		}

		for (Entry<Integer, String> entry : messages.entrySet()) {
			String[] info = entry.getValue().split(":");
			int time = Integer.parseInt(info[3]);
			if (time != -1) {
				g.setColor(Color.decode(info[4]));
				g.setFont(new Font("Helvetica", Font.BOLD, Integer.parseInt(info[5])));
				if (info[0].contains("/n")) {
					String[] m = info[0].split("/n");
					for (int i = 0; i != m.length; i++) {
						drawOutlineString(m[i], Integer.parseInt(info[1]),
								Integer.parseInt(info[2]) + (i * Integer.parseInt(info[5])), g, Color.WHITE, Color.BLACK);
					}
				} else
				drawOutlineString(info[0], Integer.parseInt(info[1]), Integer.parseInt(info[2]), g, Color.WHITE, Color.BLACK);
				if (time != 0)
					strings_temp.add(entry.getKey() + "~" + info[0] + ":" + info[1] + ":" + info[2] + ":" + (time - 1)
							+ ":" + info[4] + ":" + info[5]);

			}
			if (time == -1) {
				g.setColor(Color.decode(info[4]));
				g.setFont(new Font("Helvetica", Font.BOLD, Integer.parseInt(info[5])));
				if (info[0].contains("/n")) {
					String[] m = info[0].split("/n");
					for (int i = 0; i != m.length; i++) {
						drawOutlineString(m[i], Integer.parseInt(info[1]) * 2,
								Integer.parseInt(info[2]) + (i * Integer.parseInt(info[5])), g, Color.WHITE, Color.BLACK);
					}
				} else
					drawOutlineString(info[0], Integer.parseInt(info[1]), Integer.parseInt(info[2]), g, Color.WHITE, Color.BLACK);
				strings_temp.add(entry.getKey() + "~" + info[0] + ":" + info[1] + ":" + info[2] + ":" + time + ":"
						+ info[4] + ":" + info[5]);
			}
		}
		messages.clear();
		for (String string : strings_temp) {
			String[] info = string.split("~");

			messages.put(Integer.parseInt(info[0]), info[1]);

		}
		strings_temp.clear();

		for (Entry<Integer, String> entry : messages_player.entrySet()) {
			String[] info = entry.getValue().split(":");
			int time = Integer.parseInt(info[3]);
			if (time != -1) {
				g.setColor(Color.decode(info[4]));
				g.setFont(new Font("Helvetica", Font.BOLD, Integer.parseInt(info[5])));
				if (info[0].contains("/n")) {
					String[] m = info[0].split("/n");
					for (int i = 0; i != m.length; i++) {
						drawOutlineString(m[i], player.x+Integer.parseInt(info[1]),
								player.y+(Integer.parseInt(info[2])+(i*Integer.parseInt(info[5]))), g, Color.WHITE, Color.BLACK);
					}
				} else
					drawOutlineString(info[0], player.x+Integer.parseInt(info[1]), player.y+Integer.parseInt(info[2]), g, Color.WHITE, Color.BLACK);
				if (time != 0)
					strings_temp_player.add(entry.getKey() + "~" + info[0] + ":" + info[1] + ":" + info[2] + ":" + (time - 1)
							+ ":" + info[4] + ":" + info[5]);

			}
			if (time == -1) {
				g.setColor(Color.decode(info[4]));
				g.setFont(new Font("Helvetica", Font.BOLD, Integer.parseInt(info[5])));
				if (info[0].contains("/n")) {
					String[] m = info[0].split("/n");
					for (int i = 0; i != m.length; i++) {
						drawOutlineString(m[i], player.x+Integer.parseInt(info[1]),
								player.y+(Integer.parseInt(info[2])+(i*Integer.parseInt(info[5]))), g, Color.WHITE, Color.BLACK);
					}
				} else
					drawOutlineString(info[0], player.x+Integer.parseInt(info[1]), player.y+Integer.parseInt(info[2]), g, Color.WHITE, Color.BLACK);
				strings_temp_player.add(entry.getKey() + "~" + info[0] + ":" + info[1] + ":" + info[2] + ":" + time + ":"
						+ info[4] + ":" + info[5]);
			}
		}
		messages_player.clear();
		for (String string : strings_temp_player) {
			String[] info = string.split("~");

			messages_player.put(Integer.parseInt(info[0]), info[1]);

		}
		strings_temp_player.clear();

		for (Clickable clickable : clickables) {
			g.drawPolygon(clickable.drawPolygon(g));
		}
		
		if(paused || debug) if (mouse) g.drawImage(Texture.loadTexture("pointer.png"), mx, my, this);
		
		if(in && debug) this.sprite.drawInfo(mx, my, g);
		
		
		
		if (debug) {
			
			g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, d));
			
			drawOutlineString("Version: " + Bridge.getGame().getVersion(), 0, d, g, Color.WHITE, Color.BLACK);
			
				
			drawOutlineString("Gravity: " + player.gravity, 0, d*2, g, Color.WHITE, Color.BLACK);
			drawOutlineString("Falling: " + player.falling, 0, d*3, g, Color.WHITE, Color.BLACK);
			drawOutlineString("Onground: " + player.onground, 0, d*4, g, Color.WHITE, Color.BLACK);
			drawOutlineString("Jumping: " + player.jumping, 0, d*5, g, Color.WHITE, Color.BLACK);
			drawOutlineString("Speed: " + player.speedboost, 0, d*6, g, Color.WHITE, Color.BLACK);
			drawOutlineString("Location: " + player.x + ":" + player.y, 0, d*7, g, Color.WHITE, Color.BLACK);
			drawOutlineString("Left: " + player.left, 0, d*8, g, Color.WHITE, Color.BLACK);
			drawOutlineString("Right: " + player.right, 0, d*9, g, Color.WHITE, Color.BLACK);
			drawOutlineString("Up: " + player.up, 0, d*10, g, Color.WHITE, Color.BLACK);
			drawOutlineString("Climbing: " + player.climbing, 0, d*11, g, Color.WHITE, Color.BLACK);
			drawOutlineString("Level: " + player.level, 0, d*12, g, Color.WHITE, Color.BLACK);
			drawOutlineString("Entities: " + e, 0, d*13, g, Color.WHITE, Color.BLACK);
			
		
			
		}
		
		
		
		

	}
	
	public static void drawOutlineString(String string, int x, int y, Graphics g, Color inside, Color outside){
		g.setColor(outside);
		g.drawString(string, x-1, y-1);
		g.drawString(string, x-1, y+1);
		g.drawString(string, x+1, y-1);
		g.drawString(string, x+1, y+1);
		g.setColor(inside);
		g.drawString(string, x, y);
	}
	
	public static void drawOutlinePolygon(Polygon p, Graphics g, Color inside, Color outsite){
		g.setColor(inside);
		g.drawPolygon(p);
		g.setColor(outsite);
		g.drawPolygon(new int[] {p.xpoints[0]-1,p.xpoints[1]+1,p.xpoints[2]+1,p.xpoints[3]-1}, new int[] {p.ypoints[0]-1,p.ypoints[1]-1,p.ypoints[2]+1,p.ypoints[3]+1}, 4);
		g.drawPolygon(new int[] {p.xpoints[0]+1,p.xpoints[1]-1,p.xpoints[2]-1,p.xpoints[3]+1}, new int[] {p.ypoints[0]+1,p.ypoints[1]+1,p.ypoints[2]-1,p.ypoints[3]-1}, 4);
}

	private void drawGameOver(Graphics g) {

		if (gameStatus.contains("gameover")) {
			
			
			g.drawImage(Background.GAMEOVER.getImage(), 0, 0, Bridge.getGameBoardSize(0), Bridge.getGameBoardSize(1), this);
			g.setColor(Color.black);
			
			String gameover = "Game Over";
			String reason = gameStatus.split(":")[1];
			reason = reason.replaceAll("_", " ");

			Button restart = new Button("Try again", B_WIDTH / 2, B_HEIGHT / 2, B_WIDTH / 6, B_HEIGHT / 10,
					Color.decode("#950000"), Color.white, new Font("Helvetica", Font.PLAIN, 15), ButtonMethod.RESTART);
			
			
			clickables.clear();
			clickables.add(restart);

			g.drawPolygon(restart.drawPolygon(g));
			

			g.setFont(new Font("Helvetica", Font.BOLD, 20));
			g.drawString(gameover, (B_WIDTH - g.getFontMetrics().stringWidth(gameover)) / 2, B_HEIGHT / 4);
			
			g.setFont(new Font("Helvetica", Font.BOLD, 15));
			g.drawString(reason, (B_WIDTH - g.getFontMetrics().stringWidth(reason)) / 2, B_HEIGHT / 3);
			

			if (mouse) g.drawImage(Texture.loadTexture("pointer.png"), mx, my, this);
			
		}
		if (gameStatus.contains("won")) {

			g.drawImage(Background.WIN.getImage(), 0, 0, Bridge.getGameBoardSize(0), Bridge.getGameBoardSize(1), this);
			g.setColor(Color.black);
			String won = "You won level " + gameStatus.split(":")[1] + "!";

			Button levelup = new Button("Next Level", B_WIDTH / 2, B_HEIGHT / 2, B_WIDTH / 6, B_HEIGHT / 10,
					Color.decode("#44cc44"), Color.white, new Font("Helvetica", Font.PLAIN, 15), ButtonMethod.LEVEL_UP);
			clickables.add(levelup);

			g.drawPolygon(levelup.drawPolygon(g));
			

			Font small = new Font("Helvetica", Font.BOLD, 14);
			FontMetrics fm = getFontMetrics(small);
			g.setFont(small);
			g.drawString(won, (B_WIDTH - fm.stringWidth(won)) / 2, B_HEIGHT / 3);
			

			if (mouse) g.drawImage(Texture.loadTexture("pointer.png"), mx, my, this);

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// if(loaded)

		if (!paused && Bridge.getPlayer().ready)
			update();

		repaint();

	}

	private void update() {
		if(!paused && !inv){
			
			for(Moveable sprite : moveables){
				sprites.remove(sprite);
				sprite.move();
				moveables_temp.add(sprite);	
				
			}
			moveables.clear();
			for (Moveable sprite : moveables_temp) {
				moveables.add(sprite);
				sprites.add(((Sprite)sprite));
			}
			moveables_temp.clear();
			Bridge.getPlayer().move();
			for (Sprite sprite : removedSprites){
				if (moveables.contains(sprite))
					moveables.remove(sprite);
				if (tools.contains(sprite))
					tools.remove(sprite);
				if (sprites.contains(sprite)) {
					sprites.remove(sprite);
				}
			}
			removedSprites.clear();
			
			for(Sprite sprite : sprite_temp){
				if(sprite instanceof Moveable){
					moveables.add(((Moveable) sprite));
				} else 
					sprites.add(sprite);
			}
			sprite_temp.clear();

		}
	}
	
	public void toggleGravity() {
		if (Bridge.player.gravity){
			Bridge.player.gravity = false;
			Bridge.player.dx=0;
			Bridge.player.dy=0;
		} else
			Bridge.player.gravity = true;
	}

	public void toggleHitboxes() {
		if (hitboxes)
			hitboxes = false;
		else
			hitboxes = true;
	}

	public String getLocation(int x, int y) {
		return x + ":" + y;
	}

	public void toggleDebugMode() {
		if (debug)
			debug = false;
		else
			debug = true;
	}

	public int getLevels() {
		return levels.size();
	}

	public void start() {
		this.init();
	}

	public void pause() {
		paused = true;

		clickables.add(new Button("Resume", B_WIDTH / 4, B_HEIGHT / 2, B_WIDTH / 6, 50, Color.gray, Color.white,
				new Font("Helvetica", Font.BOLD, 25), ButtonMethod.RESUME));
		clickables.add(new Button("Main Menu", (B_WIDTH / 2 + B_WIDTH) / 2, B_HEIGHT / 2, B_WIDTH / 4, 50, Color.gray,
				Color.white, new Font("Helvetica", Font.BOLD, 25), ButtonMethod.MAIN_MENU));

	}

	public void resume() {
		clickables.clear();
		for (Entry<Integer, String> entry : messages.entrySet()) {
			String[] split = entry.getValue().split(":");
			if (Integer.parseInt(split[3]) == -1) {
				strings_temp.add(entry.getKey() + "~" + split[0] + ":" + split[1] + ":" + split[2] + ":" + "1" + ":"
						+ split[4] + ":" + split[5]);
			} else
				strings_temp.add(entry.getKey() + "~" + split[0] + ":" + split[1] + ":" + split[2] + ":" + split[3]
						+ ":" + split[4] + ":" + split[5]);
		}
		messages.clear();
		for (String string : strings_temp) {
			String[] info = string.split("~");

			messages.put(Integer.parseInt(info[0]), info[1]);

		}
		strings_temp.clear();

		for (Entry<Integer, String> entry : messages_player.entrySet()) {
			String[] split = entry.getValue().split(":");
			if (Integer.parseInt(split[3]) == -1) {
				strings_temp_player.add(entry.getKey() + "~" + split[0] + ":" + split[1] + ":" + split[2] + ":" + "1"
						+ ":" + split[4] + ":" + split[5]);
			} else
				strings_temp_player.add(entry.getKey() + "~" + split[0] + ":" + split[1] + ":" + split[2] + ":"
						+ split[3] + ":" + split[4] + ":" + split[5]);
		}
		for (String string : strings_temp_player) {
			String[] info = string.split("~");

			messages_player.put(Integer.parseInt(info[0]), info[1]);

		}
		strings_temp_player.clear();
		paused = false;

	}

	public void drawRectangle(Rectangle point) {
		// recs.add(point);
	}

	public void addMoveable(Moveable moveable) {
		moveables_temp.add(moveable);
	}

	public void removeSprite(Sprite sprite) {
		removedSprites.add(sprite);
	}



	private class TAdapter extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			for (Sprite sprite : getLevel(Bridge.player.level))
				if (sprite instanceof Keyable)
					((Keyable) sprite).keyReleased(e);

		}

		@Override
		public void keyPressed(KeyEvent e) {
			
			if(e.getKeyCode() == KeyEvent.VK_R){
				loadLevel();
			}
			
			if(inv){

				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					if (l == 0) {
						l = Bridge.player.inventory.size() - 1;
					} else {
						l = l - 1;
					}
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (l == Bridge.player.inventory.size() - 1) {
						l = 0;
					} else {
						l = l + 1;
					}
				}
				
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					ButtonMethod.SELECT_TOOL.clicked();
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					ButtonMethod.CLOSE_INVENTORY.clicked();
					return;
				}
				
				

				

			}
			
			if(!ingame && gameStatus.contains("won")){
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					ButtonMethod.LEVEL_UP.clicked();
				}
			}
			
//		if(e.getKeyCode() == KeyEvent.VK_F11){
//				
//				Utils.broadcastMessage(extra + "");
//				if(!fs){
//					
//					timer.setDelay(DELAY/2);
//					Utils.broadcastMessage("FS false");
//					
//					Bridge.getGame().dispose();
//					Bridge.getGame().setUndecorated(true);
//					vc.setFullScreenWindow(Bridge.getGame());
//					fs = true;
//					extra = ((double) Bridge.getGame().getWidth())/((double) Bridge.getGameBoardSize(0));
//					
//					for(Sprite sprite : sprites){
//						sprite.x = (int) (sprite.x*extra);
//						sprite.y = (int) (sprite.y*extra);
//						sprite.height = (int) (sprite.height*extra);
//						sprite.width = (int) (sprite.width*extra);
//					}
//					
//					for(Moveable sprite : moveables){
//						((Sprite) sprite).x = (int) (((Sprite) sprite).x*extra);
//						((Sprite) sprite).y = (int) (((Sprite) sprite).y*extra);
//						((Sprite) sprite).height = (int) (((Sprite) sprite).height*extra);
//						((Sprite) sprite).width = (int) (((Sprite) sprite).width*extra);
//					}
//					
//					for(Sprite sprite : tools){
//						sprite.x = (int) (sprite.x*extra);
//						sprite.y = (int) (sprite.y*extra);
//						sprite.height = (int) (sprite.height*extra);
//						sprite.width = (int) (sprite.width*extra);
//					}
//					
//					Bridge.player.x = (int) (Bridge.player.x*extra);
//					Bridge.player.y = (int) (Bridge.player.y*extra);
//					Bridge.player.rh = (int) (Bridge.player.rh*extra);
//					Bridge.player.rw = (int) (Bridge.player.rw*extra);
//					Bridge.player.wh = (int) (Bridge.player.wh*extra);
//					Bridge.player.ww = (int) (Bridge.player.ww*extra);
//					
//					
//					return;
//				}
//				else {
//					
//					timer.setDelay(DELAY);
//					Utils.broadcastMessage("FS true");
//					
//					
//					for(Sprite sprite : sprites){
//						sprite.x = (int) (sprite.x/extra);
//						sprite.y = (int) (sprite.y/extra);
//						sprite.height = (int) (sprite.height/extra);
//						sprite.width = (int) (sprite.width/extra);
//					}
//					
//					for(Moveable sprite : moveables){
//						((Sprite) sprite).x = (int) (((Sprite) sprite).x/extra);
//						((Sprite) sprite).y = (int) (((Sprite) sprite).y/extra);
//						((Sprite) sprite).height = (int) (((Sprite) sprite).height/extra);
//						((Sprite) sprite).width = (int) (((Sprite) sprite).width/extra);
//					}
//					
//					for(Sprite sprite : tools){
//						sprite.x = (int) (sprite.x/extra);
//						sprite.y = (int) (sprite.y/extra);
//						sprite.height = (int) (sprite.height/extra);
//						sprite.width = (int) (sprite.width/extra);
//					}
//					
//					Bridge.player.x = (int) (Bridge.player.x/extra);
//					Bridge.player.y = (int) (Bridge.player.y/extra);
//					Bridge.player.rh = (int) (Bridge.player.rh/extra);
//					Bridge.player.rw = (int) (Bridge.player.rw/extra);
//					Bridge.player.wh = (int) (Bridge.player.wh/extra);
//					Bridge.player.ww = (int) (Bridge.player.ww/extra);
//					fs = false;
//					Bridge.getGame().dispose();
//					vc.setFullScreenWindow(null);
//					Bridge.getGame().setUndecorated(false);
//					Bridge.getGame().setVisible(true);
//					
//					extra = 1;
//					
//				}
//				
//			}
			
//			if(e.getKeyCode() == KeyEvent.VK_1){
//				Utils.setPlayerLevel(1);
//				loadLevel();
//			}
//			
//			if(e.getKeyCode() == KeyEvent.VK_2){
//				Utils.setPlayerLevel(2);
//			}
//			
//			if(e.getKeyCode() == KeyEvent.VK_3){
//				Utils.setPlayerLevel(3);
//			}
//			
//			if(e.getKeyCode() == KeyEvent.VK_4){
//				Utils.setPlayerLevel(4);
//			}
//			
//			if(e.getKeyCode() == KeyEvent.VK_5){
//				Utils.setPlayerLevel(5);
//			}
//			
//			if(e.getKeyCode() == KeyEvent.VK_6){
//				Utils.setPlayerLevel(6);
//			}
//			
//			if(e.getKeyCode() == KeyEvent.VK_7){
//				Utils.setPlayerLevel(7);
//			}
//			
//			if(e.getKeyCode() == KeyEvent.VK_8){
//				Utils.setPlayerLevel(8);
//			}
			for (Sprite sprite : getLevel(Bridge.player.level))
				if (sprite instanceof Keyable)
					((Keyable) sprite).keyPressed(e);
		}
	}

	private class MMListener extends MouseMotionAdapter {
		
		
		
		public void mouseDragged(MouseEvent e){
			
			mx = e.getX();
			my = e.getY();
			if(debug)
				for(Sprite ssprite : sprites){
					if(ssprite.getPolygon().contains(new Point(mx, my))){
						in = true;
						sprite = ssprite;
						return;
					}
					sprite = null;
					in = false;
				}
			
		
			for (Clickable clickable : clickables) {
				if (clickable.getPolygon().contains(e.getPoint())) {
					clickable.mouseMoved(e);
				}
			}

		}

		public void mouseMoved(MouseEvent e) {
			
			if(board.contains(e.getPoint())) mouse = true; else mouse = false;
			
			mx = e.getX();
			my = e.getY();
			if(debug)
				for(Sprite ssprite : sprites){
					if(ssprite.getPolygon().contains(new Point(mx, my))){
						in = true;
						sprite = ssprite;
						return;
					}
					sprite = null;
					in = false;
				}
			
		
			for (Clickable clickable : clickables) {
				if (clickable.getPolygon().contains(e.getPoint())) {
					clickable.mouseMoved(e);
				}
			}

		}
	}

	private class MListener extends MouseAdapter {

		public void mousePressed(MouseEvent e) {

			try {
				for (Clickable clickable : clickables)
					if (clickable.getPolygon().contains(e.getPoint())) {
						clickable.mousePressed(e);
					}
				Bridge.player.mouseClicked(e);
			} catch (ConcurrentModificationException ex) {
			}
		}

		public void mouseReleased(MouseEvent e) {
			for (Clickable clickable : clickables)
				if (clickable.getPolygon().contains(e.getPoint()))
					clickable.mouseReleased(e);
		}

	}

	public boolean addSprite(Sprite sprite2) {
		if(moveables.contains(sprite2) || sprites.contains(sprite2))
			return false;
		else sprite_temp.add(sprite2);
		return true;
	}
	

	
	
	

}

