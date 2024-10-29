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

public class OrangeSlime extends GameObject{

	Handler handler;
	Random r;
	
	int nextMove = 16;
	int move;
	
	public OrangeSlime(int x, int y, ID id, Handler handler, Random r) {
		super(x, y, id);
		
		this.handler = handler;
		this.r = r;
		
		damage = 1;
		maxHP = 5;
		HP = 5;
		speed = 1;
		velX = velY = speed;
		getEnemyImage();
	}

	public void getEnemyImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/slimes/orange-1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/slimes/orange-2.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		timer++;
		if(timer >= 52) {
			special(this);
			timer = 0;
		}
		nextMove--;
		if(nextMove == 0) {
			nextMove = 16;
			move = r.nextInt(2);
		}
		if(move == 1) {
			y += velY;
		}else {
			x += velX;
		}
		
		if(iTime > 0) {
			iTime--;
		}
		
		if(y <= 0 || y >= Game.HEIGHT - 96) {
			velY *= -1;
		}
		if(x <= 0 || x >= Game.WIDTH - 48) {
			velX *= -1;
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
			if (!summoned) {
				special(this);
			}
			if(Game.player.role.equals("paladin"))Player.HP++;
			handler.removeObject(this);
		}
	}

	public void render(Graphics2D g2) {
		BufferedImage image = null;
		
		if(spriteNum == 1) {
			image = up1;
		}else {
			image = up2;
		}
		
		if(hasEffect("poison") >= 0 && HP > 0) {
			if(effects.get(hasEffect("poison")).getDurtion() > 0 && effects.get(hasEffect("poison")).getDurtion() % 10 == 0) tint(image, poison);
		}
		
		if(iTime != 0 && iTime%2 == 0) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
		}
		if(!summoned) {
			g2.drawImage(image,(int) x, (int)y, 48, 48, null);
		}else {
			g2.drawImage(image, (int)x, (int)y, 32, 32, null);
		}
		int alpha = 127; // 50% transparent
		Color myColour = new Color(255, 0, 0, alpha);
		g2.setColor(myColour);
		if(!summoned) {
			g2.drawRect((int)x + 2, (int)y + 5, 44, 35);
		}else {
			g2.drawRect((int)x + 2,(int) y + 5, 32, 32);
		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

	public Rectangle getBounds() {
		if(!summoned) {
			return new Rectangle((int)x + 2, (int)y + 5, 32, 35);
		}else {
			return new Rectangle((int)x + 2,(int) y + 8, 28, 28);
		}
	}

	public void special(GameObject object) {
		if(HP <= 0) {
			OrangeSlime greenSlimeSmall = new OrangeSlime((int)x + 20,(int) y + 20, ID.Slime, handler, r);
			greenSlimeSmall.setMaxHP(3);
			greenSlimeSmall.setHP(3);
			greenSlimeSmall.setVelY(2);
			greenSlimeSmall.setITime(30);
			greenSlimeSmall.setSummoned(true);
			OrangeSlime greenSlimeSmall2 = new OrangeSlime((int)x - 20,(int) y - 20, ID.Slime, handler, r);
			greenSlimeSmall2.setMaxHP(3);
			greenSlimeSmall2.setHP(3);
			greenSlimeSmall2.setVelX(2);
			greenSlimeSmall2.setITime(30);
			greenSlimeSmall2.setSummoned(true);
			handler.addObject(greenSlimeSmall);
			handler.addObject(greenSlimeSmall2);
		}else {
			if(move == 1) {
				if(velX > 0)handler.addObject(new FireTrail((int)x-32, (int)y, ID.Hazard, handler));
				else if (velX < 0)handler.addObject(new FireTrail((int)x+32, (int)y, ID.Hazard, handler));;
			}else {
				if(velY > 0)handler.addObject(new FireTrail((int)x, (int)y-32, ID.Hazard, handler));
				else if (velY < 0)handler.addObject(new FireTrail((int)x, (int)y+32, ID.Hazard, handler));;
			}
		}
	}
	

}
