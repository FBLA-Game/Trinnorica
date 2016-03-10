package org.fbla.game.sprites.tools;

import java.awt.Polygon;

import org.fbla.game.spriteutils.Entity;
import org.fbla.game.spriteutils.Projectile;
import org.fbla.game.spriteutils.SpriteType;
import org.fbla.game.utils.Direction;

public class Arrow extends Projectile  {
	


	public Arrow(int x, int y, Direction direction, Entity shooter) {
        super(x, y, shooter, false);
        this.direction = direction;
        this.dx = 10;
        damage = 10;
        init();
    }
	public Arrow(int x, int y, int dx, int dy, Entity shooter) {
        super(x, y, shooter, true);
        this.dx = (dx-shooter.x)/30;
        this.dy = (dy-shooter.y)/30;
        damage = 10;
        init();
    }
    @Override
    public SpriteType getType(){
    	return SpriteType.ARROW;
    	
    }

    private void init() {
        loadImage("tools/arrow.png");
        setImageDimensions(15, 5, 0, 0);
    }	
    
    
    
}