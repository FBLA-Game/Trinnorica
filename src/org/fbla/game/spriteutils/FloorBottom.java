package org.fbla.game.spriteutils;

import java.awt.Color;

public enum FloorBottom {
	
	STONE("#3D3D3D"),
	BLUE_STONE("#373496"),
	RED_STONE("#721F15"),
	DIRT("#562B0D"),
	GRASS("#005600");
	
	String color;
	
	private FloorBottom(String color) {
		this.color = color;
	}
	
	public Color getColor(){
		return Color.decode(color);
	}
	
	public String getHex(){
		return color;
	}

	public static FloorBottom getColorFromType(int type) {
		FloorBottom floor = STONE;
		switch(type){
		case 0:
			floor = STONE;
			break;
		case 1:
			floor = BLUE_STONE;
			break;
		case 2:
			floor = RED_STONE;
			break;
		case 3:
			floor = GRASS;
			break;
		default:
			floor = DIRT;
			break;
			
		}
		
		
		return floor;
	}

}
