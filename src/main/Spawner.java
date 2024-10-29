package main;

import java.util.Random;

import enemies.BlueSlime;
import enemies.GreenSlime;
import enemies.OrangeSlime;
import enemies.RedSlime;
import enemies.SlimeQueen;
import main.Game.STATE;

public class Spawner {

	private Handler handler;
	private Random r;
	public static boolean slimeBoss = false;
	public static int slimesKilled = 0;
	private int timer = 0;
	
	public Spawner(Handler handler, HUD hud, Random r) {
		this.handler = handler;

		this.r = new Random();
	}

	public void tick() {
		timer++;
		if(Game.gameState == STATE.Slime) {
			if(slimesKilled < 10 && timer > (500 - Scores.slimeBossKilled * 5)) {
				timer = 0;
				int spawn = r.nextInt(4);
				if(spawn == 1)handler.addObject(new RedSlime(Game.WIDTH/4 + 10, Game.HEIGHT/4 + 10, ID.Slime, handler, r));
				else if(spawn == 2)handler.addObject(new GreenSlime(Game.WIDTH/4 + 10, Game.HEIGHT/4 + 10, ID.Slime, handler, r));
				else if(spawn == 3)handler.addObject(new BlueSlime(Game.WIDTH/4 + 10, Game.HEIGHT/4 + 10, ID.Slime, handler, r));
				else handler.addObject(new OrangeSlime(Game.WIDTH/4 + 10, Game.HEIGHT/4 + 10, ID.Slime, handler, r));
			}
			if(slimesKilled > 9 && !slimeBoss) {
				slimeBoss = true;
				handler.addObject(new SlimeQueen(Game.WIDTH/2 - 70, -20, ID.Slime, handler, r));
			}
		}
		
	}
	
}


