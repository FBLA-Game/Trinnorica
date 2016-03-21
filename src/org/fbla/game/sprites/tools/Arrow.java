package org.fbla.game.sprites.tools;

import java.awt.Polygon;

import org.fbla.game.spriteutils.Entity;
import org.fbla.game.spriteutils.Projectile;
import org.fbla.game.spriteutils.SpriteType;
import org.fbla.game.utils.Direction;

public class Arrow extends Projectile  {



	
        private int speed = 1;

	public Arrow(int x, int y, Direction direction, Entity shooter) {
        super(x, y, shooter, false);
        this.direction = direction;
        this.dx = 10;
        damage = 10;
        init();
    }
	public Arrow(int x, int y, int dx, int dy, Entity shooter) {
        super(x, y, shooter, true);
      //  this.dx = 1;
  //      this.dy = Math.tan(Math.atan(dy/dx));
//        this.dx = (dx-shooter.x)/30;
//        this.dy = (dy-shooter.y)/30;
        this.dx = speed * Math.cos(Math.toRadians(Math.tan((shooter.y - dy) /(shooter.x - dx))));
        this.dx = speed * Math.sin(Math.toRadians(Math.tan((shooter.y-dy)/(shooter.x-dx))));
        
        damage = 10;
        init();
    }
    @Override
    public SpriteType getType(){
    	return SpriteType.ARROW;
    	
    }

    private void init() {
    	width = 15;
    	height = 5;
//        loadImage("tools/arrow.png");
//        getImageDimensions();
    }	
    
    
    
}
