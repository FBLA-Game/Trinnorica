package org.fbla.game.utils;

import java.util.ArrayList;
import java.util.Random;

public enum DamageReason {
	VOID("You fell out of the world!:You're a clumsy one, aren't you?:Oh. The world ends right there..."),
	ENEMY("You were killed by an enemy.:They got you.. Try again?:Ouch. Beat to death."),
	PROJECTILE("You were shot!:You took an arrow to the knee! I think.."),
	BAD_THINGS("You probably stepped on a spike. Painful!:Ouch. Looks like you stepped on something painful!"),
	RANDOM("You probably stepped on a spike. Painful!:Ouch. Looks like you stepped on something painful!:You probably stepped on a spike. Painful!:Ouch. Looks like you stepped on something painful!:You were shot!:You took an arrow to the knee! I think..:You were killed by an enemy.:They got you.. Try again?:Ouch. Beat to death.:You fell out of the world!:You're a clumsy one, aren't you?:Oh. The world ends right there...");
	
	String reason;
	
	DamageReason(String reason){
		this.reason = reason;
	}
	
	public String getMessage(){
		ArrayList<String> ms = new ArrayList<>();
		for(String m : reason.split(":"))
			ms.add(m);
		return ms.get(new Random().nextInt(ms.size()));
				
	}

}
