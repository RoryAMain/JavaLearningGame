package javafxdemo;
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
	private ArrayList<String> categoryNames;
	private String questionString;
	private String winningPlayer;
	private String answerString;
	
	/////Stuff Rory Changed////
	
	private String correctAnswerString;
	public boolean buttonPushed;
	public int currentButtonId;
	public boolean questionAnswered;
	public int buttonCounter = 0;
	
	public void setCorrectAnswer(String answerIn)
	{
		correctAnswerString = answerIn;
	}
	
	/////////////////////////
	
	public void getCategoryName(ArrayList<String> inNames){
		categoryNames = new ArrayList<String>(inNames);
		/*ArrayList<String> categoryNames = new ArrayList<String>();
		categoryNames.add("Category 1");
		categoryNames.add("Category 2");
		categoryNames.add("Category 3");
		categoryNames.add("Category 4");
		categoryNames.add("Category 5");
		return categoryNames;*/
	}
	
	public void setPlayerTurn(String turn){
		playerTurn.setText(turn);
		//return "This Person's Turn";
	}
	
	public void setPlayerScore(String score){
		playerScore.setText(score);
		//return "0";
	}
	public int getCategoryNum(){
		return categoryNames.size();
	}
	
	public void setWinningPlayer(String player){
		winningPlayer = player;
	}
	
	public void getQuestion(String inquestion){
		questionString = inquestion;
		//return "I am asking a question.";
	}
	
	public void retrieveAnswer(String inanswer){
		answerString = inanswer;
	}
	
	public String getAnswer(){
		return answerString;
	}

	/*public void updateScore(){
		playerScore.setText(getPlayerScore());
	}
	
	public void updatePlayer(){
		playerTurn.setText(getPlayerTurn());
		playerScore.setText(getPlayerScore());
	}*/
	
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
						final Stage questionStage = new Stage();
						questionStage.initStyle(StageStyle.UNDECORATED);
						questionStage.setTitle("Question");
						questionStage.initModality(Modality.APPLICATION_MODAL);
						questionStage.initOwner(thestage);
		                VBox questionVBox = new VBox(20);
		                questionVBox.setPadding(new Insets(10, 50, 50, 50));
                
		                questionScreen(questionVBox, questionStage);
		                
		                Scene questionScene = new Scene(questionVBox, 500, 300);
		                questionStage.setScene(questionScene);
		                questionStage.show();
					}
				});
			}
		}
	}
	
	public void questionScreen(VBox secondScreen, Stage thestage){
		Text question = new Text(questionString);
		question.setFont(new Font(20));
		TextField answer = new TextField();
		answer.setPromptText("Enter your answer.");
		Label message = new Label("");
		message.setFont(new Font(20));
		
		 Button close = new Button("Close");
		 close.setTranslateX(350);
		 close.alignmentProperty().set(Pos.BOTTOM_RIGHT);
	     close.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					thestage.close();
				}
			});
	        
		Button submit = new Button("Submit");
        submit.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if ((answer.getText() != null && !answer.getText().isEmpty())) {
		            retrieveAnswer(answer.getText());
		            //enter answer checking here
		            
		            /////Stuff Rory Changed/////
		            
		            buttonPushed = false;
		            questionAnswered = true;
		            
		            
		            
		            /////////////////////
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
	
	public void start(Stage primaryStage) {
		//game name
		primaryStage.setTitle("Trivia Game");
		
		// loading images
		Image image = new Image("javaGameBackground.jpg");
		
		/*------------------------------------------------------------*/

		// preparing background
		ImageView background = new ImageView();
		background.setImage(image);
		
		/*------------------------------------------------------------*/
		
		//preparing the table of categories and buttons
		GridPane gridpane = new GridPane();
		//gridpane.setGridLinesVisible(true);
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
	    playerTurn.setFont(new Font(30));
	    playerTurn.setTranslateY(-350);
	    
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
		
		
	}
}