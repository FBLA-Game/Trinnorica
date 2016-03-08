package org.fbla.game.sprites.tools;

import org.fbla.game.spriteutils.SpriteType;
import org.fbla.game.spriteutils.Tool;
import org.fbla.game.utils.Images;

public class Key extends Tool {
	

	private int id;
	
	public Key(int x, int y, int id) {
		super(x, y);
		this.id = id;
		init();
		
		if(id==-1){
			loadImage("tools/magic_key.png");
		}
	}
	
	public void init(){
		loadImage("tools/key.png");
		setImageDimensions(17, 17,0,0);
		Images.colorKey(getImage(),this);
	}
	
	@Override
	public SpriteType getType(){
		return SpriteType.KEY;
	}
	
	@Override
	public void use(){
		//Code to open door
		
	}

	public int getID() {
		return id;
	}
	

}
