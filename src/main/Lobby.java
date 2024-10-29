package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game.STATE;

public class Lobby {

	public static int slimeBoss = 0;
	private BufferedImage slime, skeleton, dragon, orc, treasure;
	
	public void getImages() {
		try {
			if(Game.gameState == STATE.Lobby) {
				slime = ImageIO.read(getClass().getResourceAsStream("/lobby/slimeEntrance.png"));
			}else {
				slime = ImageIO.read(getClass().getResourceAsStream("/lobby/slimeExit.png"));
			}
			treasure = ImageIO.read(getClass().getResourceAsStream("/items/chest.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics2D g2) {
		getImages();
		if(Game.gameState == STATE.Lobby) {
			g2.drawImage(slime, 0, 0, 96, 96, null);
			g2.setColor(Color.red);
			g2.drawRect(20, 10, 60, 64);
		}else {
			g2.drawImage(slime, Game.WIDTH/2 - 56, 100, 96, 96, null);
			g2.setColor(Color.red);
			g2.drawRect(Game.WIDTH/2 - 36, 100, 60, 64);
			
		}
		collision();
	}
	
	public void collision() {
		if(Game.gameState == STATE.Lobby) {
			if(Game.player.getBounds().intersects(new Rectangle(20, 10, 60, 64))) {
				Game.gameState = STATE.Slime;
			}
		}else {
			if(Game.player.getBounds().intersects(new Rectangle(Game.WIDTH/2 - 36, 100, 60, 64))) {
				Game.gameState = STATE.Lobby;
				Game.handler.clearEnemies(null);
				Game.handler.clearEnemies(null);
				Game.handler.clearEnemies(null);
				Game.handler.clearEnemies(null);
				Game.player.setHP(Game.player.getMaxHP());
				slimeBoss = 0;
				Spawner.slimesKilled = 0;
				Spawner.slimeBoss = false;
			}
			if(Game.player.getBounds().intersects(new Rectangle(Game.WIDTH/2 - 36, 400, 60, 64))) {
				Scores.gold += 1 + Scores.slimeBossKilled;
			}
		}
	}
	
}
