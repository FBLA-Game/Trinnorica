package org.fbla.game.sprites;

import org.fbla.game.spriteutils.Interactable;
import org.fbla.game.spriteutils.Interaction;
import org.fbla.game.spriteutils.SpriteType;
import org.fbla.game.spriteutils.State;

import res.Texture;

public class FakeInteractable extends Interactable {

	
	private SpriteType type;
	private State state;
	private Interaction interaction;
	
	public FakeInteractable(int x, int y, SpriteType type, State state, Interaction interaction) {
		super(x, y);
		this.type = type;
		this.state = state;
		init();
		this.interaction = interaction;
	}
	
	

	public void init(){
		switch(type){
		case WALL:
			loadImage(Texture.loadTexture("wall/" + state.toString().toLowerCase() + ".png"));
			getImageDimensions();
			break;
		case ARROW:
			loadImage("tools/arrow.png");
			getImageDimensions();
		default:
			break;
		}
	}
	public SpriteType getType(){
		return type;
	}
	
	@Override
	public void interact() {
		interaction.run();
	}

}
