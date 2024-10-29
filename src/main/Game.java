package RPG_BulletHell.src.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.Random;

import RPG_BulletHell.src.attacks.PaladinE;
import RPG_BulletHell.src.attacks.PaladinQ;
import RPG_BulletHell.src.attacks.RogueE;
import RPG_BulletHell.src.attacks.RogueQ;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 3938370020331774607L;

	public static final int HEIGHT = 660, WIDTH = HEIGHT / 12 * 9;
	private Thread thread;
	private boolean running = false;
	
	private Random r;
	public static Handler handler;
	static HUD hud;
	private Spawner spawner;
	public static Player player;
	public static PaladinE paladinE;
	public static PaladinQ paladinQ;
	public static RogueE rogueE;
	public static RogueQ rogueQ;
	private KeyInput keyI;
	private RolePicker rolePicker;
	private WorldMaker worldMaker;
	private Lobby lobby;
	public static int fps;
	
	public enum STATE {
		Lobby,
		Select,
		Shop,
		Pause,
		End,
		Clear,
		Slime;  
	}
	
	public static STATE gameState = STATE.Select;
	
	public Game () {
		worldMaker = new WorldMaker(this);
		handler = new Handler();
		rolePicker = new RolePicker();
		keyI = new KeyInput();
		lobby = new Lobby();
		
		new Window(WIDTH, HEIGHT, "RPG BulletHell", this);
		
		hud = new HUD();
		spawner = new Spawner(handler, hud, r);
		
		r = new Random();
		
		createObjects();
		
		handler.addObject(player);
		
		//handler.addObject(new BlueSlime(WIDTH/4, HEIGHT/4, ID.Slime, handler, r));
		//handler.addObject(new RedSlime(WIDTH/4, HEIGHT/4, ID.Slime, handler, r));
		//handler.addObject(new SlimeQueen(WIDTH/4, HEIGHT/4, ID.Slime, handler, r));
		//handler.addObject(new OrangeSlime(WIDTH/4 * 3, HEIGHT/4 * 3 - 40, ID.Slime, handler, r));
		//handler.addObject(new SkeletonMage(WIDTH/4 - 80, HEIGHT/4 * 3 - 40, ID.Skeleton, handler, r));
		
		this.addKeyListener(keyI);
	}
	
	public void createObjects() {
		//player = new Player(WIDTH/2 - 32, HEIGHT - 200, ID.Player, null, handler, "rogue");
		player = new Player(WIDTH/2 - 32, HEIGHT - 200, ID.Player, null, handler, "rogue");
		player.setMaxHP(20);
		player.setHP(player.getMaxHP());
		paladinE = (new PaladinE(0, 0, ID.Attack));
		rogueE = new RogueE(0, 0, ID.Attack);
		paladinQ = new PaladinQ(0, 0, ID.Attack);
		rogueQ = new RogueQ(0,0,ID.Attack);
	}
	
	public void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
				long timer = System.currentTimeMillis();
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
					while(delta >= 1){
						tick();
						delta--;
					}
			if(running)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames);
				fps = frames;
				frames = 0;
			}
		}
		stop();
	}
	
	public void tick() {
		if(gameState == STATE.Slime || gameState == STATE.Clear) {
			handler.tick();
			hud.tick();
			spawner.tick();
		}else if(gameState == STATE.Select) {
			rolePicker.Tick();
		}
		if(gameState == STATE.Lobby) {
			handler.tick();
		}
		if(gameState == STATE.End) {
			player.setHP(player.getMaxHP());
			player.setX(WIDTH/2 - 32);
			player.setY(HEIGHT - 200);
			gameState = STATE.Lobby;
		}
		collision();
		//handler.addObject(new SlimeBallAttack(300, 300, ID.EnemyProjectile, handler));
	}
	
	public void collision() {
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getID() != ID.Attack && tempObject.getID() != ID.Player) {
				if((player.getBounds().intersects(tempObject.getBounds()))) {
					if(player.getITime() == 0) {
						player.setHP(player.getHP() - tempObject.getDamage());
						player.setITime(60);
					}
					if(tempObject.getID() == ID.EnemyProjectile || tempObject.getID() == ID.Hazard)handler.removeObject(tempObject);
				}
				if(paladinE.getBounds().intersects(tempObject.getBounds()) && paladinE.getCasted()){
					if(tempObject.getITime() == 0) {
						System.out.println("hit");
						if(tempObject.getID() == ID.EnemyProjectile || tempObject.getID() == ID.Hazard)tempObject.setHP(tempObject.getHP() - paladinE.getDamage()*100);
						else tempObject.setHP(tempObject.getHP() - paladinE.getDamage());
						tempObject.setITime(30);
					}
				}
				if(rogueE.getBounds().intersects(tempObject.getBounds()) && rogueE.getCasted()){
					if(tempObject.getITime() == 0) {
						System.out.println("hit");
						if(tempObject.getID() == ID.EnemyProjectile || tempObject.getID() == ID.Hazard)tempObject.setHP(tempObject.getHP() - rogueE.getDamage()*100);
						else tempObject.setHP(tempObject.getHP() - rogueE.getDamage());
						rogueE.special(tempObject);
						tempObject.setITime(30);
					}
				}
				if(rogueQ.getBounds().intersects(tempObject.getBounds() ) && rogueQ.getCasted()){
					if(tempObject.getITime() == 0) {
						System.out.println("hit");
						if(tempObject.getID() == ID.EnemyProjectile || tempObject.getID() == ID.Hazard)tempObject.setHP(tempObject.getHP() - rogueQ.getDamage()*100);
						else tempObject.setHP(tempObject.getHP() - rogueQ.getDamage());
						tempObject.setITime(30);
					}
				}
			}
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics2D g2 = (Graphics2D) bs.getDrawGraphics();
		
		g2.setColor(Color.black);
		g2.fillRect(0, 0, WIDTH, HEIGHT); // Remember to replace
		worldMaker.render(g2);
		if(gameState == STATE.Slime || gameState == STATE.Clear) {
			hud.render(g2);
			handler.render(g2);
			player.render(g2);
		}else if(gameState == STATE.Select) {
			rolePicker.render(g2);
		}
		if((gameState == STATE.Lobby || gameState == STATE.Clear )&& player != null) {
			handler.render(g2);
			lobby.render(g2);
			player.render(g2);
		}
		
		g2.dispose();
		bs.show();
	}
	
	public static void checkFull() {
		if(player.getHP() > player.getMaxHP()) {
			player.setHP(player.getMaxHP());
		}
	}
	
	public static float clamp(float var, float min, float max) {
		if(var >= max) {
			return max;
		}else if(var <= min) {
			return min;
		}else {
			return var;
		}
	}
	
	public static void main(String args[]) {
		new Game(); // starts game up
		System.out.println("game started");
	}
	
}
