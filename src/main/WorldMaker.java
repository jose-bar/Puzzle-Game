package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.Game.STATE;

public class WorldMaker {

	Game game;
	BufferedImage[] tile, lobbyTile;
	int mapTileNum[][];
	
	public WorldMaker(Game game) {
		
		this.game = game;
		
		tile = new BufferedImage[20];
		mapTileNum = new int[10][13];
		lobbyTile = new BufferedImage[20];
		
		getTileImage();
		loadMap("/maps/lobby.txt");
		
	}
	
	public void getTileImage() {
		
		try {
			
			tile[0] = ImageIO.read(getClass().getResourceAsStream("/world/dungeonFloor-1.png"));
			
			tile[1] = ImageIO.read(getClass().getResourceAsStream("/world/dungeonFloor-2.png"));
			
			tile[2] = ImageIO.read(getClass().getResourceAsStream("/world/dungeonFloor-3.png"));
			
			tile[3] = ImageIO.read(getClass().getResourceAsStream("/world/dungeonFloor-4.png"));
			
			tile[4] = ImageIO.read(getClass().getResourceAsStream("/world/wall-tile2.png"));
			
			tile[5] = ImageIO.read(getClass().getResourceAsStream("/world/water-tile.png"));
			
			tile[6] = ImageIO.read(getClass().getResourceAsStream("/world/dungeonCarpet.png"));
			
			tile[7] = ImageIO.read(getClass().getResourceAsStream("/world/dungeonCarpet2.png"));
			
			tile[8] = ImageIO.read(getClass().getResourceAsStream("/world/dungeonPillar.png"));
			
			tile[9] = ImageIO.read(getClass().getResourceAsStream("/world/dungeonPillarTop.png"));
			
			tile[10] = ImageIO.read(getClass().getResourceAsStream("/world/dungeonPillarBottom.png"));
			
			tile[11] = ImageIO.read(getClass().getResourceAsStream("/world/dungeonPillarBody.png"));
			
			tile[12] = ImageIO.read(getClass().getResourceAsStream("/world/dungeonHole-1.png"));
			
			tile[13] = ImageIO.read(getClass().getResourceAsStream("/world/dungeonHole2.png"));
			
			tile[14] = ImageIO.read(getClass().getResourceAsStream("/world/dungeonHole-3.png"));
			
			tile[15] = ImageIO.read(getClass().getResourceAsStream("/world/dungeonHole-4.png"));
			
			lobbyTile[0] = ImageIO.read(getClass().getResourceAsStream("/world/grass-tile-flowerless.png"));
			
			lobbyTile[1] = ImageIO.read(getClass().getResourceAsStream("/world/grass-tile-light-flowers.png"));
			
			lobbyTile[2] = ImageIO.read(getClass().getResourceAsStream("/world/slimeDungeon-1.png"));
			
			lobbyTile[3] = ImageIO.read(getClass().getResourceAsStream("/world/slimeDungeon-2.png"));
			
			lobbyTile[4] = ImageIO.read(getClass().getResourceAsStream("/world/slimeDungeon-3.png"));
			
			lobbyTile[5] = ImageIO.read(getClass().getResourceAsStream("/world/slimeDungeon-4.png"));
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public void loadMap(String fp) {
		
		try {
			InputStream is = getClass().getResourceAsStream(fp);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < 10 && row < 13) {
				
				String line = br.readLine(); // This can read a single line of txt
				
				while(col < 10) {
					
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == 10) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		}catch(Exception e) {
			
		}
	}
	
	public void render(Graphics2D g2) {
		if(Game.gameState != STATE.Lobby && Game.gameState != STATE.Select) {
			loadMap("/maps/map.txt");
		}else {
			loadMap("/maps/lobby.txt");
		}
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while(col < 10 && row < 13) {
			
			int tileNum = mapTileNum[col][row];
			if(Game.gameState != STATE.Select && Game.gameState != STATE.Lobby) {
				g2.drawImage(tile[tileNum], x, y, 48, 48, null);
			}else {
				g2.drawImage(lobbyTile[tileNum], x, y, 48, 48, null);
			}
			col++;
			x += 48;
			
			if(col == 10) {
				col = 0;
				x = 0;
				row++;
				y += 48;
			}
			
		}
	}
	
}
