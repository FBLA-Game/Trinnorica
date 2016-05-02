package org.fbla.game.sprites;

import java.util.ArrayList;

import org.fbla.game.spriteutils.Interactable;
import org.fbla.game.spriteutils.Rotation;
import org.fbla.game.spriteutils.Sprite;
import org.fbla.game.spriteutils.SpriteType;
import org.fbla.game.spriteutils.Tool;
import org.fbla.game.utils.Images;
import org.fbla.game.utils.InteractionMethod;

import res.Texture;

public class Switch extends Interactable {
	
	Sprite sprite;
	Sprite[] sprites;
	Rotation rotation;
	SpriteType type;
	SwitchType stype;
	boolean triggered = false;
	private boolean multiple = false;
	private InteractionMethod method;

	public Switch(int x, int y, Sprite sprite, ArrayList<Sprite> level, Rotation rotation, InteractionMethod method,SwitchType stype) {
        super(x, y);
        type = SpriteType.SWITCH;
        this.sprite = sprite;
        level.add(sprite);
        this.method = method;
        this.rotation = rotation;
        this.stype = stype;
        init();
    }
	
	public Switch(int x, int y, Sprite[] sprites, ArrayList<Sprite> level, Rotation rotation, InteractionMethod method,SwitchType stype) {
        super(x, y);
        type = SpriteType.SWITCH;
        this.sprites = sprites;
        for(Sprite sprite : sprites)
        	level.add(sprite);
        this.rotation = rotation;
        multiple = true;
        this.method = method;
        this.stype = stype;
        init();
    }
    
    @Override
    public SpriteType getType(){
    	return type;
    }

    private void init() {
        
        loadImage(Images.rotate(Texture.loadTexture("objects/switch_" + stype + ".png"), rotation.getRotation()));
        getImageDimensions();
    }
    
    
    @Override
    public void interact(Tool tool){
    	interact();
    }
    @Override
    public void interact(){
    	if(!multiple) 
    		method.interact(sprite);
    	 else 
    		for(Sprite sprite : sprites) method.interact(sprite);
    	
    	if(triggered){
    		if(rotation.equals(Rotation.LEFT)) x = x-5;
    		if(rotation.equals(Rotation.UP)) y = y-5;
    		triggered = false;
    		loadImage(Images.rotate(Texture.loadTexture("objects/switch_" + stype + ".png"), rotation.getRotation()));
        	getImageDimensions();
        	type = SpriteType.SWITCH;
        	return;
    		
    	} else {
    		if(rotation.equals(Rotation.LEFT)) x = x+5;
    		if(rotation.equals(Rotation.UP)) y = y+5;

    		loadImage(Images.rotate(Texture.loadTexture("objects/triggered-switch_" + stype + ".png"), rotation.getRotation()));
        	getImageDimensions();
        	type = SpriteType.TRIGGERED_SWITCH;
        	triggered = true;
    	}
    	
    }

	public Object getSwitchType() {
		return stype;
	}
}