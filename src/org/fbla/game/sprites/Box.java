package org.fbla.game.sprites;

import java.awt.Color;
import java.awt.Graphics;

import org.fbla.game.Bridge;
import org.fbla.game.boards.GameBoard;
import org.fbla.game.spriteutils.Entity;
import org.fbla.game.spriteutils.Moveable;
import org.fbla.game.spriteutils.Sprite;
import org.fbla.game.spriteutils.SpriteSubType;
import org.fbla.game.spriteutils.SpriteType;
import org.fbla.game.utils.DamageReason;
import org.fbla.game.utils.Images;
import org.fbla.game.utils.Sound;
import org.fbla.game.utils.Utils;

import res.Audio;

public class Box extends Entity implements Moveable {
	
	public double dx;
	public double dy;
	public boolean onground = false;
	private boolean jumping = false;
	private boolean falling = false;
	
	
	public Box(int x, int y) {
        super(x, y);
        init();
    }
	
	
	
    
    @Override
    public SpriteType getType(){
    	return SpriteType.BOX;
    }

    private void init() {
    	loadImage(Images.createColorImage("#000000"));
    	setImageDimensions(30, 30);
    }

	

	

	@Override
	public void move() {
		
		for (Sprite sprite : ((GameBoard)Bridge.getGame().getBoard()).sprites) {
			if(sprite instanceof Player) continue;
			if (!bounds.intersects(sprite.getPolygon().getBounds()))continue;
			if(!Utils.intersects(bounds, sprite.getPolygon())) continue;
			if(sprite.getType().getSubType().equals(SpriteSubType.PROJECTILE)) continue;
			
			onground = false;
			
			if (sprite.getSubType().equals(SpriteSubType.PARTIAL_COLLIDEABLE)) {
				switch(getIntercectingDirection(sprite.getPolygon().getBounds())){
				case DOWN:
					onground = true;
					jumping = false;
					y = sprite.y-getHeight();
					break;
				default:
					onground = false;
					break;
				}
			}
			
			
			if(sprite.getSubType().equals(SpriteSubType.COLLIDEABLE) || sprite.getSubType().equals(SpriteSubType.INTERACTABLE)){
				
				
				switch(getIntercectingDirection(sprite.getPolygon().getBounds())){
				case DOWN:
					onground = true;
					jumping = false;
					y = sprite.y-getHeight();
					break;
				case UP:
					break;
					default:
						onground = false;
						break;
				}
			}
		}
		
		if(dy > 0)
			falling = true;
		else falling = false;
		
		if(onground) falling = false;
		
		if(jumping){
			dy = dy+0.1;
		}
		else
		if(onground)dy=0; else
		dy = 3;
		
		x=(int) (x+dx);
		y=(int) (y+dy);
	}


	@Override
	public void disable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enable() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void drawInfo(int x, int y, Graphics g) {
		String name = getType().name();
		GameBoard.drawOutlineString(name, x, y, g, Color.WHITE, Color.BLACK);
		GameBoard.drawOutlineString(jumping + "", x, y+g.getFontMetrics().getHeight(), g, Color.WHITE, Color.BLACK);
		GameBoard.drawOutlineString(onground + "", x, y+g.getFontMetrics().getHeight()*2, g, Color.WHITE, Color.BLACK);
		
	}

	public void bounce(Player player) {
		if(falling || onground){

			jumping = true;
			onground = false;
			dy = -3;
			y=y-4;
			if(dx == 0)
				dx = player.dx;
			else dx = dx*-1;
		
		}
	}
}