import java.awt.Color;

import javax.swing.JOptionPane;

import java.awt.Graphics;


/*This class controls the logic and operation of the game, like player movement, how the mhos move, how things are placed
  An instance of it is created in class, and this instance of it is 1 round of the game
  There are a lot of methods in this class, and we'll go through them 1 by 1
 */
//---------------Ð------------------------------------------------------------------------

public class Grid {
	//Here we create a lot of arrays that store information about the grid
	//We can also control the number of mhos and fences from here
	private static int[][] gridArray = new int[12][12];
	private static boolean firstSet = true; //indicates whether the program has just been opened or not
	private static boolean playerDead = false;//determines if game continues or ends
	private static int playerX;//coordinates of player
	private static int playerY;
	private static int numberInsideFences = 0;//# of fences randomly placed inside the grid, excluding those on the border
	private static int numberMhos = 2; //number of mhos
	private static int numberpla = 5;//number of body parts
	private static int[][] mhoList = new int[numberMhos][2];//stores x and y coordinates of each mho
	private static int[][] plaList = new int[numberpla][2];
	private static int gameOverCount = 0;
	int applegot=0;
	//This is an orange color
	static Color hvOrange = new Color(252, 146, 0);
	//This is the constructor. It just takes graphics to draw the mhos, fences, and player and then runs init
	public Grid(Graphics g){
		init(g);
	}

	/*This is init. It creates the random set of mhos and fences and player location on the first time its run
	  It only runs once per round by creating a boolean called firstSet that starts true but becomes untrue after
	  init starts by filling up gridArray with values
  	  then it sets the perimeter fences
	  Then it randomly places fences and mhos, depending on how many are allowed above
	 */
	public static void init(Graphics g) {
		/*0 = empty space
		 * 1 = Fence
		 * 2=Mho
		 * 3 = You
		 */
		if (firstSet){ //sets up initial round of game
			for (int x=0; x<=11; x++){
				for (int y=0; y<=11; y++){
					gridArray[x][y] = 0; ////fill array with empty space
				}
			}

			for (int x=0; x<=11; x++){
				//set perimeter fences
				gridArray[x][0] = 1;
				gridArray[x][11] = 1;
				if(x>0 && x<11){
					gridArray[0][x] = 1;
					gridArray[11][x] = 1;
				}
			}

			for (int count=1; count<=numberInsideFences; count++){
				//create random inside fences
				boolean placeFence = false;
				while(placeFence == false){
					int x = (int) (Math.ceil(Math.random()*10)+1); //random number between 1 and 11
					int y = (int) (Math.ceil(Math.random()*10)+1);
					if(gridArray[x][y] == 0){ //checks to see if space is unoccupied
						gridArray[x][y] = 1;
						placeFence = true;
					}
				}
			}
			for (int count=1; count<=numberMhos; count++){
				//create random mhos
				boolean placeMho = false;
				while(placeMho == false){
					int x = (int) (Math.ceil(Math.random()*10)+1); //random number between 1 and 11
					int y = (int) (Math.ceil(Math.random()*10)+1);
					if(gridArray[x][y] == 0){
						gridArray[x][y] = 2;
						placeMho = true;
					}
				}
			}
			//create player
			boolean placePlayer = false;
			int x = (int) (Math.ceil(Math.random()*10)+1); //random number between 1 and 11
			int y = (int) (Math.ceil(Math.random()*10)+1);
			while(placePlayer == false){ //while player has not yet been designated to a spot
				if(gridArray[x][y] == 0){ //if space is unoccupied
					gridArray[x][y] = 3; //set to 3, which marks it as where player will be
					playerX = x; //coordinates of player
					playerY = y;
					placePlayer = true; //ends loop
				}
			}

			for (int count=1; count<=numberpla-1; count++){	
				boolean placepla = false;
				while(placepla == false){
					int xnext = (int) (x+count*10); //random number between 1 and 11
					int ynext = (int) (x+count*10);
					/*
					if(gridArray[xnext][ynext] == 0);
						gridArray[xnext][ynext] = 3;
						placePlayer = true; 
				}
			}
			firstSet = false;
		}
		updateGrid(g);
	}
	/*This is the code that controls the mho's movements
	  It starts by finding out where each mho in the mho list is
	  Then it checks each mho's position in comparison to the player's position, and changes the mho's position according to the rules of hivolts
	  Then it places all the mho's in the new positions
	  Takes nothing, returns nothing
	 */
	public static void mhoAI(){
		createMhoList();
		createplaList();
	}

	/*
	 * This is invoked at the beginning of mhoAI()
	 * It creates a 2D array, mhoList, that stores the x and y coordinates of each mho
	 * The array has 2 columns, with column 0 for the x values and column 1 for the y values
	 * The first [] of the array specifies a particular mho
	 * The second [] contains the x and y
	 * Does not take/return anything
	 */
	private static void createMhoList(){
		int mhosCounted = 0;
		for (int x=0; x<=11; x++){
			for (int y=0; y<=11; y++){
				if(gridArray[x][y] == 2){ //if a mho is located at cell (x,y)
					mhoList[mhosCounted][0] = x; //set coordinates of mho in array
					mhoList[mhosCounted][1] = y;
					mhosCounted++;
				}
			}
		}
	}

	private static void createplaList(){
		int plaCounted = 0;
		for (int x=0; x<=11; x++){
			for (int y=0; y<=11; y++){
				if(gridArray[x][y] == 2){ //if a mho is located at cell (x,y)
					plaList[plaCounted][0] = x; //set coordinates of mho in array
					plaList[plaCounted][1] = y;
					plaCounted++;
				}
			}
		}
	}

	/*
	 * This method is used obve in mhoAI()
	 * Takes the new x and y coordinates of mho, returns nothing
	 */
	/*
	private static void moveMho(int x, int y){
		if(gridArray[x][y] == 0){//if the space is empty
			gridArray[x][y] = 2; //marks new cell as occupied by a mho
		}
		else if(gridArray[x][y] == 3){//if the new location is occupied by the player
			gridArray[x][y] = 2; //the mho lands there
			playerDead = true; //player dies
		}
		else if(gridArray[x][y] == 1){//if it walks into a fence
			numberMhos--;//it dies; one less mho is on the board
		}
	}*/

	/*
	 * Takes the x and y coordinates of a square in the grid
	 * This is used to see where the mho/player can move next
	 * Returns true/false
	 */
	private static boolean checkPosition(int x, int y){
		boolean spaceOccupied = false; //by default, is empty
		if(gridArray[x][y] == 2){ //if occupied by mho
			spaceOccupied = true;
		}
		return spaceOccupied;
	}

	/*
	 * Takes new coordinates of the player
	 * Checks to see if player will stay alive or die
	 */
	private static void changeGridPlayer(int x, int y){
		if(gridArray[x][y] == 0){ //if space is empty
			gridArray[x][y] = 3; //mark as occupied by player
		}
		else if(gridArray[x][y] == 3) {
			//do nothing
		}
		else { //if space is occupied by a fence or a mho
			playerDead = true; //player dies
		}
	}

	/*
	 * Takes an integer, which was set by the KeyListener methods
	 * Returns nothing
	 * Based on the keyboard input from user
	 * Moves the player to a new location
	 * Invoked by KeyListener in Display.java
	 */
	public static void movePlayer(int movement){
		//if player is still alive
		if(!playerDead){
			/* 1=up
			 * 2=down
			 * 3=left
			 * 4=right
			 * 5=up/right
			 * 6=up/left
			 * 7=down/right
			 * 8=down/left
			 * 9=jump
			 */
			if(movement == 1){
				gridArray[playerX][playerY] = 0; //set original location to empty
				playerY --; //go up
				changeGridPlayer(playerX, playerY); //new location
			}
			else if(movement == 2){
				gridArray[playerX][playerY] = 0;
				playerY ++;
				changeGridPlayer(playerX, playerY);
			}
			else if(movement == 3){
				gridArray[playerX][playerY] = 0;
				playerX ++;
				changeGridPlayer(playerX, playerY);
			}
			else if(movement == 4){
				gridArray[playerX][playerY] = 0;
				playerX --;
				changeGridPlayer(playerX, playerY);
			}
			else if(movement == 5){
				gridArray[playerX][playerY] = 0;
				playerY --;
				playerX ++;
				changeGridPlayer(playerX, playerY);
			}
			else if(movement == 6){
				gridArray[playerX][playerY] = 0;
				playerY --;
				playerX --;
				changeGridPlayer(playerX, playerY);
			}
			else if(movement == 7){
				gridArray[playerX][playerY] = 0;
				playerY ++;
				playerX ++;
				changeGridPlayer(playerX, playerY);
			}
			else if(movement == 8){
				gridArray[playerX][playerY] = 0;
				playerY ++;
				playerX --;
				changeGridPlayer(playerX, playerY);
			}
			else if(movement == 9){
				boolean placePlayer = false;
				while(placePlayer == false){ //keeps this looping until program finally finds a space for the player to land on
					int x = (int) (Math.ceil(Math.random()*10)+1); //random # between 1 and 11, so that player doesn't land on the boundary of the grid
					int y = (int) (Math.ceil(Math.random()*10)+1);
					if(gridArray[x][y] == 0 || gridArray[x][y] == 2){ //if cell is empty or occupied by mho
						gridArray[playerX][playerY] = 0; //move player to that cell
						playerX = x;
						playerY = y;
						changeGridPlayer(playerX, playerY);
						placePlayer = true; //end loop
					}
				}
			}
		}
	}

	/*This method creates a dialog box after game is over, either telling the player that
	 * they won, or that they lost.  Then, it proceeds to close the window.
	 */
	public static void gameOver(Graphics g){
		if(numberMhos == 0){///victory condition
			JOptionPane.showMessageDialog(null, "Congratulations! You won!");
		}
		else if(playerDead == true){//lose condition
			JOptionPane.showMessageDialog(null, "Sorry, you lost.");
		}
		System.exit(0); //close the program
	}

	/*
	 * Takes Graphics object as argument, returns nothing
	 * Checks if a game over condition is true
	 * Draws the fence, mhos, and player on the screen
	 * Shifts these drawings based on the gridArray #'s
	 */
	public static void updateGrid(Graphics g){
		if(numberMhos==0 || playerDead == true){//if either game over condition is true
			if(gameOverCount == 0){//the game over dialog hasn't popped up before
				gameOver(g);
				gameOverCount ++;
			}
		}
		else{
			//loops through the whole grid
			for (int x=0; x<=11; x++){
				for (int y = 0; y <=11;y ++){
					/*if(gridArray[x][y] == 1){ //if cell is occupied by a fence
						drawFence(g,x,y); //draw a fence
					}*/
					if(gridArray[x][y] == 3){ //if cell is occupied by the player
						drawYou(g,x,y); //draw player
					}
					if(gridArray[x][y] == 2){ //if cell is occupied by a mho
						drawMho(g,x,y); //draw mho
					}
				}
			}
		}
	}

	/*
	 * Takes Graphics object and coordinates of player
	 * Draws player as a smiley face
	 */
	public static void drawYou(Graphics g, int x, int y){
		int x1 = x*41 + 25 + 5;
		int y1 = y*41 + 25 + 5;
		g.setColor(hvOrange);
		g.drawOval(x1,y1,30,30);
		g.fillOval(x1+7,y1+8, 6, 6);
		g.fillOval(x1+18,y1+8, 6, 6);
		g.drawArc(x1 + 7, y1 + 20, 16, 5, 0, -180);
	}

	/*
	 * Takes Graphics object and coordinates of mho
	 * Draws mho as a frowny face
	 */
	public static void drawMho(Graphics g, int x, int y){
		int x1 = x*41 + 25 + 5;
		int y1 = y*41 + 25 + 5;
		g.setColor(hvOrange);
		g.fillOval(x1,y1,30,30);
		g.setColor(Color.BLACK);
		g.fillOval(x1+7,y1+8, 6, 6);
		g.fillOval(x1+18,y1+8, 6, 6);
		g.drawArc(x1 + 7, y1 + 20, 16, 5, 10, 165);
	}

	/*
	 * Takes Graphics object and coordinates of fence
	 * Draws fence
	 */
	public static void drawFence(Graphics g, int x, int y){
		int x1 = x*41 + 25 + 1;
		int y1 = y*41 + 25 + 1;
		g.setColor(hvOrange);
		g.fillRect(x1 + 4,y1 + 5,32,31);
		g.setColor(Color.BLACK);
		g.fillRect(x1 + 7, y1 + 4, 26, 7);
		g.fillRect(x1 + 7, y1 + 14, 26, 5);
		g.fillRect(x1 + 7, y1 + 22, 26, 5);
		g.fillRect(x1 + 7, y1 + 30, 26, 8);
	}

}
