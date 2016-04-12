package org.fbla.game.boards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import org.fbla.game.Bridge;
import org.fbla.game.spriteutils.Clickable;
import org.fbla.game.utils.Background;
import org.fbla.game.utils.Board;
import org.fbla.game.utils.BoardType;
import org.fbla.game.utils.Button;
import org.fbla.game.utils.ButtonMethod;

import res.Texture;

public class MenuBoard extends Board implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int M_WIDTH = 960;
	public int M_HEIGHT = 640;
	public int LEVEL_DEBUG = 8;
	private int mx = 0;
	private int my = 0;
	Timer timer;
	boolean mouse = false;
	private Board board;
	public List<Clickable> clickables = new ArrayList<>();

	public MenuBoard() {
		
		setType(BoardType.MENU_BOARD);
		initBoard();
		board = this;
	}

	public void initBoard() {

		timer = new Timer(15, this);
		timer.start();

		addKeyListener(new TAdapter());
		addMouseMotionListener(new MMListener());
		addMouseListener(new MListener());

		clickables.add(new Button("Play", M_WIDTH / 4, (M_HEIGHT / 2 + M_HEIGHT) / 2, M_WIDTH / 4, (M_HEIGHT / 20 + 10),
				Color.gray, Color.white, new Font("Helvetica", Font.BOLD, 25), ButtonMethod.START));

		clickables.add(new Button("Quit", ((M_WIDTH / 2) + M_WIDTH) / 2, (M_HEIGHT / 2 + M_HEIGHT) / 2, M_WIDTH / 4,
				(M_HEIGHT / 20 + 10), Color.gray, Color.white, new Font("Helvetica", Font.BOLD, 25),
				ButtonMethod.QUIT));
		
		clickables.add(new Button("Credits", M_WIDTH / 2, ((((M_HEIGHT / 2) + M_HEIGHT)/2) + M_HEIGHT)/2, M_WIDTH / 4, (M_HEIGHT / 20 + 10),
				Color.gray, Color.white, new Font("Helvetica", Font.BOLD, 25), ButtonMethod.CREDITS));

		setLayout(null);

		setFocusable(true);

		setPreferredSize(new Dimension(M_WIDTH, M_HEIGHT));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawMenu(g);
		Toolkit.getDefaultToolkit().sync();
	}

	public void drawMenu(Graphics g) {
		
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 45));
		
		g.setColor(Color.WHITE);

		g.drawImage(Background.MENU.getImage(), 0, 0, M_WIDTH, M_HEIGHT, null);
		
		g.drawImage(Texture.loadTexture("title.png"), M_WIDTH/2-200, 25, 400, 50, this);
		
//		g.setFont(Bridge.font);
		
//		g.drawString("Trinnorica", (M_WIDTH/2) - (g.getFontMetrics().stringWidth("Trinnorica")/2), 55);

		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		
		String model;
		
		try{
			model = Bridge.getPlayer().getPlayerModel();
		} catch(NullPointerException ex){
			model = "yellow";
		}
		
		g.drawImage(Texture.loadTexture("playermodels/" +model+ "/stand.png"),
		
				(M_WIDTH / 2) - ((Bridge.getPlayer().getRestingWidth() * 5) / 2), (M_HEIGHT / 2) - ((Bridge.getPlayer().getRestingHeight() * 5) / 2), Bridge.getPlayer().getRestingWidth() * 5, Bridge.getPlayer().getRestingHeight() * 5, this);
//		Image img = Texture.loadTexture("moving_floor.gif");
//		g.drawImage(img,
//				(M_WIDTH / 2) - ((img.getWidth(null) * 5) / 2), (M_HEIGHT / 2) - ((img.getHeight(null) * 5) - Bridge.getPlayer().getWalkingHeight()/2 ), img.getWidth(null) * 5, img.getHeight(null) * 5, this);

		g.setColor(Color.white);

		for (Clickable clickable : clickables) {
			g.drawPolygon(clickable.drawPolygon(g));
		}
		
		
		if (mouse) g.drawImage(Texture.loadTexture("pointer.png"), mx, my, this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	private class TAdapter extends KeyAdapter {
		
		boolean g = false;
		boolean r = false;
		boolean n = false;

		@Override
		public void keyReleased(KeyEvent event) {
			
			if(event.getKeyCode() == KeyEvent.VK_G){
				g = false;
			}
			
			if(event.getKeyCode() == KeyEvent.VK_R){
				r = false;
			}
			
			
			if(event.getKeyCode() == KeyEvent.VK_N){
				n = false;
			}
			
		}

		@Override
		public void keyPressed(KeyEvent event) {

			// Cameron
			// Quit.
			if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
				Bridge.quit();
			}

			// Cameron
			
//			if(event.getKeyCode() == KeyEvent.VK_C){
//				Bridge.DEBUG = true;
//				Bridge.getPlayer().inventory.clear();
//				Bridge.getPlayer().inventory.add(new Bow(0,0));
//				Bridge.getPlayer().inventory.add(new NinjaCloak(0,0));
//				Bridge.getPlayer().inventory.add(new Key(0, 0, -1));
//				Bridge.getPlayer().inventory.add(new FireBow(0, 0));
//				
//			}
//			if(event.getKeyCode() == KeyEvent.VK_1){
//				Utils.setPlayerLevel(1);
//				LEVEL_DEBUG = 1;
//			}
//			
//			if(event.getKeyCode() == KeyEvent.VK_2){
//				Utils.setPlayerLevel(2);
//				LEVEL_DEBUG = 2;
//			}
//			
//			if(event.getKeyCode() == KeyEvent.VK_3){
//				Utils.setPlayerLevel(3);
//				LEVEL_DEBUG = 3;
//			}
//			
//			if(event.getKeyCode() == KeyEvent.VK_4){
//				Utils.setPlayerLevel(4);
//				LEVEL_DEBUG = 4;
//			}
//			
//			if(event.getKeyCode() == KeyEvent.VK_5){
//				Utils.setPlayerLevel(5);
//				LEVEL_DEBUG = 5;
//			}
//			
//			if(event.getKeyCode() == KeyEvent.VK_6){
//				Utils.setPlayerLevel(6);
//				LEVEL_DEBUG = 6;
//			}
//			
//			if(event.getKeyCode() == KeyEvent.VK_7){
//				Utils.setPlayerLevel(7);
//				LEVEL_DEBUG = 7;
//			}
//			
//			if(event.getKeyCode() == KeyEvent.VK_8){
//				Utils.setPlayerLevel(8);
//				LEVEL_DEBUG = 8;
//			}
//			if(event.getKeyCode() == KeyEvent.VK_9){
//				Utils.setPlayerLevel(9);
//				LEVEL_DEBUG = 9;
//			}
//			
//			if(event.getKeyCode() == KeyEvent.VK_0){
//				Utils.setPlayerLevel(10);
//				LEVEL_DEBUG = 10;
//			}
//			
//			
//			
			if(event.getKeyCode() == KeyEvent.VK_D){
				Bridge.openLevelDebug(LEVEL_DEBUG);
			}
			
			//Cameron
			//Hidden feature.
			
			if(event.getKeyCode() == KeyEvent.VK_G){
				g = true;
//				Utils.broadcastMessage("G true");
			}
			
			if(event.getKeyCode() == KeyEvent.VK_R){
				r = true;
//				Utils.broadcastMessage("R true");
			}
			
			
			
			if(event.getKeyCode() == KeyEvent.VK_N){
				n = true;
//				Utils.broadcastMessage("N true");
			}
			
			//Check if user is pressing "g", "r", and "n"
			
			if(g&&r&&n){
				if(Bridge.getPlayer().getPlayerModel().equalsIgnoreCase("yellow")) Bridge.getPlayer().setPlayerModel("green");
				else Bridge.getPlayer().setPlayerModel("yellow");
				g=false;
				r=false;
				n=false;
			}
		}
	}

	private class MMListener extends MouseMotionAdapter {

		public void mouseMoved(MouseEvent e) {
			if(board.contains(e.getPoint())){
				mouse = true;
				
			} else mouse = false;
			mx = e.getX();
			my = e.getY();
			for (Clickable clickable : clickables) {
				if (clickable.getPolygon().contains(e.getPoint())) {
					clickable.mouseMoved(e);
					((Button) clickable).over = true;
					
				} else ((Button) clickable).over = false;
			}

		}
		
	}

	private class MListener extends MouseAdapter {
		

		
		
		public void mousePressed(MouseEvent e) {
			for (Clickable clickable : clickables)
				if (clickable.getPolygon().contains(e.getPoint())) {
					clickable.mousePressed(e);
					break;
				}
		}

		public void mouseReleased(MouseEvent e) {
			for (Clickable clickable : clickables)
				if (clickable.getPolygon().contains(e.getPoint()))
					clickable.mouseReleased(e);
		}
	}

}
