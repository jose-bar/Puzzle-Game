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

public class SlimeBallAttackSmall extends GameObject{

	float playerX, playerY;
	float ogX, ogY;
	String color = "green";
	Handler handler;
	Random r;
	
	public SlimeBallAttackSmall(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		
		damage = 3;
		maxHP = 500;
		HP = 500;
		speed = 3;
		getEnemyImage();
		special(this);
	}

	public void getEnemyImage() {
		try {
			if(color.equals("green")) {
				f1 = ImageIO.read(getClass().getResourceAsStream("/enemyAttacks/slimeBall-1.png"));
				f2 = ImageIO.read(getClass().getResourceAsStream("/enemyAttacks/slimeBall-2.png"));
				f3 = ImageIO.read(getClass().getResourceAsStream("/enemyAttacks/slimeBall-3.png"));
			}else {
				f1 = ImageIO.read(getClass().getResourceAsStream("/enemyAttacks/magictracker-1.png"));
				f2 = ImageIO.read(getClass().getResourceAsStream("/enemyAttacks/magicTracker-2.png"));
				f3 = ImageIO.read(getClass().getResourceAsStream("/enemyAttacks/magicTracker-3.png"));
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		if(iTime > 0) {
			iTime--;
		}
		int vx = (int) ((playerX)- ogX);
	    int vy = (int) ((playerY)- ogY);

	    double distance = Math.sqrt((vx * vx) + (vy * vy));

	    double doubleX = ((vx / distance));
	    double doubleY = ((vy / distance));

	    x += doubleX * 4;
	    y += doubleY * 4;
		
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
		if(HP <= 0 || x < - 128 || x > Game.WIDTH + 32 || y < - 128 || y > Game.HEIGHT + 32) {
			handler.removeObject(this);
		}
	}

	public void render(Graphics2D g2) {
		BufferedImage image = null;
		
		if(spriteNum == 1) {
			image = f1;
		}else if(spriteNum == 2) {
			image = f2;
		}else if(spriteNum == 3){
			image = f3;
		}
		if(iTime != 0 && iTime%2 == 0) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
		}
		if(color.equals("green")) g2.drawImage(image, (int)x, (int)y, 80, 80, null);
		else g2.drawImage(image, (int)x + 26, (int)y + 26, 28, 28, null);
		int alpha = 127; // 50% transparent
		Color myColour = new Color(255, 0, 0, alpha);
		g2.setColor(myColour);
		g2.drawRect((int)x + 26, (int)y + 26, 26, 26);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x + 26, (int) y + 26, 26, 26);
	}
	
	public void setColor(String color) {
		if(color.equals("blue")) {
			damage = 2;
			this.color = color;
		}
		getEnemyImage();
	}
	
	@Override
	public void special(GameObject object) {
		playerX = Game.player.getX() - 32;
		playerY = Game.player.getY();
		ogX = x;
		ogY = y;

	}

}
