package org.fbla.game.sprites;

import org.fbla.game.spriteutils.FloorBottom;
import org.fbla.game.spriteutils.Sprite;
import org.fbla.game.spriteutils.SpriteType;

public class Floor extends Sprite {
	
	public static int GRAY_STONE = 0;
	public static int BLUE_STONE = 1;
	public static int RED_STONE = 2;
	public static int GRASS = 3;
	
	
	private int type;
	private FloorBottom bottom = FloorBottom.getColorFromType(0);
	
	public Floor(int x, int y, int type) {
        super(x, y);
        this.type = type;
        bottom = FloorBottom.getColorFromType(type);
        initFloor();
    }
	
	public Floor(int x, int y, int type, FloorBottom back) {
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
    	return SpriteType.FLOOR;
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
}