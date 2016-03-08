package org.fbla.game.sprites.tools;

import org.fbla.game.spriteutils.Entity;
import org.fbla.game.spriteutils.Projectile;
import org.fbla.game.spriteutils.SpriteType;
import org.fbla.game.utils.Direction;

public class Arrow extends Projectile  {
	


	public Arrow(int x, int y, Direction direction, Entity shooter) {
        super(x, y, shooter, true);
        this.direction = direction;
        this.dx = 10;
        damage = 10;
        init();
    }
    @Override
    public SpriteType getType(){
    	return SpriteType.ARROW;
    	
    }

    private void init() {
        loadImage("tools/arrow.png");
        setImageDimensions(16, 4, 0, 0);
    }	
    
    
}