package org.fbla.game.sprites.tools;

import org.fbla.game.Bridge;
import org.fbla.game.boards.GameBoard;
import org.fbla.game.sprites.Player;
import org.fbla.game.spriteutils.SpriteType;
import org.fbla.game.spriteutils.Weapon;
import org.fbla.game.utils.Sound;

import res.Audio;

public class Bow extends Weapon {
	

	public Bow(int x, int y) {
		super(x, y);
		init();
	}
	
	public void init(){
		damage = 10;
		loadImage("tools/bow.png");
		setImageDimensions(16, 16,0,0);
	}
	
	@Override
	public SpriteType getType(){
		return SpriteType.BOW;
	}
	
	@Override
	public void use(){
		Audio.playSound(Sound.BOW_SHOOT);
		Arrow arrow = new Arrow(
				getEntity().x, 
				getEntity().y,
				getEntity().getFacingDirection(),
				getEntity());
		arrow.direction = getEntity().getFacingDirection();
		
		if(getEntity() instanceof Player){
			arrow = new Arrow(
					getEntity().x,
					getEntity().y,
					((GameBoard) Bridge.getGame().board).mx,
					((GameBoard) Bridge.getGame().board).my,
					getEntity());
		}
		((GameBoard)Bridge.getGame().getBoard()).addMoveable(arrow);
		 
	}
	

}
