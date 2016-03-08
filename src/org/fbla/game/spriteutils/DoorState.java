package org.fbla.game.spriteutils;

public enum DoorState {
	
	OPEN("open"),
	CLOSED("closed");
	
	String state;
	
	DoorState(String state){
		this.state = state;
	}

}
