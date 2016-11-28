package javafxdemo;
import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	
	public ArrayList<String> getCategoryName(){
		ArrayList<String> categoryNames = new ArrayList<String>();
		categoryNames.add("Category 1");
		categoryNames.add("Category 2");
		categoryNames.add("Category 3");
		categoryNames.add("Category 4");
		categoryNames.add("Category 5");
		return categoryNames;
	}
	
	public String getPlayerTurn(){
		return "This Person's Turn";
	}
	
	public String getPlayerScore(){
		return "0";
	}
	public int getCategoryNum(){
		return 5;
	}
	
	public void createLabels(ArrayList<Label> catArr, GridPane gridpane){
	    for(int i = 0; i < getCategoryNum(); i++)
	    {
	    	catArr.add(new Label(getCategoryName().get(i)));
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
		thebuttons.add(new Button("400"));
		thebuttons.add(new Button("500"));
		
		for(int i = 0; i < 5; i++){
			thebuttons.get(i).setFont(new Font(25));
		}
		return thebuttons;
	}
	
	public void createButtons(ArrayList<ArrayList <Button>> buttons, GridPane gridpane, Stage thestage){
		for(int i = 0; i < getCategoryNum(); i ++){
			buttons.add(makeButtons());
			for(int j = 0; j < 5; j++)
			{
				GridPane.setHalignment(buttons.get(i).get(j), HPos.CENTER);
				gridpane.add(buttons.get(i).get(j), i, j+1);
				
				Button btn = buttons.get(i).get(j);
				buttons.get(i).get(j).addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						btn.setDisable(true);
						final Stage dialog = new Stage();
						dialog.initStyle(StageStyle.UNDECORATED);
						dialog.setTitle("Question");
		                dialog.initModality(Modality.APPLICATION_MODAL);
		                dialog.initOwner(thestage);
		                VBox dialogVbox = new VBox(20);
		                
		                //temporary//////////
		                Button tempbutton = new Button("exit");
		                tempbutton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent mouseEvent) {
								dialog.close();
							}
						});
		                dialogVbox.getChildren().add(new Text("This is the question"));
		                dialogVbox.getChildren().add(tempbutton);
		                ////////////////////
		                
		                Scene dialogScene = new Scene(dialogVbox, 500, 300);
		                dialog.setScene(dialogScene);
		                dialog.show();
						//enter control
					}
				});
			}
		}
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
	    Label playerTurn = new Label(getPlayerTurn());
	    playerTurn.setFont(new Font(30));
	    playerTurn.setTranslateY(-350);
	    
	    Label playerScore = new Label(getPlayerScore());
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
