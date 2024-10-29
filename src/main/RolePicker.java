package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game.STATE;

public class RolePicker {
	
	private int spriteCounter = 0;
	private int spriteNum = 1;
	private BufferedImage paladin1;	
	private BufferedImage paladin2;
	private BufferedImage rogue1;
	private BufferedImage rogue2;
	private int choice = 1;

	RolePicker(){
		
		try {
			paladin1 = ImageIO.read(getClass().getResourceAsStream("/player/paladin-front1.png"));
			paladin2 = ImageIO.read(getClass().getResourceAsStream("/player/paladin-front2.png"));
			rogue1 = ImageIO.read(getClass().getResourceAsStream("/player/rogue-front1.png"));
			rogue2 = ImageIO.read(getClass().getResourceAsStream("/player/rogue-front2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void Tick() {
		if(KeyInput.rightPressed) {
			choice = 2;
		}
		if(KeyInput.leftPressed) {
			choice = 1;
		}
		
		if(KeyInput.spacePressed) {
			if(choice == 1) {
				Game.player.role = "rogue";
			}else {
				Game.player.role = "paladin";
			}
			Game.player.getPlayerImage();
			Game.gameState = STATE.Lobby;
		}
		
		spriteCounter++;
		if(spriteCounter > 13) {
			if (spriteNum == 1) {
				spriteNum = 2;
			}else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}
	
	public void render(Graphics2D g2) {
		BufferedImage image1 = null;
		BufferedImage image2 = null;
		//BufferedImage image3 = null;
		
		if(spriteNum == 1) {
			image1 = paladin1;
			image2 = rogue1;
		}
		if(spriteNum == 2) {
			image1 = paladin2;
			image2 = rogue2;
		}
		g2.drawImage(image1,(int) Game.WIDTH/5 * 3, (int)Game.HEIGHT/4, 48, 48, null);
		g2.drawImage(image2,(int) Game.WIDTH/4, (int)Game.HEIGHT/4, 48, 48, null);
		g2.setColor(Color.white);
		if(choice == 1) g2.drawRect((int)Game.WIDTH/4 - 16, (int)Game.HEIGHT/4 - 16, 80, 116);
		else g2.drawRect((int)Game.WIDTH/5 * 3 - 16, (int)Game.HEIGHT/4 - 16, 80, 116);
		g2.drawString("Rogue", Game.WIDTH/4 + 6, Game.HEIGHT/4 + 70);
		g2.drawString("Paladin", Game.WIDTH/5 * 3 + 2, Game.HEIGHT/4 + 70);
	}

}
