package attacks;

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

public class PaladinE extends GameObject{
	
	public PaladinE(int x, int y, ID id) {
		super(x, y, id);
		
		key = "paladinE";
		damage = 3;
		animation = 0;
		casted = false;
		
		getAttackImage();
	}

	public void adjust() {
		
	}
	
	public void getAttackImage() {
		if(direction.equals("right")) {
			try {
				f1 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-right1.png"));
				f2 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-right2.png"));
				f3 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-right3.png"));
				f4 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-right4.png"));
				f5 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-right5.png"));
				f6 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-right6.png"));
			}catch(IOException e) {
				e.printStackTrace();
			}
		}else if(direction.equals("left")) {
			try {
				f1 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-left1.png"));
				f2 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-left2.png"));
				f3 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-left3.png"));
				f4 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-left4.png"));
				f5 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-left5.png"));
				f6 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-left6.png"));
			}catch(IOException e) {
				e.printStackTrace();
			}
		}else if(direction.equals("up")) {
			try {
				f1 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-up1.png"));
				f2 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-up2.png"));
				f3 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-up3.png"));
				f4 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-up4.png"));
				f5 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-up5.png"));
				f6 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-up6.png"));
			}catch(IOException e) {
				e.printStackTrace();
			}
		}else if(direction.equals("down")) {
			try {
				f1 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-down1.png"));
				f2 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-down2.png"));
				f3 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-down3.png"));
				f4 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-down4.png"));
				f5 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-down5.png"));
				f6 = ImageIO.read(getClass().getResourceAsStream("/attacks/paladinE-down6.png"));
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void tick() {
		
		cooldown--;
		Game.player.setCooldownE(Game.player.getCooldownE() - 1);
		animation-=2;
		if (animation < 1) {
			casted = false;
		}
		if(Game.player.getCooldownE() < 1) {
			Game.handler.removeObject(this);
		}
		setX(Game.player.getX());
		setY(Game.player.getY());
		if(direction.equals("up")) {
			y -= 28;
			x -= 20;
		}
		if(direction.equals("down")) {
			y += 22;
			x -= 28;
		}
		if(direction.equals("left")) {
			y -= 8;
			x -= 48;
		}
		if(direction.equals("right")) {
			y -= 4;
			x -= 8;
		}

	}

	public void render(Graphics2D g2) {
		getAttackImage();
		BufferedImage image = null;
		
		switch(animation) {
		case 1,2,3,4,5,6,7:
			image = f6;
			break;
		case 8,9,10,11,12,13,14:
			image = f5;
			break;
		case 15,16,17,18,19,20,21:
			image = f4;
			break;
		case 22,23,24,25,26,27:
			image = f3;
			break;
		case 28,29,30,31,32,33:
			image = f2;
		case 34,35,36,37,38,39,40:
			image = f1;
		}
	
		g2.drawImage(image, (int)x + 20,(int) y, 64, 64, null);
		
		int alpha = 127; // 50% transparent
		Color myColour = new Color(255, 0, 0, alpha);
		g2.setColor(myColour);
		if(direction.equals("up")) g2.drawRect((int)x + 16,(int) y , 64, 40);
		else if(direction.equals("down")) g2.drawRect((int)x + 22, (int)y + 28, 64, 40);
		else if (direction.equals("right")) g2.drawRect((int)x + 50, (int)y + 5, 36, 64);
		else g2.drawRect((int)x + 22, (int)y + 5, 36, 64);
		
	}

	public Rectangle getBounds() {
		if (animation + 10 > 0) {
			if(direction.equals("up")) return new Rectangle((int)x + 16, (int)y , 64, 60);
			else if(direction.equals("down")) return new Rectangle((int)x + 22, (int)y + 28, 64, 40);
			else if (direction.equals("right")) return new Rectangle((int)x + 50,(int) y + 5, 36, 64);
			else return new Rectangle((int)x + 22, (int)y + 5, 36, 64);
		}else {
			return new Rectangle();		
		}
	}
	
	@Override
	public void special(GameObject object) {
		// TODO Auto-generated method stub
		
	}

}
