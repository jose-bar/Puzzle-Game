package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HUD {
	
	public int maxHP = Player.MaxHP;
	private int greenValue = 255;
	private int timer = 0;
	private int potions = 2;
	
	public void tick() {
		timer ++;
		if(timer > 15)greenValue = Game.player.getHP() * 5;
		greenValue = (int) Game.clamp(greenValue,1,255);
	}
	
	public void render(Graphics2D g2) {
		g2.setColor(Color.gray);
		g2.fillRect(15, Game.HEIGHT - 100, 100, 16);
		g2.setColor(new Color(75, greenValue, 0));
		if(timer > 15)g2.fillRect(15, Game.HEIGHT - 100, Game.player.getHP() * 5, 16);
		g2.setColor(Color.white);
		g2.drawRect(15, Game.HEIGHT - 100, maxHP * 5, 16);
		if(timer > 15)g2.drawString("HP = "+Game.player.getHP()+"/"+Game.player.getMaxHP(), 15, Game.HEIGHT - 60);
		g2.drawString("Frames = "+Game.fps, 15, Game.HEIGHT / 5);
		g2.drawString("Boss Kills = "+Scores.slimesKilled, 15, Game.HEIGHT / 5 - 90);
		BufferedImage image = null;
		
		if(timer > 15) {
			if(Game.player.role.equals("paladin")) {
				if (Game.player.getCooldownE() > 0) {
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
				}
				try {
					image = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-up6.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g2.drawImage(image, 120, Game.HEIGHT - 100, 32, 32, null);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				if (Game.player.getCooldownQ() > 0) {
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
				}
				try {
					image = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinQ-3.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g2.drawImage(image, 145, Game.HEIGHT - 110, 48, 48, null);
			}else if(Game.player.role.equals("rogue")) {
				if (Game.player.getCooldownE() > 0) {
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
				}
				try {
					image = ImageIO.read(getClass().getResourceAsStream("/attacks/rogueE-right1.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g2.drawImage(image, 120, Game.HEIGHT - 100, 32, 32, null);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				if (Game.player.getCooldownQ() > 0) {
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
				}
				try {
					image = ImageIO.read(getClass().getResourceAsStream("/attacks/rogueQ-1.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g2.drawImage(image, 145, Game.HEIGHT - 110, 48, 48, null);
			}
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		
		g2.drawString(potions+"   x ", Game.WIDTH-140, Game.HEIGHT - 80);
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/items/HealthPotion.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g2.drawImage(image, Game.WIDTH-120, Game.HEIGHT - 110, 36, 48, null);
		g2.drawString("Slimes Killed: " + Spawner.slimesKilled, 15, 84);
	}
	
	public void SetTimer(int score) {
		this.timer = score;
	}
	public int getTimer() {
		return timer;
	}
	public int getPotions() {
		return potions;
	}
	public void setPotions(int potions) {
		this.potions = potions;
	}
	
}
