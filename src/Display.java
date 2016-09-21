
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JComponent;

//Summary paragraph:
//This is the display class
//It controls some of the graphics, and the keylisteners for player movement
//The graphics it controls is everything but the drawing of the mhos, fences, and players
//Such as the grid and background

//-------------------------------------------------------------------------------------------

public class Display extends JComponent{

	//Here we define some invariants
	public static final int ROWS = 12;
	public static final int COLS = 12;
	private final int X_GRID_OFFSET = 25; // 25 pixels from left
	private final int Y_GRID_OFFSET = 25; // 25 pixels from top
	private final int CELL_WIDTH = 40;
	private final int CELL_HEIGHT = 40;

	//The creation of the color we used. We chose a color that matched the initial color
	//scheme of the hivolts game
	
	static Color hvOrange = new Color(252, 146, 0);

	// Note that a final field can be initialized in constructor
	private final int DISPLAY_WIDTH;   
	private final int DISPLAY_HEIGHT;
	
	//This creates a key listener which is used in controlling player movement
	public KeyControl listener = new KeyControl();
	
	//This is a constructor for this display class, it is called in Main
	public Display(int width, int height) {
		DISPLAY_WIDTH = width;
		DISPLAY_HEIGHT = height;
		setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
	}
	
	//This is the paint component of the display class
	//It starts by drawing a black background, which is needed to "remove" the last turns graphics
	//Then it draws the grid, the method of which is below
	//Then it creates an instance of Grid, the next class, which creates and draws the mhos, fence, and player
	//It also repaints
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 559, 632);
		g.setColor(hvOrange);
		drawGrid(g);
		//drawButtons(g);	
		Grid gameGrid = new Grid(g);
		Grid.updateGrid(g);
		repaint();
	}
	
	//This method uses 2 for loops to draw the grid
	//It calls upon the invariants above
	//Adapted from our Conway game of life code
	void drawGrid(Graphics g) {
		for (int row = 0; row <= ROWS; row++) {
			g.drawLine(
					X_GRID_OFFSET,
					Y_GRID_OFFSET + (row* (CELL_HEIGHT + 1)),
					X_GRID_OFFSET + COLS * (CELL_WIDTH + 1), 
					Y_GRID_OFFSET + (row * (CELL_HEIGHT + 1)));
		}
		for (int col = 0; col <= COLS; col++) {
			g.drawLine(
					X_GRID_OFFSET + (col * (CELL_WIDTH + 1)), 
					Y_GRID_OFFSET,
					X_GRID_OFFSET + (col * (CELL_WIDTH + 1)), 
					Y_GRID_OFFSET + ROWS * (CELL_HEIGHT + 1));
		}
	}
	//Here is the key listener class
		//For each case of a key being pressed, we run a method in Grid
		//For instance, when you press W, it runs movePlayer(1), which corresponds to an upward movement in Grid
		//Then after each command, we run mhoAI, which moves all the mhos
		//Invokes movePlayer() in Grid.java
		private class KeyControl extends KeyAdapter { //Listens to the keyboard!
			
			@Override
			public void keyPressed(KeyEvent e) {
				//Move the player based on what key was pressed
			switch(e.getKeyCode()) {
			case KeyEvent.VK_W:
				Grid.movePlayer(1);
				Grid.mhoAI();
				break;
			case KeyEvent.VK_UP:
				Grid.movePlayer(1);
				Grid.mhoAI();
				break;
			case KeyEvent.VK_X:
				Grid.movePlayer(2);
				Grid.mhoAI();
				break;
			case KeyEvent.VK_DOWN:
				Grid.movePlayer(2);
				Grid.mhoAI();
				break;
			case KeyEvent.VK_D:
				Grid.movePlayer(3);
				Grid.mhoAI();
				break;
			case KeyEvent.VK_RIGHT:
				Grid.movePlayer(3);
				Grid.mhoAI();
				break;
			case KeyEvent.VK_A:
				Grid.movePlayer(4);
				Grid.mhoAI();
				break;
			case KeyEvent.VK_LEFT:
				Grid.movePlayer(4);
				Grid.mhoAI();
				break;
			case KeyEvent.VK_E:
				Grid.movePlayer(5);
				Grid.mhoAI();
				break;
			case KeyEvent.VK_Q:
				Grid.movePlayer(6);
				Grid.mhoAI();
				break;
			case KeyEvent.VK_C:
				Grid.movePlayer(7);
				Grid.mhoAI();
				break;
			case KeyEvent.VK_Z:
				Grid.movePlayer(8);
				Grid.mhoAI();
				break;
			case KeyEvent.VK_J:
				Grid.movePlayer(9);
				break;
			case KeyEvent.VK_S:
				Grid.mhoAI();
				break;
			}
		}
		public void keyReleased(KeyEvent e) {


		}

		public void keyTyped(KeyEvent e) {

		}
	}
}
