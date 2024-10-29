package statusEffects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GameObject;

public abstract class StatusEffect {

	protected float x, y;
	protected BufferedImage f1, f2, f3, f4, f5, f6;
	protected int spriteCounter = 0;
	protected int spriteNum = 1;
	protected int duration = 0;
	protected String type = "neutral";
	
	public StatusEffect(float x, float y, String type, int duration) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.duration = duration;
	}
	
	public abstract void special(GameObject object);
	public abstract void tick(GameObject object);
	public abstract void render(Graphics2D g2);
	
	public void setX(float f) {
		this.x = f;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setDurration(int duration) {
		this.duration = duration;
	}
	public int getDurtion() {
		return duration;
	}
}
