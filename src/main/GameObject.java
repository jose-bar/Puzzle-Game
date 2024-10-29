package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import statusEffects.Poison;
import statusEffects.StatusEffect;

public abstract class GameObject {

	public ArrayList<StatusEffect> effects = new ArrayList<StatusEffect>();
	
	protected Color poison = new Color(75, 0, 130, 100);
	
	protected float x, y;
	protected ID id;
	protected int maxHP;
	protected int HP;
	protected int timer;
	protected float velX;
	protected float velY;
	protected float speed;
	protected int iTime = 0;
	protected BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
	protected BufferedImage f1, f2, f3, f4, f5, f6;
	protected String direction = "down";
	protected int spriteCounter = 0;
	protected int spriteNum = 1;
	protected int damage;
	protected int cooldownE;
	protected int cooldownQ;
	protected int animation;
	protected boolean casted;
	protected String key;
	protected int cooldown;
	private boolean stop = false;
	protected boolean summoned = false;
	
	public GameObject(float x, float y, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public abstract void special(GameObject object);
	public abstract void tick();
	public abstract void render(Graphics2D g2);
	public abstract Rectangle getBounds();
	
	public static void tint(BufferedImage image, Color color) {
	    for (int x = 0; x < image.getWidth(); x++) {
	        for (int y = 0; y < image.getHeight(); y++) {
	            Color pixelColor = new Color(image.getRGB(x, y), true);
	            int r = (2 * pixelColor.getRed() + color.getRed()) / 3;
	            int g = (2 * pixelColor.getGreen() + color.getGreen()) / 3;
	            int b = (2 * pixelColor.getBlue() + color.getBlue()) / 3;
	            int a = pixelColor.getAlpha();
	            int rgba = (a << 24) | (r << 16) | (g << 8) | b;
	            image.setRGB(x, y, rgba);
	        }
	    }
	}
	
	public void setHP(int hp) {
		this.HP = hp;
	}
	public int getHP() {
		return HP;
	}
	public void setMaxHP(int hp) {
		this.maxHP = hp;
	}
	public int getMaxHP() {
		return maxHP;
	}
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
	public void setID(ID id) {
		this.id = id;
	}
	public ID getID() {
		return id;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public float getSpeed() {
		return speed;
	}
	public void setVelX(float velX) {
		this.velX = velX;
	}
	public float getVelX() {
		return velX;
	}
	public void setVelY(float velY) {
		this.velY = velY;
	}
	public float getVelY() {
		return velY;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getDamage() {
		return damage;
	}
	public int getCooldownE() {
		return cooldownE;
	}
	public void setCooldownE(int cooldown) {
		this.cooldownE = cooldown;
	}
	public int getCooldown() {
		return cooldown;
	}
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	public int getCooldownQ() {
		return cooldownQ;
	}
	public void setCooldownQ(int cooldown) {
		this.cooldownQ = cooldown;
	}
	public boolean getCasted() {
		return casted;
	}
	public void setCasted(boolean casted) {
		this.casted = casted;
	}
	public void setAnimation(int animation) {
		this.animation = animation;
	}
	public String getName() {
		return key;
	}
	public void setITime(int iTime) {
		this.iTime = iTime;
	}
	public int getITime() {
		return iTime;
	}
	public void setTimer(int timer) {
		this.timer = timer;
	}
	public int getTimer() {
		return timer;
	}
	public boolean getStop() {
		return stop;
	}
	public void setStop(boolean stop) {
		this.stop = stop;
	}
	public boolean getSummoned() {
		return summoned;
	}
	public void setSummoned(boolean summoned) {
		this.summoned = summoned;
	}
	public void addEffect(float x, float y, String type, int timer) {
		if (type.equals("poison")) {
			effects.add(new Poison(y, y, type, timer));
		}
	}
	public int getEffectSize() {
		return effects.size();
	}
	
	public int hasEffect(String type) {
		int index = -1;
		
		for(int i = 0; getEffectSize() > i; i++) {
			if(effects.get(i).getType().equals(type)){
				index = i;
				break;
			}
		}
		
		return index;
	}
	
	public void removeStatusEffect(String type) {
		for(int i = 0; getEffectSize() > i; i++) {
			if(effects.get(i).getType().equals(type)){
				effects.remove(i);
			}
		}
	}
	
}
