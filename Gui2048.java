/** Gui2048.java */
/** PSA8 Release */

/*
 * Name: Peter Phan Student
 * Login:cs8bwahh
 * Date: 3/3/2016
 * File: Gui2048.java
 * Sources of Help: Introduction to JAVA Programming
 * 10th ed. Daniel Liang, Piazza
 * 
 * Class to create 2048 gameplay through JavaFx
 * 
 */


import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;



/*
 * Name:Gui2048
 * Purpose: Class used to implement 2048 gameplay
 * through Javafx
 */

public class Gui2048 extends Application
{
  private String outputBoard; // The filename for where to save the Board
  private Board board; // The 2048 Game Board

  private static final int TILE_WIDTH = 106;

  private static final int TEXT_SIZE_LOW = 55; // Low value tiles (2,4,8,etc)
  private static final int TEXT_SIZE_MID = 45; // Mid value tiles 
  //(128, 256, 512)
  private static final int TEXT_SIZE_HIGH = 35; // High value tiles 
  //(1024, 2048, Higher)

  // Fill colors for each of the Tile values
  private static final Color COLOR_EMPTY = Color.rgb(238, 228, 218, 0.35);
  private static final Color COLOR_2 = Color.rgb(238, 228, 218);
  private static final Color COLOR_4 = Color.rgb(237, 224, 200);
  private static final Color COLOR_8 = Color.rgb(242, 177, 121);
  private static final Color COLOR_16 = Color.rgb(245, 149, 99);
  private static final Color COLOR_32 = Color.rgb(246, 124, 95);
  private static final Color COLOR_64 = Color.rgb(246, 94, 59);
  private static final Color COLOR_128 = Color.rgb(237, 207, 114);
  private static final Color COLOR_256 = Color.rgb(237, 204, 97);
  private static final Color COLOR_512 = Color.rgb(237, 200, 80);
  private static final Color COLOR_1024 = Color.rgb(237, 197, 63);
  private static final Color COLOR_2048 = Color.rgb(237, 194, 46);
  private static final Color COLOR_OTHER = Color.BLACK;
  private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);

  private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242); 
  // For tiles >= 8

  private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101); 
  // For tiles < 8

  private StackPane endScreen; 
  private GridPane pane;
  private Scene scene;

  private Rectangle[][] rectArr;//Rectangle 2D Array to store rectangles displayed
  private Text[][] textArr;//Text 2D Array to store text displayed
  private Text score;//Text score to store the Score of 2048




  /*
   * Name: start
   * Purpose: sets up the game for the user to play
   * Parameter:
   * @param Stage primaryStage, this is where the pane
   * will be attached to forthe user to see the game
   * Return:void
   */
  @Override
    public void start(Stage primaryStage)
    {
      // Process Arguments and Initialize the Game Board
      processArgs(getParameters().getRaw().toArray(new String[0]));

      // Create the pane that will hold all of the visual objects
      pane = new GridPane();
      pane.setAlignment(Pos.CENTER);
      pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
      pane.setStyle("-fx-background-color: rgb(187, 173, 160)");
      // Set the spacing between the Tiles
      pane.setHgap(15); 
      pane.setVgap(15);



      //displays "2048" as the Game Title
      Text text2048 = new Text();
      text2048.setText("2048");
      text2048.setFont(Font.font("Serif", FontWeight.BOLD, 50));
      text2048.setFill(Color.BLACK);
      GridPane.setHalignment(text2048, HPos.CENTER);
      pane.add(text2048, 0, 0, 2, 1);

      //initilizes the score to be shown
      score = new Text();
      score.setText("Score: " + board.getScore());
      score.setFont(Font.font("Serif", FontWeight.BOLD, 40));
      score.setFill(Color.BLACK);
      GridPane.setHalignment(score, HPos.CENTER);
      pane.add(score, 2, 0, 2, 1);

      //creates 2D array to store reference to Rectangles
      //and Text that will be displayed
      rectArr = new Rectangle[board.GRID_SIZE][board.GRID_SIZE];
      textArr = new Text[board.GRID_SIZE][board.GRID_SIZE];



endScreen = new StackPane();
      endScreen.getChildren().add(pane);
      Scene scene = new Scene(endScreen);
      primaryStage.setTitle("Gui2048");
      primaryStage.setScene(scene);



      // for loop initizes rectArr[] and textArr[][]
      String number;// this represents the empty or numbered tile
      Rectangle tile;
      //used to make changes to  refernces of 
      //Rectangles in the rectArr
      Text tileNum;
      //used to make changes to refences of Text
      //in textArr
      for(int i = 0; i<board.getGrid().length; i++) {
	for(int j=0; j<board.getGrid().length; j++) {
	  rectArr[i][j]= new Rectangle();
	  tile = rectArr[i][j];

	  //binds the tile to the GridPane 
	  tile.widthProperty().bind(scene.widthProperty().divide
                                              (board.getGrid().length).subtract(board.GRID_SIZE*15));
//pane.widthProperty()
//	      .divide(this.board.GRID_SIZE)
//	      .subtract((board.GRID_SIZE-1)*15));
	  tile.heightProperty().bind(scene.heightProperty()
	      .divide(board.GRID_SIZE+1)
	      .subtract((board.GRID_SIZE-2)*15));
	  //fills the tile to the according to grid in board
	  tile.setFill(tileColor(board.getGrid()[i][j]));


	  textArr[i][j] = new Text();
	  tileNum = textArr[i][j];

	  //fills the tiles with numbers if there are any
	  if(board.getGrid()[i][j]==0) {
	    number="";
	  }
	  else{ number=""+board.getGrid()[i][j];
	  }

	  tileNum.setText(number);
	  tileNum.setFont(Font.font("Times New Roman", FontWeight.BOLD,
		TEXT_SIZE_LOW));
	  tileNum.setFill(COLOR_VALUE_DARK);
	  GridPane.setHalignment(tileNum, HPos.CENTER);

	  pane.add(tile, j, i+1);
	  pane.add(tileNum, j, i+1);

	}
      }



/*
      endScreen = new StackPane();
      endScreen.getChildren().add(pane);
      Scene scene = new Scene(endScreen);
      primaryStage.setTitle("Gui2048");
      primaryStage.setScene(scene);*/
      primaryStage.show();

      //used to handle key pressing events
      scene.setOnKeyPressed(new myKeyHandler());

    }



  /*
   * Name: showBoard
   * Purpose: show the board to the user and will display a 
   * separate screen when game is over
   * Parameter:none
   * Return:void
   */

  private void showBoard() {
    String number;// this represents the empty or numbered tile
    Rectangle tile;
    //used to make changes to  refernces of
    //Rectangles in the rectArr
    Text tileNum;
    //used to make changes to refences of Text
    //in textArr 
    Color textColor; 
    //used to set color of text
    int textSize;
    //used to set text size according to the int
    //in the board grid


    //updates the game after moves are made


    for(int i = 0; i<board.getGrid().length; i++) {
      for(int j=0; j<board.getGrid().length; j++) {

	tile= rectArr[i][j];
	tile.setFill(tileColor(board.getGrid()[i][j]));	



	tileNum = textArr[i][j];

	//if changes text according to the int
	//on the game grid
	if(board.getGrid()[i][j]==0) {
	  number = " ";
	  textColor = COLOR_OTHER;
	  textSize = TEXT_SIZE_LOW;
	}
	else {
	  number=""+board.getGrid()[i][j];

	  //if changes text color according to tile value
	  if(board.getGrid()[i][j]>=8) {
	    textColor = COLOR_VALUE_LIGHT;

	    // if statement changes text size according to tile value
	    if(board.getGrid()[i][j]>512) {
	      textSize = TEXT_SIZE_HIGH;
	    }
	    else {


	      if(board.getGrid()[i][j]>64) {
		textSize = TEXT_SIZE_MID;

	      }
	      else {
		textSize = TEXT_SIZE_LOW;
	      }

	    }

	  }
	  else {
	    textColor = COLOR_VALUE_DARK;
	    textSize = TEXT_SIZE_LOW;
	  }    
	}

	tileNum.setFont(Font.font("Times New Roman", FontWeight.BOLD,
	      textSize));
	tileNum.setText(number);
	tileNum.setFill(textColor);

      }    
    }


    //shows "Game Over!" on different screen when game is over

    if(board.isGameOver()==true) {
      Rectangle gameEnd = new Rectangle();
      gameEnd.widthProperty().bind(pane.widthProperty());
      gameEnd.heightProperty().bind(pane.heightProperty());
      gameEnd.setFill(COLOR_GAME_OVER);


      Text printGameOver = new Text();
      printGameOver.setText("GAME OVER!");
      printGameOver.setFont(Font.font("Georgia", FontWeight.BOLD,
	    50));
      printGameOver.setFill(COLOR_VALUE_DARK);
      endScreen.getChildren().add(gameEnd);
      endScreen.getChildren().add(printGameOver);	

    }

  }

  /*
   * Name: tileColor
   * Purpose: returns the color that the tile is supposed to be
   * Parameter:
   * @param int tileNum, the number on the tile 
   * Return:Color, the color used to fill the rectangle
   */

  public Color tileColor(int tileNum) {
    if(tileNum==0) { 
      return COLOR_EMPTY;
    }

    if(tileNum==2) {
      return COLOR_2;
    }

    if(tileNum==4) {
      return COLOR_4;
    }

    if(tileNum==8) {
      return COLOR_8;
    }

    if(tileNum==16) {
      return COLOR_16;
    }

    if(tileNum==32) {
      return COLOR_32;
    }

    if(tileNum==64) {
      return COLOR_64;
    }

    if(tileNum==128) {
      return COLOR_128;
    }

    if(tileNum==256) {
      return COLOR_256;
    }

    if(tileNum==512) {
      return COLOR_512;
    }

    if(tileNum==1024) {
      return COLOR_1024;
    }

    if(tileNum==2048) {
      return COLOR_2048;
    }

    else{
      return COLOR_OTHER;
    }
  }


  /*
   * Name: myKeyHandler
   * Purpose: to handle events in which buttons are pressed
   */

  private class myKeyHandler implements EventHandler<KeyEvent>
  {
    /*
     * Name: handle
     * Purpose: handles key pressing events like the arrow keys,
     * R and S
     * Parameter:
     * @param KeyEvent e, this is what button is pressed 
     * Return:void
     */
    @Override
      public void handle(KeyEvent e) {
	//while the game is not over, the key can be pressed
	if(board.isGameOver()==false) {
	  switch(e.getCode())
	  {
	    case UP:
	      if(board.canMoveUp()) {
		board.move(Direction.UP);

		board.addRandomTile();
	      }
	      break;
	    case DOWN: 
	      if(board.canMoveDown()) {
		board.move(Direction.DOWN);
		board.addRandomTile();	      
	      }
	      break;
	    case LEFT: 
	      if(board.canMoveLeft()) {
		board.move(Direction.LEFT);	      
		board.addRandomTile();
	      }
	      break;
	    case RIGHT: 
	      if(board.canMoveRight()) {
		board.move(Direction.RIGHT);
		board.addRandomTile();
	      }
	      break;
	    case R:
	      board.rotate(true);
	      break;
	    case S:
	      try {
		System.out.println("Saving Board to " + outputBoard);
		board.saveBoard(outputBoard);
	      } catch (IOException f) {
		System.out.println("saveBoard threw an Exception");
	      }
	      break;

	    default:break;
	  }
	  //displays the board and score as it gets updated
	  score.setText("Score: " + board.getScore());
	  showBoard();
	}
      }
  }






  /** DO NOT EDIT BELOW */

  // The method used to process the command line arguments
  private void processArgs(String[] args)
  {
    String inputBoard = null;   // The filename for where to load the Board
    int boardSize = 0;          // The Size of the Board

    // Arguments must come in pairs
    if((args.length % 2) != 0)
    {
      printUsage();
      System.exit(-1);
    }

    // Process all the arguments 
    for(int i = 0; i < args.length; i += 2)
    {
      if(args[i].equals("-i"))
      {   // We are processing the argument that specifies
	// the input file to be used to set the board
	inputBoard = args[i + 1];
      }
      else if(args[i].equals("-o"))
      {   // We are processing the argument that specifies
	// the output file to be used to save the board
	outputBoard = args[i + 1];
      }
      else if(args[i].equals("-s"))
      {   // We are processing the argument that specifies
	// the size of the Board
	boardSize = Integer.parseInt(args[i + 1]);
      }
      else
      {   // Incorrect Argument 
	printUsage();
	System.exit(-1);
      }
    }

    // Set the default output file if none specified
    if(outputBoard == null)
      outputBoard = "2048.board";
    // Set the default Board size if none specified or less than 2
    if(boardSize < 2)
      boardSize = 4;

    // Initialize the Game Board
    try{
      if(inputBoard != null)
	board = new Board(inputBoard, new Random());
      else
	board = new Board(boardSize, new Random());
    }
    catch (Exception e)
    {
      System.out.println(e.getClass().getName() + 
	  " was thrown while creating a " +
	  "Board from file " + inputBoard);
      System.out.println("Either your Board(String, Random) " +
	  "Constructor is broken or the file isn't " +
	  "formated correctly");
      System.exit(-1);
    }
  }

  // Print the Usage Message 
  private static void printUsage()
  {
    System.out.println("Gui2048");
    System.out.println("Usage:  Gui2048 [-i|o file ...]");
    System.out.println();
    System.out.println("  Command line arguments come in pairs of the "+ 
	"form: <command> <argument>");
    System.out.println();
    System.out.println("  -i [file]  -> Specifies a 2048 board that " + 
	"should be loaded");
    System.out.println();
    System.out.println("  -o [file]  -> Specifies a file that should be " + 
	"used to save the 2048 board");
    System.out.println("                If none specified then the " + 
	"default \"2048.board\" file will be used");  
    System.out.println("  -s [size]  -> Specifies the size of the 2048" + 
	"board if an input file hasn't been"); 
    System.out.println("                specified.  If both -s and -i" + 
	"are used, then the size of the board"); 
    System.out.println("                will be determined by the input" +
	" file. The default size is 4.");
  }
}
