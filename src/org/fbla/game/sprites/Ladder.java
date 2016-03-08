
package org.fbla.game.sprites;

import org.fbla.game.spriteutils.Sprite;
import org.fbla.game.spriteutils.SpriteType;

public class Ladder extends Sprite {

    public Ladder(int x, int y) {
        super(x, y);
        
        initLadder();
    }
    
    @Override
    public SpriteType getType(){
    	return SpriteType.LADDER;
    }

    private void initLadder() {
        
        loadImage("objects/ladder.png");
        getImageDimensions();
    }
}