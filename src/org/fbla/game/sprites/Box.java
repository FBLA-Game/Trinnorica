package org.fbla.game.sprites;

import org.fbla.game.Bridge;
import org.fbla.game.boards.GameBoard;
import org.fbla.game.spriteutils.Entity;
import org.fbla.game.spriteutils.Moveable;
import org.fbla.game.spriteutils.Sprite;
import org.fbla.game.spriteutils.SpriteSubType;
import org.fbla.game.spriteutils.SpriteType;
import org.fbla.game.utils.Images;
import org.fbla.game.utils.Utils;

public class Box extends Entity implements Moveable {
	
	public int dx;
	public int dy;
	public boolean onground = false;
	
	
	public Box(int x, int y) {
        super(x, y);
        init();
    }
	
	
	
    
    @Override
    public SpriteType getType(){
    	return SpriteType.BOX;
    }

    private void init() {
    	loadImage(Images.createColorImage("#FFFFFF"));
    	setImageDimensions(10, 10);
    }

	

	

	@Override
	public void move() {
		
		for (Sprite sprite : ((GameBoard)Bridge.getGame().getBoard()).sprites) {
			if(sprite instanceof Player) continue;
			if (!bounds.intersects(sprite.getPolygon().getBounds()))continue;
			if(!Utils.intersects(bounds, sprite.getPolygon())) continue;
			if(sprite.getType().getSubType().equals(SpriteSubType.PROJECTILE)) continue;
			
			if(sprite.getSubType().equals(SpriteSubType.COLLIDEABLE)){
				onground = true;
			}
		}
		if(onground)dy=0; else
		dy = 3;
		
		x=x+dx;
		y=y+dy;
	}


	@Override
	public void disable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enable() {
		// TODO Auto-generated method stub
		
	}
}