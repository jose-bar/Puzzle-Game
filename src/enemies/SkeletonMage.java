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
import main.Player;

public class SkeletonMage extends GameObject{

	Handler handler;
	Random r;
	
	int nextMove = 360;
	int move;
	
	public SkeletonMage(int x, int y, ID id, Handler handler, Random r) {
		super(x, y, id);
		
		this.handler = handler;
		this.r = r;
		
		damage = 3;
		maxHP = 8;
		HP = 8;
		speed = 0;
		velX = velY = speed;
		getEnemyImage();
	}

	public void getEnemyImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/skeletons/skeletonMage-1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/skeletons/skeletonMage-2.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		nextMove--;
		if(nextMove == 0) {
			nextMove = 360;
			special(this);
		}
		if(iTime > 0) {
			iTime--;
		}
		
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
			if(Game.player.role.equals("paladin"))Player.HP++;
			handler.removeObject(this);
		}
		//collision();
	}

	public void render(Graphics2D g2) {
		BufferedImage image = null;
		
		if(spriteNum == 1) {
			image = up1;
		}else {
			image = up2;
		}
		if(iTime != 0 && iTime%2 == 0) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
		}
		g2.drawImage(image, (int)x, (int)y, 48, 48, null);
		int alpha = 127; // 50% transparent
		Color myColour = new Color(255, 0, 0, alpha);
		g2.setColor(myColour);
		//g2.fillRect((int)x + 4, (int)y + 5, 36, 42);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x + 4, (int)y + 5, 36, 42);
	}

	public void special(GameObject object) {
		handler.addObject(new MagicTracker((int)x, (int)y, ID.EnemyProjectile, handler));
	}

}
