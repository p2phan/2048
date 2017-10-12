//------------------------------------------------------------------//           
// Board.java                                                       //
//                                                                  //
// Class used to represent a 2048 game board                        //
//                                                                  //
// Author:  W16-CSE8B-TA group                                      //
// Date:    1/17/16                                                 //
//------------------------------------------------------------------//

/* 
 * Name: Peter Phan Student 
 * Login: cs8bwahh 
 * Date: 1/26/16
 * File: Board.java 
 * Sources of Help: Tutors in Lab, Introduction to Java 
 * Programming 10th Edition Daniel Liang 
 * 
 * The Board class is used to either construct a new board
 * or to load a board from a previously saved game. When 
 * the saved game is loaded all the information will be 
 * to this Board object. Including the grid size, the score,
 * and the numbers in the grid. The board class has methods
 * to move the "tiles" in the certain direction, keep track 
 * of the score and to determin whether or not the game
 * is over. It can also save the game to a file and print
 * out the results.
 *
 *
 */

import java.util.*;
import java.io.*;

public class Board {
	public final int NUM_START_TILES = 2;
	public final int TWO_PROBABILITY = 90;
	public final int GRID_SIZE;


	private final Random random;
	private int[][] grid;
	private int score;


	/* Constructor to create fresh board
	 *@param int boardSize, represents board size
	 *@param Random random, represents the
	 *random object to be used in upcoming methods
	 */
	public Board(int boardSize, Random random) {
		this.random = random;
		GRID_SIZE = boardSize; 
		grid = new int[GRID_SIZE][GRID_SIZE];
		int count = 0;
		while( count < NUM_START_TILES) {
			addRandomTile();
			count++;
		}
	}
	/*
	 * Constructor to create a board offinput file
	 *@param String inputBoard represents the name 
	 *of the file of the game
	 *@param Random random, represents the
	 *random object to be used in upcoming methods
	 */
	public Board(String inputBoard, Random random) throws IOException {
		this.random = random;
		Scanner newGame = new Scanner( new File(inputBoard));
		GRID_SIZE = newGame.nextInt();
		grid = new int[GRID_SIZE][GRID_SIZE];
		score = newGame.nextInt();
		while (newGame.hasNext()) {
			for(int i = 0; i < grid.length; i++) {
				for(int j = 0; j < grid[i].length; j++) {
					grid[i][j] = newGame.nextInt();
				}
			}
		}


	}

	/*
	 * Name:saveBoard
	 * Purpose: This method is to save the game to a file
	 * once the player loses or exits.
	 * Parameters:
	 * @param String outputBoard, this represents the 
	 * name of the file that the game will be saved to.
	 * Return: void
	 */
	public void saveBoard(String outputBoard) throws IOException {
		File saveGame = new File(outputBoard);
		PrintWriter putInGame = new PrintWriter(saveGame);
		//the game will first print the grid size on one line
		//then the score on the next line
		putInGame.println(GRID_SIZE);
		putInGame.println(score);
		for(int i=0; i<grid.length; i++) {
			for(int j=0; j<grid[i].length; j++) {
				putInGame.print(grid[i][j]+" ");

			}
			putInGame.println();
		}
		putInGame.close();
	}


	/*
	 * Name:addRandomTile
	 * Purpose: This method will add a tile of 2 or 4 to a 
	 * random empty spot everytime it's called.
	 * Parameters: none
	 * Return: void
	 */

	public void addRandomTile() {

		// count is used to find the number of empty spaces
		// in the grid
		int count = 0;
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if( grid[i][j]==0)
				{
					count++;
				}
			}
		}
		if (count == 0) 
			return;	


		//after the location is chosen based on how many
		//empty spaces there are in the grid, count is
		//is set back to 0 to find where the location is
		int location = random.nextInt(count);
		int value = random.nextInt(100);
		count = 0;	

		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if (grid[i][j]==0) {
					if(count ==location) {
						if (value < TWO_PROBABILITY) {
							grid[i][j]=2;
						}
						else{ grid[i][j]=4;}
					}
					count++;
				}
			}
		}	
	}









	/*
	 * Name: rotate
	 * Purpose: This method will rotate the board either 90
	 * degrees clockwise or counterclockwise based on the 
	 * boolean.
	 * Parameters:
	 * @param: boolean rotateClockwise, this represents 
	 * how the board will rotate. If rotateClockwise == true,
	 * it will rotate clockwise. If rotateClockwise == false,
	 * it will rotate counterclockwise.
	 * Return: void
	 *
	 */
	public void rotate(boolean rotateClockwise) {


		//the new int[][] temp is created to store the grid
		//numbers onto temp. Then, the numbers in temp
		// will replace that in grid based on the location
		// in the int[][]and making the grid rotated.

		int[][] temp = new int[GRID_SIZE][GRID_SIZE];
		for( int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				temp[i][j] = grid[i][j];
			}
		}  
		if (rotateClockwise == true)
		{
			for(int i = 0; i < grid.length; i++) {
				for(int j = 0; j < grid[i].length; j++) {
					grid[i][j] = temp[grid.length-j-1][i];

				}
			}
		}
		if ( rotateClockwise == false) {
			for(int i = 0; i < grid.length; i++) {
				for(int j = 0; j < grid[i].length; j++) {
					grid[i][j] = temp[j][grid.length-1-i];
				}
			}
		}
	}

	//Complete this method ONLY if you want to attempt at getting the extra credit
	//Returns true if the file to be read is in the correct format, else return
	//false
	public static boolean isInputFileCorrectFormat(String inputFile) {
		//The try and catch block are used to handle any exceptions
		//Do not worry about the details, just write all your conditions inside the
		//try block
		try {
			Scanner checkGame = new Scanner( new File(inputFile) );
			if(!checkGame.hasNextInt()) 
			{
				return false;
			}
			else { 
				int checkSize = checkGame.nextInt();
				if (checkSize<2)
					return false;
			}

			if(!checkGame.hasNextInt()) {
				return false;
			}
			else { 
				int checkScore = checkGame.nextInt();
				if(checkScore<0 || checkScore%4 != 0)
					return false;
			}

			int checkGrid;
			while (checkGame.hasNext()) {
				if(!checkGame.hasNextInt()) {
					return false;
				}
				else {
					checkGrid = checkGame.nextInt();
					if( checkGrid <0 || checkGrid%2 != 0) 
						return false;

				}
			}



			//write your code to check for all conditions and return true
			// if it satisfies
			//all conditions else return false
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 *Name: moveLeft
	 *Purpose:This is the helper method for move() method
	 *Moves the tiles in the left direction and 
	 *tiles that are combined already cannot combine again
	 *Parameter: none
	 *Return: void
	 */
	private void moveLeft() {
		//this for loop closes the gap between non-zero tiles
		//to the left
		for(int i = 0; i<grid.length; i++) {
			for( int j = 0; j<grid.length; j++) {
				for( int k = grid[j].length-1; k>0; k--) {
					if( grid[j][k-1]==0 ) {
						grid[j][k-1]=grid[j][k];
						grid[j][k]=0;
					}
				}
			}
		}

		//this for loop combines tiles next to each other
		// to the left
		for(int i = 0; i<grid.length; i++) {
			for (int j =0; j< grid[i].length-1; j++) {
				if( grid[i][j]==grid[i][j+1]) {
					grid[i][j] = grid[i][j]*2;
					score += grid[i][j];
					grid[i][j+1]=0;
				}
			}
		}

		//this for loop closes the gap of any remaining tiles 
		//to the left
		for(int i = 0; i<grid.length; i++) {
			for(int j = 0; j<grid.length; j++) {
				for(int k = grid[j].length-1; k>0; k--) {
					if( grid[j][k-1]==0 ) {
						grid[j][k-1]=grid[j][k];
						grid[j][k]=0;
					}
				}
			}
		}
	}

	/*
	 *Name: moveRight
	 *Purpose:This is the helper method for move() method
	 *Moves the tiles in the right direction and
	 *tiles that are combined already cannot combine again
	 *Parameter: none
	 *Return: void
	 */

	public void moveRight() {
		//this for loop closes the gap between non-zero tiles
		//to the right
		for(int i = 0; i<grid.length; i++) {
			for(int j = 0; j<grid.length; j++) {
				for (int k = 0; k<grid[j].length-1; k++) {
					if( grid[j][k+1]==0) {
						grid[j][k+1]=grid[j][k];
						grid[j][k]=0;
					}
				}
			}
		}

		//this for loop combines tiles next to each other
		//to the right
		for(int i = 0; i<grid.length; i++) {
			for(int j = grid[i].length-1; j>0; j--) {
				if(grid[i][j]== grid[i][j-1]) {
					grid[i][j]=grid[i][j]*2;
					score+=grid[i][j];
					grid[i][j-1]=0;
				}
			}
		}

		//this for loop closes the gap of any remaining tiles 
		//to the right
		for(int i = 0; i<grid.length; i++) {
			for(int j = 0; j<grid.length; j++) {
				for (int k = 0; k<grid[j].length-1; k++) {
					if( grid[j][k+1]==0) {
						grid[j][k+1]=grid[j][k];
						grid[j][k]=0;
					}
				}
			}
		}
	}
	/*
	 *Name: moveUp
	 *Purpose:This is the helper method for move() method
	 *Moves the tiles in the up direction
	 *tiles that are combined already cannot combine again
	 *Parameters:none
	 *Return: void
	 */
	private void moveUp() {
		//this for loop closes the gap between non-zero tiles
		//upwards
		for(int i = 0; i<grid.length; i++) {
			for(int j = grid.length-1; j>0; j--) {
				for (int k = 0; k<grid[j].length; k++) {
					if( grid[j-1][k]==0) {
						grid[j-1][k]=grid[j][k];
						grid[j][k]=0;
					}
				}
			}
		}
		//this for loop combines tiles next to each other
		//upwards
		for(int i =0; i<grid.length-1; i++) {
			for(int j = 0; j<grid[i].length; j++) {
				if(grid[i][j]== grid[i+1][j]) {
					grid[i][j]=grid[i][j]*2;
					score+=grid[i][j];
					grid[i+1][j]=0;
				}
			}
		}

		//this for loop closes the gap of any remaining tiles 
		//upwards
		for(int i = 0; i<grid.length; i++) {
			for(int j = grid.length-1; j>0; j--) {
				for (int k = 0; k<grid[j].length; k++) {
					if( grid[j-1][k]==0) {
						grid[j-1][k]=grid[j][k];
						grid[j][k]=0;
					}
				}
			}
		}
	}

	/*
	 *Name: moveDown
	 *Purpose:This is the helper method for move() method
	 Moves the tiles in downwards and
	 *tiles that are combined already cannot combine again
	 *Parameter: none
	 *Return: void
	 */

	private void moveDown() {
		//this for loop closes the gap between non-zero tiles
		//downwards
		for(int i = 0; i<grid.length; i++) {
			for(int j = 0; j<grid.length-1; j++) {
				for (int k = 0; k<grid[j].length; k++) {
					if( grid[j+1][k]==0) {
						grid[j+1][k]=grid[j][k];
						grid[j][k]=0;
					}
				}
			}
		}

		//this for loop combines tiles next to each other
		//downwards
		for(int i = grid.length-1; i>0; i--) {
			for(int j = 0; j<grid[i].length; j++) {
				if(grid[i][j]== grid[i-1][j]) {
					grid[i][j]=grid[i][j]*2;
					score+=grid[i][j];
					grid[i-1][j]=0;
				}
			}
		}

		//this for loop closes the gap of any remaining tiles
		//downwards
		for(int i = 0; i<grid.length; i++) {
			for(int j = 0; j<grid.length-1; j++) {
				for (int k = 0; k<grid[j].length; k++) {
					if( grid[j+1][k]==0) {
						grid[j+1][k]=grid[j][k];
						grid[j][k]=0;
					}
				}
			}
		}
	}

	/*
	 *Name: move
	 *Purpose: moves the tile in the given direction
	 *by passing it into the corresponding helper method
	 *with the given direction
	 *Param: 
	 *@param Direction direction, this represents
	 *the direction passed to this method to move
	 *the tile
	 *Return: boolean, this is if the moves were implemented 
	 *corectly
	 */
	// Performs a move Operation
	public boolean move(Direction direction) {
		if(direction==Direction.LEFT) {
			moveLeft();
			return true;
		}

		if(direction==Direction.RIGHT) {
			moveRight();
			return true;
		}

		if(direction==Direction.UP) {
			moveUp();
			return true;
		}

		if(direction==Direction.DOWN) {
			moveDown();
			return true;
		}

		return false;
	}

	/*
	 *Name: isGameOver
	 *Purpose: checks the board to see if the player can 
	 *move, otherwise it will return false meaning that
	 *the game is over.
	 *Parameter: none
	 *Return: boolean, representing whther or not the game 
	 *is over, if it is, it will return true
	 *other wise, it returns false
	 *
	 */
	// Check to see if we have a game over
	public boolean isGameOver() {
		//makes sure the none of the tiles can move in
		//any direction
		if(canMoveLeft()==false && canMoveRight()==false 
				&& canMoveUp()==false && canMoveDown()==false)
			return true;

		else {
			return false;
		}
	}


	/*
	 *Name: canMoveLeft
	 *Purpose:This is the helper method for CanMove() method 
	 *checks to see if the player can move left
	 *Parameter:none
	 *Return: boolean, this is returned true if the
	 *tile can be moved in the left direction
	 *otherwise it will be returned false
	 */
	public boolean canMoveLeft() {
		//checks to see if the tile to the left matches
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length-1; j++) {
				if( grid[i][j]!=0 && grid[i][j]==grid[i][j+1] )
					return true;
			}
		}
		//checks to see to there is space to the left of a tile
		for(int i = 0; i < grid.length; i++) {
			for(int j = grid[i].length-1; j>0; j--) {
				if( grid[i][j]!=0 && grid[i][j-1]==0 )
					return true; 		
			}
		}
		return false;
	}


	/*
	 *Name: canMoveRight
	 *Purpose: This is the helper method for CanMove() method
	 *checks to see if the player can move right
	 *Parameter: none
	 *Return: boolean, this represents whether or not 
	 *the tiles can be moved in the right direction
	 *true if they can, false if not
	 */
	public boolean canMoveRight() {
		//checks to see if the tile to the right matches 
		for(int i = 0; i < grid.length; i++) {
			for(int j = grid[i].length-1; j>0; j--) {
				if( grid[i][j]!=0 && grid[i][j]==grid[i][j-1] )
					return true;
			}
		}
		//checks to see to there is space to the right of a tile
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length-1; j++) {
				if( grid[i][j]!=0 && grid[i][j+1]==0 )
					return true;
			}
		}
		return false;
	}

	/*
	 *Name: canMoveUp
	 *Purpose:This is the helper method for CanMove() method
	 *checks to see if the player can move up
	 *Parameter: none
	 *Return: boolean, this represents whether or not
	 *the tiles can be moved up
	 *true if they can, false if not
	 */
	public boolean canMoveUp() {
		//checks to see if the tile above matches
		for(int i = 0; i < grid.length-1; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if(grid[i][j]!=0 && grid[i][j]==grid[i+1][j])
					return true;
			}
		}
		//checks to see to there is space above a tile
		for(int i = grid.length-1; i>0; i--) {
			for(int j = 0; j < grid[i].length; j++) {
				if(grid[i][j]!=0 && grid[i-1][j]==0)
					return true;
			}
		}
		return false;
	}

	/*
	 *Name: canMoveDown
	 *Purpose: This is the helper method for canMove() method
	 *checks to see if the player can move down
	 *Parameter:none
	 *Return:boolean, this represents whther or not the tiles
	 *can be moved up
	 *true if they can, false if not
	 *
	 */
	public boolean canMoveDown() {
		//checks to see if the tile under matches
		for(int i = grid.length-1; i>0; i--) {
			for(int j = 0; j < grid[i].length; j++) {
				if(grid[i][j]!=0 && grid[i][j]== grid[i-1][j])
					return true;
			}
		}
		//checks to see to there is space under a tile
		for(int i = 0; i < grid.length-1; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if(grid[i][j]!=0 && grid[i+1][j]==0)
					return true;
			}
		}
		return false;
	}




	/*
	 *Name: canMove
	 *Purpose: passes the given direction into helper methods
	 *that checks to see if the tiles can be move into the given direction
	 *Parameter:
	 *@param Direction direction, this is the direction that is inputed
	 *by the player
	 *Return:boolean, it returns true if the tiles can
	 *move in the passed in direction otherwise, it returns false
	 *
	 */
	public boolean canMove(Direction direction) {
		boolean correctMove = false;	
		if( direction == Direction.LEFT)
			correctMove = canMoveLeft();

		if( direction == Direction.RIGHT)
			correctMove = canMoveRight();

		if( direction == Direction.UP) 
			correctMove = canMoveUp();

		if( direction == Direction.DOWN)
			correctMove = canMoveDown();

		return correctMove;
	}

	// Return the reference to the 2048 Grid
	public int[][] getGrid() {
		return grid;
	}

	// Return the score
	public int getScore() {
		return score;
	}

	@Override
		public String toString() {
			StringBuilder outputString = new StringBuilder();
			outputString.append(String.format("Score: %d\n", score));
			for (int row = 0; row < GRID_SIZE; row++) {
				for (int column = 0; column < GRID_SIZE; column++)
					outputString.append(grid[row][column] == 0 ? "    -" :
							String.format("%5d", grid[row][column]));

				outputString.append("\n");
			}
			return outputString.toString();
		}
}
