package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import attacks.PaladinE;
import attacks.PaladinQ;
import attacks.RogueE;
import main.Game.STATE;

public class KeyInput implements KeyListener{
	
	public static boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;
	
	int x, y;
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP)upPressed = true;
		if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)downPressed = true;
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)leftPressed = true;
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)rightPressed = true;
		if(key == KeyEvent.VK_E)attackE();
		if(key == KeyEvent.VK_Q)attackQ();
		if(key == KeyEvent.VK_SPACE) {
			spacePressed = true;
			if(Game.gameState != STATE.Select) {
				if(Game.hud.getPotions() > 0) {
					Game.hud.setPotions(Game.hud.getPotions() - 1);
					Game.player.setHP(Game.player.getHP() + Game.player.getMaxHP()/2);
					Game.checkFull();
				}
			}
		}
		if(key == KeyEvent.VK_R)Game.player.setHP(Game.player.getHP() - 2);		
	}

	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP)upPressed = false;
		if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN)downPressed = false;
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT)leftPressed = false;
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT)rightPressed = false;
		if(key == KeyEvent.VK_SPACE)spacePressed = false;
		
	}
	
	public void attackE() {
		if(Game.player.getCooldownE() < 1) {
			if(Game.player.role.equals("paladin")) {
				Game.paladinE.setCasted(true);
				Game.handler.addObject(Game.paladinE);
				String direction = Game.player.getDirection();
				Game.paladinE.setDirection(direction);
				Game.paladinE.setCooldown(90);
				Game.player.setCooldownE(Game.paladinE.getCooldown());
				Game.paladinE.setX(Game.player.getX());
				Game.paladinE.setY(Game.player.getY());
				if(direction.equals("up")) {
					Game.paladinE.setY(Game.paladinE.getY()-28);
					Game.paladinE.setX(Game.paladinE.getX()-20);
				}
				if(direction.equals("down")) {
					Game.paladinE.setY(Game.paladinE.getY()+ 22);
					Game.paladinE.setX(Game.paladinE.getX() - 28);
				}
				if(direction.equals("left")) {
					Game.paladinE.setY(Game.paladinE.getY() - 8);
					Game.paladinE.setX(Game.paladinE.getX() - 48);
				}
				if(direction.equals("right")) {
					Game.paladinE.setY(Game.paladinE.getY() - 4);
					Game.paladinE.setX(Game.paladinE.getX() - 8);
				}
				Game.paladinE.setAnimation(40);
			}else if(Game.player.role.equals("rogue")) {
				Game.rogueE.setCasted(true);
				Game.handler.addObject(Game.rogueE);
				String direction = Game.player.getDirection();
				Game.rogueE.setDirection(direction);
				Game.rogueE.setCooldown(60);
				Game.player.setCooldownE(Game.rogueE.getCooldown());
				Game.rogueE.setX(Game.player.getX());
				Game.rogueE.setY(Game.player.getY());
				if(direction.equals("up")) {
					Game.rogueE.setY(Game.rogueE.getY()-28);
					Game.rogueE.setX(Game.rogueE.getX()-20);
				}
				if(direction.equals("down")) {
					Game.rogueE.setY(Game.rogueE.getY()+ 22);
					Game.rogueE.setX(Game.rogueE.getX() - 28);
				}
				if(direction.equals("left")) {
					Game.rogueE.setY(Game.rogueE.getY() - 8);
					Game.rogueE.setX(Game.rogueE.getX() - 48);
				}
				if(direction.equals("right")) {
					Game.rogueE.setY(Game.rogueE.getY() - 4);
					Game.rogueE.setX(Game.rogueE.getX() - 8);
				}
			}
		}
	}
	public void attackQ() {
		if(Game.player.getCooldownQ() < 1) {
			if(Game.player.role.equals("paladin")) {
				Game.paladinQ.setCasted(true);
				Game.handler.addObject(Game.paladinQ);
				String direction = Game.player.getDirection();
				Game.paladinQ.setDirection(direction);
				Game.paladinQ.setCooldown(360);
				Game.player.setCooldownQ(Game.paladinQ.getCooldown());
				Game.paladinQ.setX(Game.player.getX() - 24);
				Game.paladinQ.setY(Game.player.getY());
				Game.paladinQ.special(null);
				Game.paladinQ.setAnimation(Game.player.getITime());
			}else if(Game.player.role.equals("rogue")) {
				Game.rogueQ.setCasted(true);
				Game.handler.addObject(Game.rogueQ);
				String direction = Game.player.getDirection();
				Game.rogueQ.setCooldown(300);
				Game.player.setCooldownQ(Game.rogueQ.getCooldown());
				Game.rogueQ.setX(Game.player.getX());
				Game.rogueQ.setY(Game.player.getY());
				if(direction.equals("up")) {
					Game.rogueQ.setY(Game.rogueQ.getY() - 64);
					Game.rogueQ.setX(Game.rogueQ.getX() );
				}
				if(direction.equals("down")) {
					Game.rogueQ.setY(Game.rogueQ.getY() + 64);
					Game.rogueQ.setX(Game.rogueQ.getX() );
				}
				if(direction.equals("left")) {
					Game.rogueQ.setY(Game.rogueQ.getY() );
					Game.rogueQ.setX(Game.rogueQ.getX() - 64);
				}
				if(direction.equals("right")) {
					Game.rogueQ.setY(Game.rogueQ.getY() );
					Game.rogueQ.setX(Game.rogueQ.getX() + 64);
				}
				Game.rogueQ.setAnimation(300);
			}
		}
	}

}