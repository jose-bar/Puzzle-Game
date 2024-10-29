package enemies;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game;
import main.GameObject;
import main.Handler;
import main.ID;
import main.Player;

public class FireTrail extends GameObject{

	Handler handler;
	
	public FireTrail(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		maxHP = 450;
		HP = 450;
		
		damage = 1;
		getEnemyImage();
	}
	
	public void getEnemyImage() {
		try {
			f1 = ImageIO.read(getClass().getResourceAsStream("/enemyAttacks/fireTrail-1.png"));
			f2 = ImageIO.read(getClass().getResourceAsStream("/enemyAttacks/fireTrail-2.png"));
			f3 = ImageIO.read(getClass().getResourceAsStream("/enemyAttacks/fireTrail-3.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void special(GameObject object) {
		//handler.addObject.
	}

	public void tick() {
		HP--;
		
		if(iTime > 0) {
			iTime--;
		}
		
		spriteCounter++;
		if(spriteCounter > 10) {
			if (spriteNum == 1) {
				spriteNum = 2;
			}else if (spriteNum == 2) {
				spriteNum = 3;
			}else if (spriteNum == 3) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
		
		if(HP <= 0) {
			handler.removeObject(this);
		}
		
		x = Game.clamp((int)x, 0, Game.WIDTH - 60);
		y = Game.clamp((int)y, 0, Game.HEIGHT - 60);
	}

	public void render(Graphics2D g2) {
		BufferedImage image = null;
		
		if(spriteNum == 1) {
			image = f1;
		}else if(spriteNum == 2){
			image = f2;
		}else {
			image = f3;
		}
		if(iTime != 0 && iTime%2 == 0) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
		}
		g2.drawImage(image, (int)x, (int)y, 48, 48, null);
		int alpha = 127; // 50% transparent
		Color myColour = new Color(255, 0, 0, alpha);
		g2.setColor(myColour);
		g2.drawRect((int)x + 4, (int)y + 5, 36, 42);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x + 2, (int)y + 5, 32, 35);
	}

}
