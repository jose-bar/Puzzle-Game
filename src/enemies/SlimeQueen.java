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
import main.Lobby;
import main.Player;
import main.Scores;
import main.Spawner;
import main.Game.STATE;

public class SlimeQueen extends GameObject{

	Handler handler;
	Random r;
	
	BufferedImage[] queenJump = new BufferedImage[7];
	
	int frame;
	int phase;
	int move = 0;
	int lastMove = 0;
	int cycle;
	float playerY = 0;
	float playerX = 0;
	int attacks = 0;
	int extraTimer = 0;
	
	public SlimeQueen(int x, int y, ID id, Handler handler, Random r) {
		super(x, y, id);
		
		this.handler = handler;
		this.r = r;
		
		maxHP = 30 + Scores.slimeBossKilled;
		HP = maxHP;
		damage = 3 + (Scores.slimeBossKilled / 4);
		speed = 3 + (Scores.slimeBossKilled / 5);
		velX = velY = speed;
		getEnemyImage();
	}

	public void getEnemyImage() {
		try {
			f1 = ImageIO.read(getClass().getResourceAsStream("/slimes/queen1.png"));
			f2 = ImageIO.read(getClass().getResourceAsStream("/slimes/queen2.png"));
			f3 = ImageIO.read(getClass().getResourceAsStream("/slimes/queen3.png"));
			
			for(int i = 1; i <= queenJump.length; i++) {
				queenJump[i-1] = ImageIO.read(getClass().getResourceAsStream("/enemyAttacks/slimeQueenJump-"+i+".png"));
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tickEffect() {
		for(int i = 0; getEffectSize() > i; i++) {
			effects.get(i).tick(this);
		}
	}
	
	public void tick() {
		
		tickEffect();
		
		if(extraTimer < 80) {
			extraTimer++;
			y++;
		}
		
		if (move == 1 && phase != 2 && attacks < 3) {
			frame++;
			if(frame < 20 && cycle != 90) {
				y -= 3;
			}else{
				y += 3;
				
				if(frame > 40)frame = 0;
				if(cycle < 90)cycle++;
			}
			if(cycle == 90) {
				y -= 12;
				y -= 12;
			}
			if (y < 0 && (phase != 2 && phase != 3)) {
				phase = 2;
				timer = 0;
				special(this);
			}
		}
		
		if(move == 1 && phase == 2 && timer == 50) {
			phase = 3;
		}
		
		if (move == 1 && phase == 3 && attacks < 3) {
			if(y >= playerY-32) {
			phase = 0;
			cycle = 0;
			timer = 0;
			attacks++;
			}else {
				y+=20;
				y+=20;
			}
		}
		
		if(attacks == 3 && move == 1) {
			lastMove = 1;
			move = 0;
		}
		
		if(move == 2 && timer == 50 && attacks < 5 && cycle < 4) {
			special(this);
			attacks++;
			timer = 0;
			if(attacks > 4) {
				attacks = 0;
				frame = 0;
				cycle++;
			}
		}
		
		if(move == 2 && cycle == 3) {
			lastMove = 2;
			move = 0;
			cycle = 0;
		}
		
		if(move == 3 && timer >= 1000 && lastMove != 3) {
			lastMove = 3;
			cycle = 0;
			timer = 0;
		}
		
		timer++;
		if(timer == (200 - Scores.slimeBossKilled) && cycle == 0) {
			timer = 0;
			phase++;
			switch(lastMove) {
			case 0:
				move = 1;
				break;
			case 1:
				move = 2;
				cycle = 0;
				frame = 0;
				attacks = 0;
				spriteNum = 1;
				break;
			case 2:
				move = 3;
				velX = speed * 3;
				velY = speed;
				cycle = 1;
				break;
			case 3:
				move = 0;
				lastMove = 0;
				break;
			}
		}
		
		if(iTime > 0) {
			iTime--;
		}
		
		if(move == 3) {
			x += velX;
			y += velY;
		}
		
		if(y <= 0 || y >= Game.HEIGHT - 164) {
			velY *= -1;
		}
		if(x <= 0 || x >= Game.WIDTH - 128) {
			velX *= -1;
		}
		
		
		spriteCounter++;
		if((spriteCounter > 8 && move != 1)||(spriteCounter > 5 && move == 1)) {
			if (spriteNum == 1) {
				spriteNum = 2;
			}else if (spriteNum == 2) {
				spriteNum = 3;
			}else if (spriteNum == 3) {
				spriteNum = 4;
			}else if(spriteNum == 4){
				if(move != 1)spriteNum = 1;
				else spriteNum = 5;
			}else if(spriteNum == 5) {
				spriteNum = 6;
			}else if(spriteNum == 6) {
				spriteNum = 7;
			}else if(spriteNum == 7) {
				spriteNum = 8;
			}else if(spriteNum == 8) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
		
		if(HP <= 0) {
			System.out.println("should be dead");
			if(Game.player.role.equals("paladin"))Player.HP++;
			if(!summoned) {
				move = 5;
				special(this);
			}
			if(summoned) {
				Spawner.slimesKilled++;
				Lobby.slimeBoss++;
			}
			if(Lobby.slimeBoss%2 == 0 && Lobby.slimeBoss != 0) {
				Game.gameState = STATE.Clear;
				Scores.slimeBossKilled += 1;
			}
			handler.removeObject(this);
		}
		if(move == 3 || move == 1 || move == 0) {
			if(summoned) {
				x = Game.clamp((int)x, 0, Game.WIDTH - 80);
				y = Game.clamp((int)y, 0, Game.HEIGHT - 128);
			}else {
				x = Game.clamp((int)x, 0, Game.WIDTH - 128);
				y = Game.clamp((int)y, 0, Game.HEIGHT - 80);
			}
		}
	}

	public void render(Graphics2D g2) {
		if (move != 0 && move != 3) {
			try {
				renderAttack(g2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			renderIdle(g2);
		}
		
		int alpha = 127; // 50% transparent
		Color myColour = new Color(255, 0, 0, alpha);
		g2.setColor(myColour);
		if(!summoned)g2.drawRect((int) x + 8, (int)y + 16, 108, 100);
		else g2.drawRect((int) x, (int)y, 59, 59);
		
		g2.setColor(Color.gray);
		g2.fillRect((int) x - 8, (int) y - 16, maxHP * 5, 16);
		g2.setColor(Color.red);
		g2.fillRect((int) x - 8, (int) y - 16, HP * 5, 16);
		g2.setColor(Color.white);
		g2.drawRect((int) x - 8, (int) y - 16, maxHP * 5, 16);
		if(!summoned)g2.drawString("Bobbess, Countess of Gluttony",(int) x - 8, (int) y - 24);
		else g2.drawString("Bobbess of Gluttony",(int) x + 26, (int) y - 24);
	}

	public void renderAttack(Graphics2D g2) throws IOException{
		BufferedImage image = null;
		
		if(move == 1) {
			if(spriteNum == 1) {
				image = queenJump[0];
			}else if(spriteNum == 2 || spriteNum == 8){
				image = f1;
			}else if (spriteNum == 3 || spriteNum == 7){
				image = queenJump[1];
			}else if(spriteNum == 4 || spriteNum == 6) {
				image = f3;
			}else if(spriteNum == 5) {
				image = f2;
			}
		}else if(move == 2) {
			if(spriteNum == 1) {
				if(Game.player.getY() > y)image = ImageIO.read(getClass().getResourceAsStream("/slimes/queen1-shooting.png"));
				else image = ImageIO.read(getClass().getResourceAsStream("/slimes/queen1-back.png"));
			}else if(spriteNum == 2){
				if(Game.player.getY() > y)image = ImageIO.read(getClass().getResourceAsStream("/slimes/queen2-shooting.png"));
				else image = ImageIO.read(getClass().getResourceAsStream("/slimes/queen2-back.png"));
			}else if (spriteNum == 3){
				if(Game.player.getY() > y)image = ImageIO.read(getClass().getResourceAsStream("/slimes/queen3-shooting.png"));
				else image = ImageIO.read(getClass().getResourceAsStream("/slimes/queen3-back.png"));
			}else if(spriteNum == 4) {
				if(Game.player.getY() > y)image = ImageIO.read(getClass().getResourceAsStream("/slimes/queen2-shooting.png"));
				else image = ImageIO.read(getClass().getResourceAsStream("/slimes/queen2-back.png"));
			}
		}

		if(hasEffect("poison") >= 0 && HP > 0) {
			if(effects.get(hasEffect("poison")).getDurtion() >= 0 /** && effects.get(hasEffect("poison")).show() */) tint(image, poison);
		}
		
		if(iTime != 0 && iTime%2 == 0) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
		}
		if(phase != 2) {
			if(!summoned)g2.drawImage(image,(int) x, (int)y, 128, 128, null);
			else g2.drawImage(image,(int) x, (int)y, 64, 64, null);
		}
		else {
			g2.setColor(Color.gray);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
			g2.fillOval((int) playerX - 28, (int)playerY + 64, 156, 64);
		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
	}
	
	public void renderIdle(Graphics2D g2) {
		BufferedImage image = null;
		
		if(spriteNum == 1) {
			image = f1;
		}else if(spriteNum == 2){
			image = f3;
		}else if (spriteNum == 3){
			image = f2;
		}else if(spriteNum == 4) {
			image = f3;
		}
		
		if(hasEffect("poison") >= 0 && HP > 0) {
			if(effects.get(hasEffect("poison")).getDurtion() > 0 && effects.get(hasEffect("poison")).getDurtion() % 49 == 0) tint(image, poison);
		}
		
		if(iTime != 0 && iTime%2 == 0) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
		}
		if(!summoned)g2.drawImage(image,(int) x, (int)y, 128, 128, null);
		else g2.drawImage(image,(int) x, (int)y, 64, 64, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
	
	public Rectangle getBounds() {
		if( move != 1 || (move == 1 && (!((phase == 3 || y >= playerY-32) && cycle == 90)))) {
			if(!summoned) {
				return new Rectangle((int) x + 8, (int)y + 16, 108, 100);
			}else {
				return new Rectangle((int)x + 2, (int)y + 5, 59, 59);
			}
		}else {
			return new Rectangle();
		}
	}

	@Override
	public void special(GameObject object) {
		if(move == 1) {
			if (phase == 2) {
				Game.player.setStop(true);
				if(summoned) playerY = Game.player.getY() - 16;
				else playerY = Game.player.getY() - 64;
				if(summoned) {
					if(maxHP%2 == 0)playerX = x = Game.player.getX() - 32;
					else playerX = x = Game.player.getX() + 32;
				}
				if(!summoned)playerX = x = Game.player.getX() - 32;
				Game.player.setStop(false);
			}
		}else if(move == 2) {
			Game.player.setX(Game.player.getX() + 1);
			if(summoned) {
				handler.addObject(new SlimeBallAttackSmall((int) x, (int)y, ID.EnemyProjectile, handler));
			}else {
				handler.addObject(new SlimeBallAttack((int) x, (int)y, ID.EnemyProjectile, handler));
			}
		}else if(move == 5) {
			SlimeQueen split1 = new SlimeQueen((int)x - 32,(int) y, id, handler, r);
			split1.setMaxHP(maxHP/2);
			split1.setHP(maxHP/2);
			split1.setSpeed(speed + 1);
			split1.setITime(30);
			split1.setSummoned(true);
			SlimeQueen split2 = new SlimeQueen((int)x + 32,(int) y, id, handler, r);
			split2.setMaxHP(maxHP/2 + 1);
			split2.setHP(maxHP/2 + 1);
			split2.setSpeed(speed + 1);
			split2.setITime(30);
			split2.setSummoned(true);
			split2.setVelX(split2.getVelX() * -1);
			handler.addObject(split1);
			handler.addObject(split2);
		}
	}

}
