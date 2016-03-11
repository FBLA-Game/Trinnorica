package org.fbla.game.sprites;

import java.util.Timer;
import java.util.TimerTask;

import org.fbla.game.spriteutils.Entity;
import org.fbla.game.spriteutils.FloorBottom;
import org.fbla.game.spriteutils.Moveable;
import org.fbla.game.spriteutils.SpriteType;

public class FallingFloor extends Entity implements Moveable {
	
	public static int GRAY_STONE = 0;
	public static int BLUE_STONE = 1;
	public static int RED_STONE = 2;
	public static int GRASS = 3;
	boolean t = false;
	
	
	private int type;
	private FloorBottom bottom = FloorBottom.getColorFromType(0);
	
	public FallingFloor(int x, int y, int type) {
        super(x, y);
        this.type = type;
        bottom = FloorBottom.getColorFromType(type);
        initFloor();
    }
	
	public FallingFloor(int x, int y, int type, FloorBottom back) {
        super(x, y);
        this.type = type;
        bottom = back;
        initFloor();
    }
	
	public FloorBottom getBackgroundType(){
		return bottom;
	}
    
    @Override
    public SpriteType getType(){
    	return SpriteType.FALLING_FLOOR;
    }

    private void initFloor() {
    	switch(type){
    	case 0:
    		loadImage("objects/floor/gray_stone.png");
    		break;
    	case 1:
    		loadImage("objects/floor/blue_stone.png");
    		break;
    	case 2:
    		loadImage("objects/floor/red_stone.png");
    		break;
    	case 3:
    		loadImage("objects/floor/grass.png");
    		break;
    	default:
    		loadImage("objects/floor/gray_stone.png");
    		break;
    	}
        
        
        getImageDimensions();
    }

	public int getFloorType() {
		return type;
	}

	public void startFalling(){
    	Timer timer = new Timer();
    	timer.schedule( new TimerTask() {
    		@Override
    		public void run() {
    			t = true;
    			}
    		}, 500);
    	}

	@Override
	public void move() {
		
		if(t){
			y = y+2;
		}
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