package org.fbla.game.sprites;

import org.fbla.game.spriteutils.Money;
import org.fbla.game.spriteutils.Sprite;
import org.fbla.game.spriteutils.SpriteType;

public class Gold extends Sprite implements Money {
	
	int value;

    public Gold(int x, int y) {
        super(x, y);
        value = 10;
        initFloor();
    }
    
    @Override
    public SpriteType getType(){
    	return SpriteType.GOLD;
    }

    private void initFloor() {
        
        loadImage("objects/gold.gif");
        getImageDimensions();
    }

	@Override
	public int getValue() {
		return value;
	}
	

}