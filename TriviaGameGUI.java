

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class TriviaGameGUI {
	StackPane root;
	private Label playerTurn;
	private Label playerScore;
	private static ArrayList<String> categoryNames;
	private String answerString;
	private static int numberOfTopics = 0;
	private int topicNumber = 0;
	private static int playerCount = 0;
	private ArrayList<Button> theTopics;
	private ArrayList<Button> players;
	
	/////Stuff Rory Changed////
	
	public boolean buttonPushed;
	public int currentButtonId;
	public boolean questionAnswered;
	public int buttonCounter = 0;
	
	//////////////////////////
	
	public ArrayList<String> getAllTopicNames(){
		return TriviaGlue.getAllTopicNames();
	}
	
	public static int getNumberOfTopics(){
		return numberOfTopics;
	}
	
	public void decrementTopic(){
		topicNumber--;
	}
	
	public static ArrayList<String> getCategoryName(){
		return categoryNames;
	}
	
	public static int getPlayerCount(){
		return playerCount;
	}
	
	public String getPlayerTurn(){
		return TriviaGlue.getPlayerTurn();
	}
	
	public String getPlayerScore(){
		return TriviaGlue.getPlayerScore();
	}
	public int getCategoryNum(){
		return categoryNames.size();
	}
	
	public String getQuestion(){
		return TriviaGlue.getQuestion();
	}
	
	public void retrieveAnswer(String inanswer){
		answerString = inanswer;
	}
	
	public String getAnswer(){
		return answerString;
	}
	
	public String getMessage(){
		return TriviaGlue.answerChecking();
	}

	public void updateScore(){
		playerScore.setText("Score: " + getPlayerScore());
	}
	
	public void updatePlayer(){
		playerTurn.setText("Player " + getPlayerTurn());
		playerScore.setText("Score: " + getPlayerScore());
	}
	
	public String getWinner(){
		return TriviaGlue.getWinner();
	}
	
	public String getWinningScore(){
		return TriviaGlue.getWinnerScore();
	}
	
	public ArrayList<Integer> getAllScores(){
		return TriviaGlue.getAllScores();
	}
	
	public void createLabels(ArrayList<Label> catArr, GridPane gridpane){
	    for(int i = 0; i < getCategoryNum(); i++)
	    {
	    	catArr.add(new Label(categoryNames.get(i)));
	    	catArr.get(i).setFont(Font.font("Verdana",FontWeight.BOLD, 20));
	    	GridPane.setHalignment(catArr.get(i), HPos.CENTER);
	    	gridpane.add(catArr.get(i), i, 0);
	    	ColumnConstraints column = new ColumnConstraints(200);
	        gridpane.getColumnConstraints().add(column);
	    }
	}
	
	public ArrayList<Button> makeButtons()
	{
		ArrayList<Button> thebuttons = new ArrayList<Button>();
		thebuttons.add(new Button("100"));
		thebuttons.add(new Button("200"));
		thebuttons.add(new Button("300"));
		
		for(int i = 0; i < 3; i++){
			thebuttons.get(i).setFont(new Font(25));
			thebuttons.get(i).setId(Integer.toString(buttonCounter));
			buttonCounter++;
		}
		return thebuttons;
	}
	
	public void createButtons(ArrayList<ArrayList <Button>> buttons, GridPane gridpane, Stage thestage){
		for(int i = 0; i < getCategoryNum(); i ++){
			buttons.add(makeButtons());
			for(int j = 0; j < 3; j++)
			{
				GridPane.setHalignment(buttons.get(i).get(j), HPos.CENTER);
				gridpane.add(buttons.get(i).get(j), i, j+1);
				
				Button btn = buttons.get(i).get(j);
				buttons.get(i).get(j).addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						
						////Stuff Rory Changed////
						buttonPushed = true;
						questionAnswered = false;
						currentButtonId = Integer.parseInt(btn.getId());
						//////////////////////////////
						
						btn.setDisable(true);
						Stage questionStage = new Stage();
						questionStage.initStyle(StageStyle.UNDECORATED);
						questionStage.setTitle("Question");
						questionStage.initModality(Modality.APPLICATION_MODAL);
						questionStage.initOwner(thestage);
		                VBox questionVBox = new VBox(20);
		                questionVBox.setPadding(new Insets(10, 50, 50, 50));
		                questionVBox.setPrefSize(500, 500);
                
		                questionScreen(questionVBox, questionStage, thestage);
		                
		                Scene questionScene = new Scene(questionVBox, 500, 500);
		                questionStage.setScene(questionScene);
		                questionStage.show();
					}
				});
			}
		}
	}
	
	public void questionScreen(VBox secondScreen, Stage thestage, Stage mainstage){
		Text question = new Text(getQuestion());
		question.setWrappingWidth(450);
		question.setFont(new Font(20));
		TextField answer = new TextField();
		answer.setPromptText("Enter your answer.");
		Text message = new Text("If you gave a right answer, but was wrong because of wording, click the challenge button to earn back your points.");
		message.setFont(new Font(20));
		message.setWrappingWidth(450);
		
		 Button close = new Button("Close");
		 close.setTranslateX(350);
		 close.setAlignment(Pos.BOTTOM_RIGHT);
	     close.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					updatePlayer();
					thestage.close();
					if(TriviaGlue.gameOver() == true){
		            	winScreen(mainstage);
		            }
				}
			});
	        
	 	Button challenge = new Button("Challenge");
	 	challenge.setAlignment(Pos.BOTTOM_LEFT);
	 	 challenge.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					TriviaGlue.challenged();
					updateScore();
					thestage.close();
					if(TriviaGlue.gameOver() == true){
		            	winScreen(mainstage);
		            }
				}
			});
		Button submit = new Button("Submit");
        submit.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if ((answer.getText() != null && !answer.getText().isEmpty())) {
					submit.setDisable(true);
		            retrieveAnswer(answer.getText());
		            /////Stuff Rory Changed/////
		            
		            buttonPushed = false;
		            questionAnswered = true;
		            
		            /////////////////////
		            String theMessage = getMessage();
		            message.setText(theMessage);
		            updateScore();
		            if(theMessage != "You are correct!")
		            	secondScreen.getChildren().add(challenge);
		            secondScreen.getChildren().add(close);
		        } else {
		            message.setText("You have not answered the question.");
		        }
			}
		});
      
		secondScreen.getChildren().add(question);
		secondScreen.getChildren().add(answer);
		secondScreen.getChildren().add(submit);
		secondScreen.getChildren().add(message);
	}
	
	public void winScreen(Stage thestage){
		Stage winStage = new Stage();
		winStage.setTitle("Game Over");
		VBox winVBox = new VBox(20);
        winVBox.setPadding(new Insets(10, 50, 50, 50));
        
		Label winner = new Label("The winner is: " + getWinner());
		Label winnerScore = new Label(getWinningScore());
		winner.setFont(new Font(30));
		winner.setTranslateY(-350);
		winnerScore.setFont(new Font(30));
		winnerScore.setTranslateY(-300);
		
		int yValue = -300;
		ArrayList<Text> scoreList = new ArrayList<Text>();
		for(int i = 0; i < getAllScores().size(); i++)
		{
			scoreList.add(new Text("Player " + (i+1) + ", Score: " + getAllScores().get(i)));
			scoreList.get(i).setFont(new Font(20));
			yValue += 50;
			scoreList.get(i).setTranslateY(yValue);
		}
		
		Button backToTitle = new Button("Title Screen");
		backToTitle.setTranslateX(600);
		backToTitle.setTranslateY(350);
		backToTitle.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				winStage.close();
				LearnJavaGame newGame = new LearnJavaGame();
				newGame.start(thestage);
			}
		});
		
		root = new StackPane();
		root.getChildren().add(winner);
		root.getChildren().add(winnerScore);
		root.getChildren().add(backToTitle);
		for(int i = 0; i < scoreList.size(); i++){
			root.getChildren().add(scoreList.get(i));
		}
		
		Scene winningScene = new Scene(root, 1366, 768);
        winStage.setScene(winningScene);
        winStage.show();
	}
	
	//---------------------------------------------------
	//	Disable and Enable methods
	//		The purpose of these methods are for error
	//		checking so that buttons in the opening
	//		screen are not pushed early or late
	//---------------------------------------------------
	
	public void disableTheTopics(){
		for(int i = 0; i <theTopics.size(); i++){
			if(theTopics.get(i).isDisabled() == false){
				theTopics.get(i).setDisable(true);
			}
		}
	}
	
	public void disablePlayers(){
		for(int i = 0; i <players.size(); i++){
			if(players.get(i).isDisabled() == false){
				players.get(i).setDisable(true);
			}
		}
	}
	
	public void enableTheTopics(){
		for(int i = 0; i <theTopics.size(); i++){
				theTopics.get(i).setDisable(false);
		}
	}
	
	public void enablePlayers(){
		for(int i = 0; i <players.size(); i++){
				players.get(i).setDisable(false);
		}
	}

	//---------------------------------------------------
	//	Opening Screen
	//		The opening screen is used to initialize
	//		the gameboard before gameplay starts.
	//---------------------------------------------------
	public void openingScreen(Stage thestage){
		int yValue;
		Label info = new Label("Here is a list of all the topics: ");
		info.setFont(new Font(30));
		info.setTranslateY(-350);
		yValue = -300;
		String stringList = "";
		//ArrayList<Label> list = new ArrayList<Label>();\
		int counter = 0;
		for(int i = 0; i <getAllTopicNames().size(); i++){
			if(stringList == "")
				stringList = getAllTopicNames().get(i);
			else
				stringList = stringList + ", " + getAllTopicNames().get(i);
		/*	if(i%6 == 0 && i!=0){
				list.add(new Label(stringList));
				list.get(counter).setTranslateY(yValue);
				list.get(counter).setFont(new Font(20));
				stringList = "";
				yValue+=50;
				counter++;
			}*/
		}
		
		Text list = new Text(stringList);
		list.setTextAlignment(TextAlignment.CENTER);
		list.setTranslateY(yValue);
		list.setFont(new Font(20));
		list.setWrappingWidth(1300);
		
		yValue+=150;
		Label prompt = new Label("How many topics would you like to use?");
		prompt.setFont(new Font(30));
		prompt.setTranslateY(yValue);
		
		yValue+=50;
		Button oneTopic = new Button("1");
		oneTopic.setAlignment(Pos.CENTER);
		oneTopic.setTranslateX(-100);
		oneTopic.setTranslateY(yValue);
		Button twoTopics = new Button("2");
		twoTopics.setAlignment(Pos.CENTER);
		twoTopics.setTranslateX(-50);
		twoTopics.setTranslateY(yValue);
		Button threeTopics = new Button("3");
		threeTopics.setTranslateY(yValue);
		Button fourTopics = new Button("4");
		fourTopics.setAlignment(Pos.CENTER);
		fourTopics.setTranslateX(50);
		fourTopics.setTranslateY(yValue);
		
		oneTopic.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				numberOfTopics = 1;
				topicNumber = 1;
				oneTopic.setDisable(true);
				twoTopics.setDisable(true);
				threeTopics.setDisable(true);
				fourTopics.setDisable(true);
				enableTheTopics();
			}
		});
		twoTopics.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				numberOfTopics = 2;
				topicNumber = 2;
				oneTopic.setDisable(true);
				twoTopics.setDisable(true);
				threeTopics.setDisable(true);
				fourTopics.setDisable(true);
				enableTheTopics();
			}
		});
		threeTopics.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				numberOfTopics = 3;
				topicNumber = 3;
				oneTopic.setDisable(true);
				twoTopics.setDisable(true);
				threeTopics.setDisable(true);
				fourTopics.setDisable(true);
				enableTheTopics();
			}
		});
		fourTopics.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				numberOfTopics = 4;
				topicNumber = 4;
				oneTopic.setDisable(true);
				twoTopics.setDisable(true);
				threeTopics.setDisable(true);
				fourTopics.setDisable(true);
				enableTheTopics();
			}
		});
		
		yValue+=50;
		Label prompt2 = new Label("Please Choose Your Topics: ");
		prompt2.setFont(new Font(30));
		prompt2.setTranslateY(yValue);
		
		int xValue = 0;
		yValue = yValue+50;
		boolean wentRight = false;
		theTopics = new ArrayList<Button>();
		categoryNames = new ArrayList<String>();
		for(int i = 0; i <getAllTopicNames().size(); i++){
			theTopics.add(new Button(getAllTopicNames().get(i)));
			theTopics.get(i).setAlignment(Pos.CENTER);
			if(i==0){
				//let the button center
			}
			else if(xValue< -300){
				yValue = yValue+50;
				xValue = 0;
				wentRight = false;
			}
			else if(wentRight == false){
				xValue = (xValue*-1)+200;
				wentRight = true;
			}
			else{
				xValue =(xValue*-1);
				wentRight=false;
			}
			theTopics.get(i).setTranslateY(yValue);
			theTopics.get(i).setTranslateX(xValue);
			Button btn = theTopics.get(i);
			theTopics.get(i).addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					topicNumber--;
					btn.setDisable(true);
					categoryNames.add(btn.getText());
					if(topicNumber==0){
						enablePlayers();
						disableTheTopics();
					}
				}
			});
		}
		
		Label prompt3 = new Label("How many people will be playing?");
		prompt3.setFont(new Font(30));
		prompt3.setTranslateY(yValue+50);
		
		int xValue2 = -150;
		players = new ArrayList<Button>();
		for(int i = 1; i <5; i++){
			String playerString = "" +i;
			players.add(new Button(playerString));
			
			xValue2 +=50;
			players.get(i-1).setTranslateX(xValue2);
			players.get(i-1).setTranslateY(yValue+100);
			
			int theplayer = i;
			Button btn = players.get(i-1);
			players.get(i-1).addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					playerCount = theplayer;
					TriviaGlue.initializeGame();
						triviaScreen();
						thestage.close();
				}
			});
		}		
		
		//disables buttons so that they won't be pushed ahead of time
		disableTheTopics();
		disablePlayers();
		
		root = new StackPane();
		root.setStyle("-fx-background-color: white");
		root.getChildren().add(info);
		root.getChildren().add(list);
		//for(int i = 0; i < list.size(); i++)
		//	root.getChildren().add(list.get(i));
		root.getChildren().add(prompt);
		root.getChildren().add(oneTopic);
		root.getChildren().add(twoTopics);
		root.getChildren().add(threeTopics);
		root.getChildren().add(fourTopics);
		root.getChildren().add(prompt2);
		for(int i =0; i <theTopics.size(); i++){
			root.getChildren().add(theTopics.get(i));
		}
		root.getChildren().add(prompt3);
		
		for(int i = 0; i < players.size(); i++){
			root.getChildren().add(players.get(i));
		}
		
	}
	
	public void triviaScreen(){
		//preparing the table of categories and buttons
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Trivia Game");
		// loading images
				//Image image = new Image("javaGameBackground.jpg");
				
				/*------------------------------------------------------------*/

				// preparing background
				//ImageView background = new ImageView();
				//background.setImage(image);
				
				/*------------------------------------------------------------*/
				GridPane gridpane = new GridPane();
				gridpane.setMaxSize(200,200);
			    gridpane.setPadding(new Insets(getCategoryNum()));
			    gridpane.setHgap(20);
			    gridpane.setVgap(20);

			    ArrayList<Label> categories = new ArrayList<Label>();
			    ArrayList<ArrayList <Button>> buttons = new ArrayList<ArrayList <Button>>();
			    
			    createLabels(categories, gridpane);
			    createButtons(buttons, gridpane, primaryStage);
			    
			    /*------------------------------------------------------------*/
			    //Player's Turn
			    playerTurn = new Label("Player " + getPlayerTurn());
			    playerTurn.setFont(new Font(30));
			    playerTurn.setTranslateY(-350);
			    
			    playerScore = new Label("Score: " + getPlayerScore());
			    playerScore.setFont(new Font(30));
			    playerScore.setTranslateY(-300);
			    
			    /*------------------------------------------------------------*/
			    //adding stuff to show
				root = new StackPane();
				//root.getChildren().add(background);
				root.getChildren().add(playerTurn);
				root.getChildren().add(playerScore);
				root.setStyle("-fx-background-color: lightsteelblue");
				StackPane.setAlignment(gridpane, Pos.CENTER);
				root.getChildren().add(gridpane);
				
				Scene triviaScene = new Scene(root, 1366, 768);
				primaryStage.setScene(triviaScene);
				primaryStage.show();
				
	}
	
	public void start(Stage primaryStage) {
		//initializing
		primaryStage.setTitle("Initializing game...");
		openingScreen(primaryStage);
		
	}
}