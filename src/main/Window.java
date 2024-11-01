package main;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas{

	private static final long serialVersionUID = -4810618286807932601L;
	
	public Window(int width, int height, String title, Game game) {
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Makes x button work
		frame.setResizable(false); // Cannot resize app
		frame.setLocationRelativeTo(null); // makes start in center of screen
		frame.add(game); // Adding the game into the frame
		frame.setVisible(true);
		game.start();
	}
	
}
