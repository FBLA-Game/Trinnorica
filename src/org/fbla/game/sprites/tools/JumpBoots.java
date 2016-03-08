package org.fbla.game.sprites.tools;

import org.fbla.game.Bridge;
import org.fbla.game.spriteutils.SpriteType;
import org.fbla.game.spriteutils.Tool;


public class JumpBoots extends Tool {
	

	public JumpBoots(int x, int y) {
		super(x, y);
		init();
	}
	
	public void init(){
		
		loadImage("tools/boots.png");
		setImageDimensions(16, 16,0,0);
	}
	
	@Override
	public SpriteType getType(){
		return SpriteType.BOOTS;
	}
	
	@Override
	public void use(){
		Bridge.getPlayer().toggleJumpBoost();
		 
	}
	

}
