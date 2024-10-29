package enemies;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.Game;
import main.GameObject;
import main.Handler;
import main.ID;

public class MagicTracker extends GameObject{

	Handler handler;
	Random r;
	
	public MagicTracker(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		
		damage = 4;
		maxHP = 1000;
		HP = 1000;
		speed = 3;
		getEnemyImage();
	}

	public void getEnemyImage() {
		try {
			f1 = ImageIO.read(getClass().getResourceAsStream("/enemyAttacks/magicTracker-1.png"));
			f2 = ImageIO.read(getClass().getResourceAsStream("/enemyAttacks/magicTracker-2.png"));
			f3 = ImageIO.read(getClass().getResourceAsStream("/enemyAttacks/magicTracker-3.png"));
			f4 = ImageIO.read(getClass().getResourceAsStream("/enemyAttacks/magicTracker-4.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		if(iTime > 0) {
			iTime--;
		}
		HP --;
		x += velX * 2;
		y += velY * 2;
		
		float diffX = x - Game.player.getX() - 8;
		float diffY = y - Game.player.getY() - 8;
		float distance = (float) Math.sqrt((x - Game.player.getX())*(x-Game.player.getX()) + (y - Game.player.getY()) * (y - Game.player.getY()));
		
		velX = (float) ((-1.0 / distance) * diffX);
		velY = (float) ((-1.0 / distance) * diffY);
		
		spriteCounter++;
		if(spriteCounter > 25) {
			if (spriteNum == 1) {
				spriteNum = 2;
			}else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
		if(HP <= 0) {
			System.out.println("should be dead");
			handler.removeObject(this);
		}
	}

	public void render(Graphics2D g2) {
		BufferedImage image = null;
		
		if(spriteNum == 1) {
			image = f2;
		}else {
			image = f1;
		}
		if(iTime != 0 && iTime%2 == 0) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
		}
		g2.drawImage(image, (int)x, (int)y, 16, 16, null);
		int alpha = 127; // 50% transparent
		Color myColour = new Color(255, 0, 0, alpha);
		g2.setColor(myColour);
		g2.fillRect((int)x + 2, (int)y + 5, 16, 16);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x + 2, (int)y + 5, 16, 16);
	}

	public void special(GameObject object) {
		
	}

}
