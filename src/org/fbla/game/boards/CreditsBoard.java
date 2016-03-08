package org.fbla.game.boards;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
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

public class CreditsBoard extends Board implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int M_WIDTH = 960;
	public int M_HEIGHT = 640;
	private int mx = 0;
	private int my = 0;
	Timer timer;
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
		
		clickables.add(new Button("Back to menu", 100, 50, 100, 20, Color.GRAY, Color.WHITE, new Font(Font.SANS_SERIF, Font.BOLD, 15), ButtonMethod.MAIN_MENU));


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
		
		

		g.drawImage(Background.WIN.getImage(), 0, 0, M_WIDTH, M_HEIGHT, null);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
		
		
		g.drawString("Trinnorica", (M_WIDTH/2) - (g.getFontMetrics().stringWidth("Trinnorica")/2), 55);

		
		g.setFont(new Font(Font.SANS_SERIF, Font.TYPE1_FONT, 15));
		g.drawString("Authors & Developers:", M_WIDTH/2 - (g.getFontMetrics().stringWidth("Authors & Developers:")/2), 100);
		String a = "Cameron Witcher, Joseph Phillips";
		g.drawString(a, M_WIDTH/2 - (g.getFontMetrics().stringWidth(a)/2), 120);

		for (Clickable clickable : clickables) {
			g.drawPolygon(clickable.drawPolygon(g));
		}
		
		if ( mouse )g.drawImage(Texture.loadTexture("pointer.png"), mx, my, this);
		
		
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	private class TAdapter extends KeyAdapter {
		
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
