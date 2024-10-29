package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game.STATE;

public class Player extends GameObject{

	KeyInput keyI;
	Handler handler;
	
	public static int MaxHP = 20;
	public static int HP = MaxHP;
	public String role;
	
	public Player(int x, int y, ID id, KeyInput keyI, Handler handler, String role) {
		super(x, y, id);
		
		this.role = role;
		this.handler = handler;
		this.keyI = keyI;
		cooldownE = 0;
		cooldownQ = 0;
		if(role.equals("paladin")) {
			speed = 3;
		}else {
			speed = 4;
		}
		MaxHP = 20;
		HP = 20;
		getPlayerImage();
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x + 16, (int)y + 12, 30, 36);
	}
	
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/"+role+"-back1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/"+role+"-back2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/"+role+"-right1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/"+role+"-right2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/"+role+"-left1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/"+role+"-left2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/"+role+"-front1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/"+role+"-front2.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		
		HP = (int) Game.clamp(HP, 0, MaxHP);
		
		if(Game.player.getHP() < 1) {
			if(Game.gameState == STATE.Slime) {
				Spawner.slimeBoss = false;
				Spawner.slimesKilled = 0;
			}
			Game.gameState = STATE.End;
		}
		
		if(!getStop()) {
			if(iTime > 0) {
				iTime--;
			}
			if(KeyInput.upPressed == true) {
				direction = "up";
				y -= speed;
			}
			if(KeyInput.downPressed == true) {
				direction = "down";
				y += speed;
			}
			if(KeyInput.leftPressed == true) {
				direction = "left";
				x -= speed;
			}
			if(KeyInput.rightPressed == true) {
				direction = "right";
				x += speed;
			}
		}
		
		x = Game.clamp((int)x, 0, Game.WIDTH - 60);
		y = Game.clamp((int)y, 0, Game.HEIGHT - 90);
		
		
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
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if(spriteNum == 1)image = up1;
			if(spriteNum == 2)image = up2;
			break;
		case "down":
			if(spriteNum == 1)image = down1;
			if(spriteNum == 2)image = down2;
			break;
		case "left":
			if(spriteNum == 1)image = left1;
			if(spriteNum == 2)image = left2;
			break;
		case "right":
			if(spriteNum == 1)image = right1;
			if(spriteNum == 2)image = right2;
			break;
		}
		if(iTime%2 == 0 && iTime != 0) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
		}
		g2.drawImage(image,(int) x, (int)y, 48, 48, null);
		int alpha = 127; // 50% transparent
		Color myColour = new Color(255, 0, 0, alpha);
		g2.setColor(myColour);
		g2.drawRect((int)x + 16,(int) y + 12, 30, 36);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
	}

	@Override
	public void special(GameObject object) {
		// TODO Auto-generated method stub
		getPlayerImage();
	}

	
}
