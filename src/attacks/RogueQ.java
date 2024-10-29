package attacks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game;
import main.GameObject;
import main.ID;

public class RogueQ extends GameObject{
	
	public RogueQ(int x, int y, ID id) {
		super(x, y, id);
		
		key = "rogueQ";
		damage = 5;
		animation = 0;
		casted = false;
		
		getAttackImage();
	}

	public void adjust() {
		
	}
	
	public void getAttackImage() {
		try {
			f1 = ImageIO.read(getClass().getResourceAsStream("/attacks/rogueQ-1.png"));
			f2 = ImageIO.read(getClass().getResourceAsStream("/attacks/rogueQ-2.png"));
			f3 = ImageIO.read(getClass().getResourceAsStream("/attacks/rogueQ-3.png"));
			f4 = ImageIO.read(getClass().getResourceAsStream("/attacks/rogueQ-4.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		
		cooldown--;
		Game.player.setCooldownQ(Game.player.getCooldownQ() - 1);
		animation-=2;
		if (animation < 1) {
			casted = false;
		}
		if(Game.player.getCooldownQ() < 1) {
			Game.handler.removeObject(this);
		}
		
		spriteCounter++;
		if(spriteCounter > 8) {
			if (spriteNum == 1) {
				spriteNum = 2;
			}else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
		x = Game.clamp((int)x, 0, Game.WIDTH - 60);
		y = Game.clamp((int)y, 0, Game.HEIGHT - 90);
	}

	public void render(Graphics2D g2) {
		BufferedImage image = null;
		
		if(spriteNum == 1) {
			if(animation < 35) {
				image = f3;
			}else {
				image = f1;
			}
		}else {
			if(animation < 35) {
				image = f4;
			}else {
				image = f2;
			}
		}
		
		if(animation >= 35) {
			g2.drawImage(image, (int)x ,(int) y, 64, 64, null);
		}else {
			g2.drawImage(image, (int)x-32 ,(int) y-32, 128, 128, null);
		}
		
		int alpha = 127; // 50% transparent
		Color myColour = new Color(255, 0, 0, alpha);
		g2.setColor(myColour);
		if(animation < 35)g2.drawRect((int) x-32, (int)y-32, 128, 128);
		
	}

	public Rectangle getBounds() {
		if (animation < 35 && animation > -2) {
			return new Rectangle((int) x-32, (int)y, 128, 128);
		}else {
			return new Rectangle();		
		}
	}
	
	@Override
	public void special(GameObject object) {
		// TODO Auto-generated method stub
		
	}

}
