package org.fbla.game.sprites.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import org.fbla.game.Bridge;
import org.fbla.game.spriteutils.Entity;
import org.fbla.game.spriteutils.Projectile;
import org.fbla.game.spriteutils.SpriteType;
import org.fbla.game.utils.Direction;
import org.fbla.game.utils.Utils;

public class Arrow extends Projectile  {



	
        private double speed = 10;
        private double a;

	public Arrow(int x, int y, Direction direction, Entity shooter) {
        super(x, y, shooter, false);
        this.direction = direction;
        this.dx = 10;
        damage = 10;
        init();
    }
	public Arrow(int x, int y, int dx, int dy, Entity shooter) {
        super(x, y, shooter, true);
        a = Math.atan((dy-(double) (y))/(dx-(double) (x)));
      //  this.dx = 1;
  //      this.dy = Math.tan(Math.atan(dy/dx));
//        this.dx = (dx-shooter.x)/30;
//        this.dy = (dy-shooter.y)/30;
        
        
        a = a *100;
        if(a<-90.0){
        	a = Math.atan(((double) (y)-dy)/((double) (x)-dx));
            a = a *100;
        }
        
        this.dx = speed * Math.cos(Math.toRadians(a));
        this.dy = speed * Math.sin(Math.toRadians(a));
        
        Utils.broadcastMessage("A: " + a + "\nDX: " + this.dx + "\nDY: " + this.dy);
        
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
	public void draw(Graphics g) {
		Polygon p = new Polygon(new int[] {1,2,3,4}, new int[] {1,2,3,4}, 4);
		Point pp;
//		sprite.width = (int) sprite.getImage().getWidth(this);
//		sprite.height = (int) sprite.getImage().getHeight(this);
		p = getPolygon();
//		p.reset();
		for(int i=0;i!=getPolygon().npoints;i++){
			pp = Bridge.rotatePoint(
					new Point(bounds.xpoints[i],bounds.ypoints[i]), //Original Point
					new Point((int)bounds.getBounds().getCenterX(), (int)bounds.getBounds().getCenterY()), // Center Point
					Math.toDegrees(Math.atan(dy/dx))
					);
			
			p.xpoints[i] = (int) pp.getX();
			p.ypoints[i] = (int) pp.getY();
			
		}
		g.setColor(Color.decode("#671D04"));
		g.fillPolygon(p);
		bounds = p;
		p=null;
		
		
		
	}	
    
    
    
}
