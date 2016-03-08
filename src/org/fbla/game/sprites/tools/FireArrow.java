package org.fbla.game.sprites.tools;

import org.fbla.game.spriteutils.Entity;
import org.fbla.game.spriteutils.SpriteType;
import org.fbla.game.utils.Direction;

public class FireArrow extends Arrow  {
	


	public FireArrow(int x, int y, Direction direction, Entity shooter) {
        super(x, y, direction, shooter);
        this.direction = direction;
        this.dx = 6;
        damage = 20;
        init();
    }
    @Override
    public SpriteType getType(){
    	return SpriteType.ARROW;
    	
    }

    private void init() {
        loadImage("tools/arrow_red.png");
        setImageDimensions(16, 4, 0, 0);
    }	
}