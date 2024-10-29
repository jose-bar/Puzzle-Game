package attacks;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game;
import main.GameObject;
import main.ID;

public class PaladinQ extends GameObject{

	
	public PaladinQ(int x, int y, ID id) {
		super(x, y, id);
		
		key = "paladinQ";
		damage = 2;
		animation = 0;
		casted = false;
		
		getAttackImage();
	}
	
	public void getAttackImage() {
		try {
			f1 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinQ-1.png"));
			f2 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinQ-2.png"));
			f3 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinQ-3.png"));
			f4 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinQ-4.png"));
			f5 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinQ-5.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		
		Game.player.setCooldownQ(Game.player.getCooldownQ() - 1);
		cooldown--;
		animation--;
		if (animation < 1) {
			casted = false; 
		}
		if(Game.player.getCooldownQ() < 1) {
			Game.handler.removeObject(this);
		}
		x = Game.player.getX() - 24;
		y = Game.player.getY();
	}

	public void render(Graphics2D g2) {
		getAttackImage();
		BufferedImage image = null;
		
		switch(animation) {
		case 1,2,3,4,5,6,7,8,9,91,92,93,94,95,96,97,98,99:
			image = f6;
			break;
		case 10,11,12,13,14,15,16,17,18,82,83,84,85,86,87,88,89,90,100,101,102,103,104,105,106,107,108,172,173,174,175,176,177,178,179,180:
			image = f5;
			break;
		case 19,20,21,22,23,24,25,26,27,73,74,75,76,77,78,79,80,81,109,110,111,112,113,114,115,116,117,163,164,165,166,167,168,169,170,171:
			image = f4;
			break;
		case 28,29,30,31,32,33,34,35,36,64,65,66,67,68,69,70,71,72,118,119,120,121,122,123,124,125,126,154,155,156,157,158,159,160,161,162:
			image = f3;
			break;
		case 37,38,39,40,41,42,43,44,45,55,56,57,58,59,60,61,62,63,127,128,129,130,131,132,133,134,135,145,146,147,148,149,150,151,152,153:
			image = f2;
		case 46,47,48,49,50,51,52,53,54,136,137,138,139,140,141,142,143,144:
			image = f1;
		}
	
		g2.drawImage(image, (int)x + 20, (int)y, 64, 64, null);
		
	}

	public Rectangle getBounds() {
		return new Rectangle();
	}

	public void special(GameObject object) {
		Game.player.setITime(Game.player.getITime() + 180);
	}

}
