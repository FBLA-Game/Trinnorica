package org.fbla.game.boards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
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

import javax.sound.sampled.FloatControl;
import javax.swing.Timer;

import org.fbla.game.Bridge;
import org.fbla.game.spriteutils.Clickable;
import org.fbla.game.utils.Background;
import org.fbla.game.utils.Board;
import org.fbla.game.utils.BoardType;
import org.fbla.game.utils.Button;
import org.fbla.game.utils.ButtonMethod;
import org.fbla.game.utils.Images;
import org.fbla.game.utils.Utils;

import res.Audio;
import res.Texture;

public class CreditsBoard extends Board implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int M_WIDTH = 960;
	public int M_HEIGHT = 640;
	private int mx = 0;
	private int my = 0;
	private int i = 0;
	private double a = 0.2;
	private int st = M_HEIGHT;
	private int si = 20;
	Timer timer;
	boolean drawing = true;
	private Board board;
	boolean mouse = true;
	public List<Clickable> clickables = new ArrayList<>();

	public CreditsBoard() {
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
		
		clickables.add(new Button("Back to menu", 50, 10, 100, 20, Color.GRAY, Color.WHITE, new Font(Font.SANS_SERIF, Font.BOLD, si), ButtonMethod.MAIN_MENU));


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
		if(st+(si*50)-i <= 0){
			startCreditsClose(g, a);
			return;
		}
		
		a=0.2;

		g.drawImage(Background.MENU.getImage(), 0, 0, M_WIDTH, M_HEIGHT, null);
		g.drawImage(Images.makeImageTranslucent(Images.toBufferedImage(Images.createColorImage("#000000")), a), 0, 0, M_WIDTH, M_HEIGHT, this);
//		g.drawImage(Background.DARKEN.getImage(), 0, 0, M_WIDTH, M_HEIGHT, null);
		
		g.setColor(Color.WHITE);
//		Bridge.font.getSize();
		g.setFont(Bridge.font);
		
		
//		g.drawString("Trinnorica", (M_WIDTH/2) - (g.getFontMetrics().stringWidth("Trinnorica")/2), 55);

//		g.setFont(new Font(Font.SANS_SERIF, Font.TYPE1_FONT, si));
		
		g.drawImage(Texture.loadTexture("title.png"), M_WIDTH/2-200, (st-55)-i, 400, 50, this);
		
		drawOutlineString("Authors & Developers:", x("Authors & Developers",g), st+(si*1)-i,g);
		drawOutlineString("Cameron Witcher - Head Developer", x("Cameron Witcher - Head Developer",g), st+(si*2)-i,g);
		drawOutlineString("Joseph Phillips - Level Designer & Story Creator", x("Joseph Phillips - Level Designer & Story Creator",g), st+(si*3)-i,g);
		
		drawOutlineString("Adviser:", x("Adviser:",g), st+(si*5)-i,g);
		drawOutlineString("Dawn Rottinghaus", x("Dawn Rottinghaus",g), st+(si*6)-i,g);
		
		drawOutlineString("Helpers & Beta Testers:", x("Helpers & Beta Testers:",g), st+(si*8)-i,g);
		drawOutlineString("Anna Kline - Head Beta Tester", x("Anna Kline - Head Beta Tester",g), st+(si*9)-i,g);
		drawOutlineString("Jenni Korteniemi", x("Jenni Korteniemi",g), st+(si*10)-i,g);
		drawOutlineString("Jordon Stiff", x("Jordon Stiff",g), st+(si*11)-i,g);
		drawOutlineString("Lindsay Studevant", x("Lindsay Studevant",g), st+(si*12)-i,g);
		drawOutlineString("Cynthia Crim", x("Cynthia Crim",g), st+(si*13)-i,g);
		drawOutlineString("Brent Long", x("Brent Long",g), st+(si*14)-i,g);
		drawOutlineString("Shantá Kemp", x("Shantá Kemp",g), st+(si*15)-i,g);
		drawOutlineString("Amanda Haagensen", x("Amanda Haagensen",g), st+(si*16)-i,g);
		drawOutlineString("Craig Chaney", x("Craig Chaney",g), st+(si*17)-i,g);
		drawOutlineString("Oscar Dunreath West V", x("Oscar Dunreath West V",g), st+(si*18)-i,g);
		drawOutlineString("Tori Ponce", x("Tori Ponce",g), st+(si*19)-i,g);
		drawOutlineString("Austin Borders", x("Austin Borders",g), st+(si*20)-i,g);
		drawOutlineString("Emery Mignot", x("Emery Mignot",g), st+(si*21)-i,g);
		drawOutlineString("Haylen Wilhite", x("Haylen Wilhite",g), st+(si*22)-i,g);
		drawOutlineString("Kiala Goodman", x("Kiala Goodman",g), st+(si*23)-i,g);
		drawOutlineString("Blake Qualls", x("Blake Qualls",g), st+(si*24)-i,g);
		drawOutlineString("Caleb Pearson", x("Caleb Pearson",g), st+(si*25)-i,g);
		drawOutlineString("Shayna Heart", x("Shayna Heart",g), st+(si*26)-i,g);
		drawOutlineString("Antonio Oropeza", x("Antonio Oropeza",g), st+(si*27)-i,g);
		drawOutlineString("Allison Farquhar", x("Allison Farquhar",g), st+(si*28)-i,g);
		drawOutlineString("Carsyn Stephenson", x("Carsyn Stephenson",g), st+(si*29)-i,g);
		drawOutlineString("Garrett George", x("Garrett George",g), st+(si*30)-i,g);
		drawOutlineString("Brian Kindle", x("Brian Kindle",g), st+(si*31)-i,g);
		drawOutlineString("Marshall Christian", x("Marshall Christian",g), st+(si*32)-i,g);
		drawOutlineString("Dylan McPeek", x("Dylan McPeek",g), st+(si*33)-i,g);
		drawOutlineString("Tyler Christensen", x("Tyler Christensen",g), st+(si*34)-i,g);
		drawOutlineString("Gaelen Hewitt", x("Gaelen Hewitt",g), st+(si*35)-i,g);
		drawOutlineString("Thomas Kolstad", x("Thomas Kolstad",g), st+(si*36)-i,g);
		drawOutlineString("Grady Rutledge", x("Grady Rutledge",g), st+(si*37)-i,g);
		drawOutlineString("Mike Trendle", x("Mike Trendle",g), st+(si*38)-i,g);
		drawOutlineString("Edmund Scott", x("Edmund Scott",g), st+(si*39)-i,g);
		drawOutlineString("Andrew Lindsay", x("Andrew Lindsay",g), st+(si*40)-i,g);
		drawOutlineString("David Ross", x("David Ross",g), st+(si*41)-i,g);
		drawOutlineString("Mary Ann Chambers", x("Mary Ann Chambers",g), st+(si*42)-i,g);
		
		drawOutlineString("Music & Audio:", x("Music & Audio:",g), st+(si*44)-i,g);
		drawOutlineString("Background music: HeatleyBros", x("Background music: HeatleyBros",g), st+(si*45)-i,g);
		drawOutlineString("Sound Effects: Bfxr.net", x("Sound Effects: Bfxr.net",g), st+(si*46)-i,g);
		
		drawOutlineString("Logo design: Tailorbrands.com", x("Logo design: Tailorbrands.com",g), st+(si*48)-i,g);
		
		if(st+(si*(48+3))-i <= 90){
			drawOutlineString("A big thanks to FBLA for giving us the opportunity to create this game!", x("A big thanks to FBLA for giving us the opportunity to create this game!",g), 90-(si),g);
			g.drawImage(Texture.loadTexture("fbla-logo.png"), M_WIDTH/2 - 300, 90, 600, 465, this);
		}
		else{
			drawOutlineString("A big thanks to FBLA for giving us the opportunity to create this game!", x("A big thanks to FBLA for giving us the opportunity to create this game!",g), st+(si*(48+2))-i,g);
			g.drawImage(Texture.loadTexture("fbla-logo.png"), M_WIDTH/2 - 300, st+(si*(48+3))-i, 600, 465, this);
		}
		
		
		
		
		g.setFont(Bridge.font);
		
		
		g.setFont(new Font(Font.SANS_SERIF, Font.TYPE1_FONT, 15));
		
		for (Clickable clickable : clickables) {
			g.drawPolygon(clickable.drawPolygon(g));
		}
		
		
		if ( mouse )g.drawImage(Texture.loadTexture("pointer.png"), mx, my, this);
		
		i=i+1;
		

	}
	
	private void startCreditsClose(Graphics g, double a) {
		if(!(a>=1.0)){
			g.setFont(Bridge.font);
			g.drawImage(Background.MENU.getImage(), 0, 0, M_WIDTH, M_HEIGHT, null);
			drawOutlineString("A big thanks to FBLA for giving us the opportunity to create this game!", x("A big thanks to FBLA for giving us the opportunity to create this game!",g), 90-(si),g);
			g.drawImage(Texture.loadTexture("fbla-logo.png"), M_WIDTH/2 - 300, 90, 600, 465, this);
			g.drawImage(Images.makeImageTranslucent(Images.toBufferedImage(Images.createColorImage("#000000")), a), 0, 0, M_WIDTH, M_HEIGHT, this);
			this.a = a+0.005;
		}
		else ButtonMethod.MAIN_MENU.clicked();
		
	}
	
	

	public static void drawOutlineString(String string, int x, int y, Graphics g){
		g.setColor(Color.BLACK);
		g.drawString(string, x-1, y-1);
		g.drawString(string, x-1, y+1);
		g.drawString(string, x+1, y-1);
		g.drawString(string, x+1, y+1);
		g.setColor(Color.WHITE);
		g.drawString(string, x, y);
	}
	
	private int x(String string, Graphics g){
		return M_WIDTH/2 - (g.getFontMetrics().stringWidth(string)/2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	private class TAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e){
			int key = e.getKeyCode();
			
			if(key == KeyEvent.VK_R){
				i = 0;
			}
		}
		
	}

	private class MMListener extends MouseMotionAdapter {

		public void mouseMoved(MouseEvent e) {
			if(board.contains(e.getPoint())) mouse = true; else mouse = false;
			mx = e.getX();
			my = e.getY();
			for (Clickable clickable : clickables) {
				if (clickable.getPolygon().contains(e.getPoint())) {
					clickable.mouseMoved(e);
				}
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
