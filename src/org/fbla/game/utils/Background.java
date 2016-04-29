package org.fbla.game.utils;

import java.awt.Image;

import res.Texture;

public enum Background {
	SKY("sky-background.png"),
	HELL("hell-background.gif"),
	TEST("test-background.gif"),
	WIN("win-background.png"),
	ENTERING_CAVE("cave_entry-background.png"),
	CAVE("cave-background.png"),
	GAMEOVER("gameover-background.png"), 
	DARKEN("darken.png"), 
	MENU("menu-background.png");
	
	String background;
	
	Background(String background){
		this.background = background;
	}
	
	public Image getImage(){
		return Texture.loadTexture("backgrounds/" + background);
	}
	
	
}
