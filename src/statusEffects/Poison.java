package statusEffects;

import java.awt.Graphics2D;

import main.GameObject;

public class Poison extends StatusEffect{

	public Poison(float x, float y, String type, int duration) {
		super(x, y, type, duration);
		
	}

	@Override
	public void special(GameObject object) {
		
		object.setHP(object.getHP() - 1);
		System.out.println("POSION HURTS");
		
	}

	@Override
	public void tick(GameObject object) {

		duration--;
		
		if (duration % 200 == 0 && duration > -1) {
			special(object);
		}
		
		if(duration < 0) {
			object.removeStatusEffect(type);
		}
		
	}

	@Override
	public void render(Graphics2D g2) {

		
		
	}
	
	public boolean show() {
		return duration % 200 == 0;
	}

}
