package main;

import java.awt.Graphics2D;
import java.util.LinkedList;

public class Handler {
	
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
		
	public void tick() {
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			if (tempObject.getID() == ID.Attack){
				if(tempObject.getCooldown() > 0)tempObject.tick();
			}else tempObject.tick();
		}
	}
	
	public void render(Graphics2D g2) {
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			if (tempObject.getID() == ID.Attack || tempObject.getID() == ID.Player){
				if(tempObject.getCasted())tempObject.render(g2);
			}else tempObject.render(g2);
		}
	}
	
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	
	public void removeObject(GameObject object) {
		if(object.getID() == ID.Slime) {
			Spawner.slimesKilled += 1;
		}
		if (object.getID() != ID.Player && object.getID() != ID.Attack && object.getID() != ID.EnemyProjectile && object.getID() != ID.Hazard) {
			if(Game.player.role.equals("paladin")) {
				if(object.getID() == ID.Slime) {
					if(object.getSummoned()) {
						Game.player.setHP(Game.player.getHP() + 1);
					}
				}else {
					Game.player.setHP(Game.player.getHP() + 1);
				}
			}
			Game.checkFull();
		}
		this.object.remove(object);
	}
	
	public void clearEnemies(ID id) {
		if(id == null) {
			for(int i = 0; i < object.size(); i++) {
				GameObject tempObject = object.get(i);
				
				if (tempObject.getID() != ID.Player && tempObject.getID() != ID.Attack){
					removeObject(tempObject);
				}
			}
		}else {
			for(int i = 0; i < object.size(); i++) {
				GameObject tempObject = object.get(i);
				
				if (tempObject.getID() == id){
					removeObject(tempObject);
				}
			}
		}
	}
	
}
