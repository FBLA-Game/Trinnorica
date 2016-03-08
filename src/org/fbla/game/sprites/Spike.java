package org.fbla.game.sprites;

import org.fbla.game.spriteutils.Sprite;
import org.fbla.game.spriteutils.SpriteType;

public class Spike extends Sprite {

    public Spike(int x, int y) {
        super(x, y);
        
        init();
    }
    
    @Override
    public SpriteType getType(){
    	return SpriteType.SPIKE;
    }

    private void init() {
        
        loadImage("objects/spike.png");
        getImageDimensions();
    }
}