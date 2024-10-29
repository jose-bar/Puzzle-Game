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

public class RogueE extends GameObject{

	
	public RogueE(int x, int y, ID id) {
		super(x, y, id);
		
		key = "paladinE";
		damage = 3;
		animation = 0;
		casted = false;
		speed = 8;
		
		getAttackImage();
	}

	public void adjust() {
		
	}
	
	public void getAttackImage() {
		if(direction.equals("right")) {
			try {
				f1 = ImageIO.read(getClass().getResourceAsStream("/attacks/rogueE-right1.png"));
				f2 = ImageIO.read(getClass().getResourceAsStream("/attacks/rogueE-right2.png"));
			}catch(IOException e) {
				e.printStackTrace();
			}
		}else if(direction.equals("left")) {
			try {
				f1 = ImageIO.read(getClass().getResourceAsStream("/attacks/rogueE-left1.png"));
				f2 = ImageIO.read(getClass().getResourceAsStream("/attacks/rogueE-left2.png"));
			}catch(IOException e) {
				e.printStackTrace();
			}
		}else if(direction.equals("up")) {
			try {
				f1 = ImageIO.read(getClass().getResourceAsStream("/attacks/rogueE-up1.png"));
				f2 = ImageIO.read(getClass().getResourceAsStream("/attacks/rogueE-up2.png"));
			}catch(IOException e) {
				e.printStackTrace();
			}
		}else if(direction.equals("down")) {
			try {
				f1 = ImageIO.read(getClass().getResourceAsStream("/attacks/rogueE-down1.png"));
				f2 = ImageIO.read(getClass().getResourceAsStream("/attacks/rogueE-down2.png"));
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void tick() {
		
		cooldown--;
		Game.player.setCooldownE(Game.player.getCooldownE() - 1);
		if(direction.equals("up")) {
			y -= speed;
		}
		if(direction.equals("down")) {
			y += speed;
		}
		if(direction.equals("left")) {
			x -= speed;
		}
		if(direction.equals("right")) {
			x += speed;
		}
		if(Game.player.getCooldownE() < 1) {
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
	}

	public void render(Graphics2D g2) {
		getAttackImage();
		BufferedImage image = null;
		
		if(spriteNum == 1)image = f1;
		if(spriteNum == 2)image = f2;
		
		g2.drawImage(image, (int)x + 20,(int) y, 48, 48, null);
		
		int alpha = 127; // 50% transparent
		Color myColour = new Color(255, 0, 0, alpha);
		g2.setColor(myColour);
		
		if(direction.equals("up")) g2.drawRect((int)x + 32,(int) y + 3, 26, 58);
		else if(direction.equals("down")) g2.drawRect((int)x + 32, (int)y , 26, 54);
		else if (direction.equals("right")) g2.drawRect((int)x + 20, (int)y + 7, 58, 28);
		else g2.drawRect((int)x + 20, (int)y + 5, 58, 28);
		
	}

	public Rectangle getBounds() {
		if(direction.equals("up")) return new Rectangle((int)x + 32,(int) y + 3, 26, 58);
		else if(direction.equals("down")) return new Rectangle((int)x + 32, (int)y , 26, 54);
		else if (direction.equals("right")) return new Rectangle((int)x + 20, (int)y + 7, 58, 28);
		else return new Rectangle((int)x + 20, (int)y + 5, 58, 28);
	}

	public void special(GameObject enemy) {

		if(enemy.hasEffect("poison") < 0){
			enemy.addEffect(enemy.getX(), enemy.getY(), "poison", 399);
			System.out.println("Scuesssssssssssssss!");
		}else {
			int index = enemy.hasEffect("poison");
			enemy.effects.get(index).setDurration(enemy.effects.get(index).getDurtion() + 100);
			System.out.println("Failureeeeeeeeeeee");
		}
		
	}

}
