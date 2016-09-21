import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;

//Summary paragraph:

//This is the main for our project
//The project starts running from here
//The main thing this class does is create the Display and activate the keylistener
//To create the display we had to set the width and heart and create a jframe
//We also set the title to "Hivolts" and made this display visible.

public class Main{
	public static void main(String[] args){
		//For display width I did 41(size of cell)*12(# of cells) + 1 (edge of last cell) + 25*2(desired margin) + 8*2(size of window sides)
		//The height followed a similar process
		final int DISPLAY_WIDTH = 41*12 + 1 + 25*2 + 8*2;
		final int DISPLAY_HEIGHT = 41*12 + 1 + 25 + 75 + 8 + 31;
		JFrame f = new JFrame();
		f.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		Display display = new Display(DISPLAY_WIDTH, DISPLAY_HEIGHT);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Set title
		f.setTitle("Minesweeper");
		//Create key listener
		f.addKeyListener(display.listener);
		f.add(display);
		f.setVisible(true);
	}
}