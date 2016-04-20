package org.fbla.game.sprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import org.fbla.game.Bridge;
import org.fbla.game.boards.GameBoard;
import org.fbla.game.spriteutils.Sprite;
import org.fbla.game.spriteutils.SpriteType;
import org.fbla.game.spriteutils.State;
import org.fbla.game.utils.Images;

public class Wall extends Sprite {
	
	State state;
	int size;
	int spina = 0;
	int spinb = 0;
	public String color;
	public static String BLUE_WALL = "8075FF";
	public static String BLACK_WALL = "212121";
	public boolean spin;

	public Wall(int x, int y, int size, State state, String color) {
        super(x, y);
        this.state = state;
        this.size = size;
        this.color = color;
        init();
    }
	
	public Wall(int x, int y, int size, State state) {
        super(x, y);
        this.state = state;
        this.size = size;
        this.color = "8075FF";
        init();
    }
	
	public Wall(int x, int y, int size, State state, boolean spin) {
        super(x, y);
        this.state = state;
        this.size = size;
        this.color = "8075FF";
        this.spin = spin;
        init();
    }
	
	public Wall(int x, int y, int size, State state,boolean invisible, String color) {
        super(x, y);
        this.state = state;
        this.size = size;
        this.color = color;
        if(invisible)initInvisible();
        else init();
    }
//	public Wall(int x, int y, int size, State state,boolean invisible) {
//        super(x, y);
//        this.state = state;
//        this.size = size;
//        this.color = "8075FF";
//        if(invisible)initInvisible();
//        else init();
//    }
    
    @Override
    public SpriteType getType(){
    	return SpriteType.WALL;
    }

    private void init() {
    	
    	switch(state){
    	
    	case VERTICAL:
    		loadImage(Images.createColorImage("#" + color));
    		height = size;
    		setImageDimensions(7, size);
    		break;
    
    		
    	case HORIZONTAL:
    		loadImage(Images.createColorImage("#" + color));
    		setImageDimensions(size, 7);
    		width = size;
    		break;
    		default:
    			loadImage(Images.createColorImage("#" + color));
    			break;
    	}
    }
    
    private void initInvisible() {
    	
    	switch(state){
    	case VERTICAL:
    		loadImage("empty.png");
    		height = size;
    		setImageDimensions(7, size);
    		break;
    	case LARGE_VERTICAL:
    		loadImage("empty.png");
    		break;
    		
    	case HORIZONTAL:
    		loadImage("empty.png");
    		setImageDimensions(size, 7);
    		width = size;
    		break;
    		default:
    			loadImage("empty.png");
    			break;
    	}
    }

	
}