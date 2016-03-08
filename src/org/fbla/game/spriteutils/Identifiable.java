package org.fbla.game.spriteutils;

public class Identifiable extends Tool {
	
	int id;

	public Identifiable(int x, int y, int ID) {
		super(x, y);
		this.id = ID;
		// TODO Auto-generated constructor stub
	}
	
	public int getID(){
		return id;
	}

}
