
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyValue;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class TileGameGUI extends Application {

	//---------------------------------------
	//	Variables
	//--------------------------------------
	
	StackPane root;
	
	//	flags
	private static boolean intFlag = false;
	private boolean playerchosen = false;
	private boolean typechosen = false;
	private boolean errorcheck = false;

	// GUI objects
	private Image tile = new Image("Tile.png");
	private Text targetNum;
	private Text roundNum;
	private static Text message;
	private static Text message2;
	private static Button nextButton;
	private static Button nextRound;
	private static Button finishButton;
	private Button clearAll;
	private static final Integer STARTTIME = 120;
	private Timeline timeline;
	private Label timerLabel = new Label();
	private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);

	//	Logic components
	private static int playerCount = 0;
	private ArrayList<Button> players;
	private static ArrayList<Button> operandTiles;
	private static ArrayList<Boolean> remainingOperands;
	private static ArrayList<Button> operatorTiles;
	private static ArrayList<Boolean> remainingOperators;
	private static ArrayList<Button> solutionDeckButtons;
	private static ArrayList<String> solutionDeck;

	//----------------------------------------------
	//	Getters and setters
	//----------------------------------------------
	
	//	returns a boolean that lets the game know whether it should output only
	//	integers or doubles for the game's number values
	public static boolean getFlag() {
		return intFlag;  
	}
	
	//	returns a number that lets the game know how many players are playing
	public static int getPlayerCount() {
		return playerCount;
	}

	//	retrieves and returns the round number from the controller, TileGlue
	public String getRound() {
		return Integer.toString(TileGlue.getRound());
	}

	//	retrieves and returns the target number from the controller, TileGlue
	public String getTargetNum() {
		return Double.toString(TileGlue.getGoal());
	}

	//	retrieves and returns the current player turn from the controller, TileGlue
	public String getPlayerTurn() {
		return TileGlue.getPlayerTurn();
	}

	//	retrieves and returns an ArrayList<String> holding all the operands
	public static ArrayList<String> getOperands() {
		return TileGlue.getOperands();
	}

	//	retrieves and returns an ArrayList<String> holding all the operators
	public static ArrayList<String> getOperators() {
		return TileGlue.getOperators();
	}

	//	returns the solutionDeck created by the player 
	public static ArrayList<String> getSolutionDeck() {
		return solutionDeck;
	}
	
	//----------------------------------------------
	//	Initialize Methods
	//----------------------------------------------

	//	initializes the remainingOperand ArrayList<Boolean>
	//	This array list lets the game know which operands are still in play
	public static void remainingOperandInitialize() {
		remainingOperands = new ArrayList<Boolean>();
		for (int i = 0; i < operandTiles.size(); i++) {
			remainingOperands.add(true);
		}
	}

	//	initializes the remainingOperator ArrayList<Boolean>
	//	This array list lets the game know which operators are still in play
	public static void remainingOperatorInitialize() {
		remainingOperators = new ArrayList<Boolean>();
		for (int i = 0; i < operatorTiles.size(); i++) {
			remainingOperators.add(true);
		}
	}
	
//	Initializes the hand tiles for the player
	public void createTiles(ArrayList<Button> buttons, ArrayList<String> valuesFromController, Image tile,
			int yPosition) {
		int xPosition = -700;
		solutionDeck = new ArrayList<String>();
		solutionDeckButtons = new ArrayList<Button>();

		//this for loop initializes the player's hand
		for (int i = 0; i < valuesFromController.size(); i++) {
			xPosition += 110;
			buttons.add(new Button(valuesFromController.get(i)));
			buttons.get(i).setStyle(
					"-fx-background-color: rgba(0, 0, 0, 0.0); -fx-background-radius: 10;-fx-background-image: url('Tile.png'); -fx-font-size: 20;");
			buttons.get(i).setMaxWidth(107);
			buttons.get(i).setMaxHeight(127);
			buttons.get(i).setTranslateX(xPosition);
			buttons.get(i).setTranslateY(yPosition);

			Button btn = buttons.get(i);
			int currentNum = i;
			// Below disables all the operand buttons so that the player can't start
			// their solution deck with an operand
			if (yPosition == 150) {
				buttons.get(i).setDisable(true);
			}

			//	listener for each button created
			buttons.get(i).addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					if (yPosition == 300) {
						// Currently looking at an operand button
						//Below is error checking for divide by 0 or modulo 0
						if (btn.getText().equals("0"))	//checks if the current button is 0
							if (errorcheck == true)		//this boolean value will become true if the operand previously added is % or /
								message2.setText("Invalid. Please choose another number.");
							else {
								btn.setDisable(true);
								disableOperands();
								enableOperators();
								remainingOperands.set(currentNum, false);
								solutionDeck.add(btn.getText());
								solutionDeckButtons.get(findEmptyBtn()).setStyle(
										"-fx-background-color: rgba(0, 0, 0, 0.0); -fx-background-radius: 10;-fx-background-image: url('smallerTile.png'); -fx-font-size: 20;");
								solutionDeckButtons.get(findEmptyBtn()).setMaxWidth(76);
								solutionDeckButtons.get(findEmptyBtn()).setMaxHeight(91);
								solutionDeckButtons.get(findEmptyBtn()).setText(btn.getText());
							}
						else {
							btn.setDisable(true);
							disableOperands();
							enableOperators();
							remainingOperands.set(currentNum, false);
							solutionDeck.add(btn.getText());
							solutionDeckButtons.get(findEmptyBtn()).setStyle(
									"-fx-background-color: rgba(0, 0, 0, 0.0); -fx-background-radius: 10;-fx-background-image: url('smallerTile.png'); -fx-font-size: 20;");
							solutionDeckButtons.get(findEmptyBtn()).setMaxWidth(76);
							solutionDeckButtons.get(findEmptyBtn()).setMaxHeight(91);
							solutionDeckButtons.get(findEmptyBtn()).setText(btn.getText());
						}
					} else {
						// Currently looking at operator buttons
						btn.setDisable(true);
						if (btn.getText().equals("/") || btn.getText().equals("%")) // If buttons are either / or %, it makes the errorcheck flag true
							errorcheck = true;										// otherwise, it will be made false
						else
							errorcheck = false;
						remainingOperators.set(currentNum, false);
						disableOperators();
						enableOperands();
						solutionDeck.add(btn.getText());
						solutionDeckButtons.get(findEmptyBtn()).setStyle(
								"-fx-background-color: rgba(0, 0, 0, 0.0); -fx-background-radius: 10;-fx-background-image: url('smallerTile.png'); -fx-font-size: 20;");
						solutionDeckButtons.get(findEmptyBtn()).setMaxWidth(76);
						solutionDeckButtons.get(findEmptyBtn()).setMaxHeight(91);
						solutionDeckButtons.get(findEmptyBtn()).setText(btn.getText());
					}
					if (findEmptyBtn() == -1) {
						// There are no more buttons to click.
						// The game will now calculate the score and show the player.
						clearAll.setDisable(true);
						calculate();
					}
				}
			});
		}
	}
	
	// Initializes the solution deck so that they are already placed on the board, but are invisible.
		// Making them visible only involves changing they style and setting text.
		public static void initializeSolutionDeck() {
			int xPosition = -700;
			int yPosition = -50;
			for (int i = 0; i < getOperands().size() + getOperators().size(); i++) {
				solutionDeckButtons.add(new Button(""));
				solutionDeckButtons.get(i).setStyle("-fx-background-color: rgba(0, 0, 0, 0.0); -fx-background-radius: 10;");
				xPosition += 100;
				solutionDeckButtons.get(i).setTranslateX(xPosition);
				solutionDeckButtons.get(i).setTranslateY(yPosition);
			}
		}

		// Initializes and prepares the timer.
		public void timer() {
			timerLabel.textProperty().bind(timeSeconds.asString());
			timerLabel.setTextFill(Color.DARKSLATEGREY);
			timerLabel.setFont(new Font(25));
			timerLabel.setTranslateX(550);
			timerLabel.setTranslateY(-358);

			if (timeline != null) {
				timeline.stop();
			}
			timeSeconds.set(STARTTIME);
			timeline = new Timeline();
			timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(STARTTIME + 1), new KeyValue(timeSeconds, 0)));
			timeline.playFromStart();

			// timer listener. When the timer ends, it calls the timeUp() function which forces the player to forfeit the round.
			timeline.setOnFinished(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					disableOperators();
					disableOperands();
					timeUp();
					TileGlue.calculate();
					message.setText("Time's up! I'm afraid you forfeit the round.");
					message2.setText("Player " + (TileGlue.getPlayerTurn()) + "'s score: " + TileGlue.getGoalCompare());

					// show the "done" button to move on to next player
					nextButton.setDisable(false);
					nextButton.setText("Done");
					nextButton.setStyle(null);
				}
			});
		}
	
	//--------------------------------------
	//		Game Play Methods
	//--------------------------------------

	//	Pauses the timer and shows the player the results of their solution.
	//	This method calls the calculate() method from the controller and will 
	//	show the player their score
	public void calculate() {
		timeline.pause();
		TileGlue.calculate();
		message.setText("Player " + (TileGlue.getPlayerTurn()) + "'s solution: " + TileGlue.getSolutionValue());
		message2.setText("Player " + (TileGlue.getPlayerTurn()) + "'s score: " + TileGlue.getGoalCompare());

		// show the "done" button to move on to next player
		nextButton.setDisable(false);
		nextButton.setText("Done");
		nextButton.setStyle(null);
	}
	
	// Checks if the solutionDeckButton ArrayList has a button without any text on it
		// If all buttons have text, it will return -1
		public static int findEmptyBtn() {
			for (int i = 0; i < solutionDeckButtons.size(); i++) {
				if (solutionDeckButtons.get(i).getText() == "")
					return i;
			}
			return -1;
		}

		// This method is called after the timer runs out.
		// The solutionDeck is set with very high numbers and a score is calculated
		// Numbers are ridiculously high so that it will ensure that they player forfeit the round.
		public static void timeUp() {
			solutionDeck = new ArrayList<String>();
			for (int i = 0; i < solutionDeckButtons.size(); i++) {
				solutionDeckButtons.get(i).setText("");
				if (i % 2 == 0)
					solutionDeck.add("9999");
				else
					solutionDeck.add("+");
			}
		}

		// Clears the solution deck so that the player can start over.
		public static void clearSolutionDeck() {
			solutionDeck = new ArrayList<String>();
			remainingOperandInitialize();
			remainingOperatorInitialize();
			enableOperands();
			disableOperators();
			for (int i = 0; i < solutionDeckButtons.size(); i++) {
				solutionDeckButtons.get(i).setText("");
				solutionDeckButtons.get(i).setStyle("-fx-background-color: rgba(0, 0, 0, 0.0); -fx-background-radius: 10;");
			}
		}

	//-----------------------------------------------
	//	Disable and Enable button functions
	//		The point of these functions is so the 
	//		player is forced to put their solution
	//		in the correct order:
	//			operand, operator, operand, operator, operand... etc
	//-----------------------------------------------
	// Disables all operand buttons
	public static void disableOperands() {
		for (int i = 0; i < operandTiles.size(); i++) {
			if (operandTiles.get(i).isDisabled() == false) {
				operandTiles.get(i).setDisable(true);
			}
		}
	}

	// Disables all operator buttons
	public static void disableOperators() {
		for (int i = 0; i < operatorTiles.size(); i++) {
			if (operatorTiles.get(i).isDisabled() == false) {
				operatorTiles.get(i).setDisable(true);
			}
		}
	}

	// Enables all operand buttons
	public static void enableOperands() {
		for (int i = 0; i < operandTiles.size(); i++) {
			if (remainingOperands.get(i) == true) {
				operandTiles.get(i).setDisable(false);
			}
		}
	}

	// Enables all operator buttons
	public static void enableOperators() {
		for (int i = 0; i < operatorTiles.size(); i++) {
			if (remainingOperators.get(i) == true) {
				operatorTiles.get(i).setDisable(false);
			}
		}
	}
	
	//-------------------------------------
	//	Stage set up methods
	//-------------------------------------

	//	This screen initializes how many people will be playing, and asks the player if they want to play with ints or doubles.
	public void openingScreen(Stage thestage) {
		Label prompt = new Label("How many people will be playing?");
		prompt.setFont(new Font(30));
		prompt.setTranslateY(-50);

		// Prepares buttons 1 - 4 for the player to choose from
		int xValue2 = -150;
		players = new ArrayList<Button>();
		for (int i = 1; i < 5; i++) {
			String playerString = "" + i;
			players.add(new Button(playerString));

			xValue2 += 50;
			players.get(i - 1).setTranslateX(xValue2);

			int theplayer = i;
			players.get(i - 1).addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					playerCount = theplayer;
					playerchosen = true;
					if (playerchosen == true && typechosen == true) {
						TileGlue.initializeGame();
						tileGameScreen();
						thestage.close();
					}
				}
			});
		}

		// Prepares the prompt and two buttons: int, double
		Label intOrDouble = new Label("Would you like to play with ints or doubles?");
		intOrDouble.setFont(new Font(30));
		intOrDouble.setTranslateY(-150);
		Button intButton = new Button("Int");
		intButton.setTranslateY(-100);
		intButton.setTranslateX(-100);
		Button doubleButton = new Button("Double");
		doubleButton.setTranslateY(-100);
		intButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				intFlag = true;
				typechosen = true;
				if (playerchosen == true && typechosen == true) {
					TileGlue.initializeGame();
					tileGameScreen();
					thestage.close();
				}
			}
		});
		doubleButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				intFlag = false;
				typechosen = true;
				if (playerchosen == true && typechosen == true) {
					TileGlue.initializeGame();
					tileGameScreen();
					thestage.close();
				}
			}
		});

		// Makes the prompts and buttons viewable
		root = new StackPane();
		root.setStyle("-fx-background-color: lightsteelblue");
		root.getChildren().add(prompt);
		for (int i = 0; i < players.size(); i++) {
			root.getChildren().add(players.get(i));
		}
		root.getChildren().add(intButton);
		root.getChildren().add(doubleButton);
		root.getChildren().add(intOrDouble);

	}

	// This method will clear all tiles in the players hand
	// and will remove all tiles from the solutionDeckButtons
	public void clearHand() {
		for (int i = 0; i < operandTiles.size(); i++) {
			root.getChildren().remove(operandTiles.get(i));
		}

		for (int i = 0; i < operatorTiles.size(); i++) {
			root.getChildren().remove(operatorTiles.get(i));
		}
		for (int i = 0; i < solutionDeckButtons.size(); i++) {
			root.getChildren().remove(solutionDeckButtons.get(i));
		}
	}

	// The update method is called when the next player will play
	// This sets up the next player's target number, and hand.
	public void update() {
		clearAll.setDisable(false);
		targetNum.setText(getTargetNum());
		roundNum.setText("Round " + getRound());
		message.setText("It is Player " + TileGlue.getPlayerTurn() + " turn.");
		message2.setText("");
		timer();

		clearHand();

		/*------------------------------------------------------------*/

		// Operands
		operandTiles = new ArrayList<Button>();
		createTiles(operandTiles, getOperands(), tile, 300);

		/*------------------------------------------------------------*/

		// Operators
		operatorTiles = new ArrayList<Button>();
		createTiles(operatorTiles, getOperators(), tile, 150);

		/*------------------------------------------------------------*/
		remainingOperandInitialize();
		remainingOperatorInitialize();
		initializeSolutionDeck();

		//set for all to see
		for (int i = 0; i < getOperands().size(); i++) {
			root.getChildren().add(operandTiles.get(i));
		}

		for (int i = 0; i < getOperators().size(); i++) {
			root.getChildren().add(operatorTiles.get(i));
		}
		for (int i = 0; i < getOperands().size() + getOperators().size(); i++) {
			root.getChildren().add(solutionDeckButtons.get(i));
		}
	}

	// This is the initial tile gameplay screen.
	// This is where the standard layout and messages are initializes.
	public void tileGameScreen() {
		// game name
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Operations Game");

		// loading images
		// Image image = new Image("javaGameBackground.jpg");   //Background image not used, but will be here for anyone that wants to implement it
		tile = new Image("Tile.png");
		Image tileClicked = new Image("TileClicked.png");
		Image information = new Image("information.png");

		/*------------------------------------------------------------*/

		// preparing background
		//ImageView background = new ImageView();
		//background.setImage(image);

		/*------------------------------------------------------------*/

		// preparing information bar
		Label info = new Label();
		info.setGraphic(new ImageView(information));
		info.setTranslateY(-358);
		Text target = new Text(10, 50, "Target Number: ");
		target.setFont(new Font(20));
		target.setFill(Color.DIMGREY);
		target.setTranslateX(-600);
		target.setTranslateY(-358);

		targetNum = new Text(10, 50, getTargetNum());
		targetNum.setFont(new Font(25));
		targetNum.setFill(Color.DARKSLATEGREY);
		targetNum.setTranslateX(-475);
		targetNum.setTranslateY(-358);

		roundNum = new Text(10, 50, "Round " + getRound());
		roundNum.setFont(new Font(25));
		roundNum.setFill(Color.DARKSLATEGREY);
		roundNum.setTranslateY(-358);

		// initialize text objects to display messages to players
		message = new Text("It is Player " + TileGlue.getPlayerTurn() + " turn.");
		message.setFont(new Font(30));
		message.setTranslateY(-250);
		message.setFill(Color.DARKSLATEGREY);

		message2 = new Text("");
		message2.setFont(new Font(30));
		message2.setTranslateY(-200);
		message2.setFill(Color.DARKSLATEGREY);

		// return to title screen button
		// This button will remain hidden until the end of the game.
		Button backToTitle = new Button("Title Screen");
		backToTitle.setTranslateX(600);
		backToTitle.setTranslateY(350);
		backToTitle.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				primaryStage.close();
				LearnJavaGame newGame = new LearnJavaGame();
				newGame.start(primaryStage);
			}
		});

		// button that moves on to next player
		nextButton = new Button("");
		nextButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.0); -fx-background-radius: 10; -fx-font-size: 20;");
		nextButton.setTranslateY(-300);
		nextButton.setDisable(true);
		// nextButton Listener
		nextButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				nextButton.setDisable(true);
				nextButton.setText("");
				nextButton.setStyle(
						"-fx-background-color: rgba(0, 0, 0, 0.0); -fx-background-radius: 10; -fx-font-size: 20;");

				TileGlue.nextPlayer();

				int currentPlayer = Integer.parseInt(getPlayerTurn()) - 1;
				// if the current player doesn't go over the player count, it updates the board for the next player's turn
				if (currentPlayer < playerCount) {
					TileGlue.update();
					update();
				} else {
					// If all players have gone, this if statement checks if the next round is within 5 rounds
					// and then output the winner of the round
					if (TileGlue.getRound() - 1 < 4) {
						TileGlue.finishRound();
						int winnerIndex = TileGlue.getWinnerIndex();
						winnerIndex++;
						message.setText(
								"The winner for round " + TileGlue.getRound() + " is Player " + winnerIndex + "!");
						message2.setText("Ready for the next round?");
						root.getChildren().add(nextRound);
						nextRound.setText("Next Round");
						nextRound.setStyle(null);
					} else {
						// This is the final round. The final round's winner will be shown, and then will move on to showing
						// the overall winner for the game.
						TileGlue.finishRound();
						int winnerIndex = TileGlue.getWinnerIndex();
						winnerIndex++;
						message.setText(
								"The winner for round " + TileGlue.getRound() + " is Player " + winnerIndex + "!");
						message2.setText("Ready to see who won?");
						root.getChildren().add(finishButton);
						finishButton.setText("Done");
						finishButton.setStyle(null);
					}
				}
			}
		});

		// Prepares the finish button that the player will hit to show the overall winner of the game.
		finishButton = new Button("");
		finishButton
				.setStyle("-fx-background-color: rgba(0, 0, 0, 0.0); -fx-background-radius: 10; -fx-font-size: 20;");
		finishButton.setTranslateY(-300);
		
		// finish button listener
		//		Once this button is pushed, the overall winner will be revealed
		finishButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				root.getChildren().remove(finishButton);
				TileGlue.finishGame();
				int winnerIndex = TileGlue.getWinnerIndex();
				winnerIndex++;
				message.setText("Congratulations! The winner is Player " + winnerIndex + "!");
				message2.setText("");
				root.getChildren().add(backToTitle);
			}
		});

		//	Prepares the next round button that the player will hit to move to the next round.
		nextRound = new Button("");
		nextRound.setStyle("-fx-background-color: rgba(0, 0, 0, 0.0); -fx-background-radius: 10; -fx-font-size: 20;");
		nextRound.setTranslateY(-300);
		
		// next round listener
		// 		Once this button is pushed, it will increment the round and start a new one
		nextRound.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				root.getChildren().remove(nextRound);
				TileGlue.incrementRound();
				TileGlue.resetPlayer();
				TileGlue.update();
				update();

			}
		});

		//	Prepares the clear button that will allow the player to clear their solution deck so that
		//	they may add things from the beginning again.
		clearAll = new Button("");
		clearAll.setText("Clear");
		clearAll.setTranslateY(-300);
		clearAll.setTranslateX(-600);
		
		// Clear listener
		clearAll.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				clearSolutionDeck();
			}
		});

		// timer stuff
		Text timertext = new Text(10, 50, "Time Remaining: ");
		timertext.setFont(new Font(25));
		timertext.setFill(Color.DIMGREY);
		timertext.setTranslateX(440);
		timertext.setTranslateY(-358);
		Text seconds = new Text(10, 50, "seconds");
		seconds.setFont(new Font(25));
		seconds.setFill(Color.DIMGREY);
		seconds.setTranslateX(620);
		seconds.setTranslateY(-358);
		timer();

		/*------------------------------------------------------------*/

		// Operands
		operandTiles = new ArrayList<Button>();
		createTiles(operandTiles, getOperands(), tile, 300);

		/*------------------------------------------------------------*/

		// Operators
		operatorTiles = new ArrayList<Button>();
		createTiles(operatorTiles, getOperators(), tile, 150);

		/*------------------------------------------------------------*/
		// Initialization
		remainingOperandInitialize();
		remainingOperatorInitialize();
		initializeSolutionDeck();

		// Adds all components to stack pane
		root = new StackPane();
		// root.getChildren().add(background);
		root.setStyle("-fx-background-color: lightsteelblue");
		root.getChildren().add(info);
		root.getChildren().add(timerLabel);
		root.getChildren().add(seconds);
		root.getChildren().add(timertext);
		root.getChildren().add(target);
		root.getChildren().add(targetNum);
		root.getChildren().add(roundNum);
		root.getChildren().add(message);
		root.getChildren().add(message2);
		root.getChildren().add(nextButton);
		root.getChildren().add(clearAll);
		for (int i = 0; i < getOperands().size(); i++) {
			root.getChildren().add(operandTiles.get(i));
		}

		for (int i = 0; i < getOperators().size(); i++) {
			root.getChildren().add(operatorTiles.get(i));
		}
		for (int i = 0; i < getOperands().size() + getOperators().size(); i++) {
			root.getChildren().add(solutionDeckButtons.get(i));
		}

		Scene tileScene = new Scene(root, 1366, 768);
		primaryStage.setScene(tileScene);
		primaryStage.show();
	}

	public void start(Stage primaryStage) {
		// Opens opening screen where the player will initialize the game
		// by choosing how many players and whether to play with ints or doubles.
		primaryStage.setTitle("Initializing game...");
		openingScreen(primaryStage);
	}

}
