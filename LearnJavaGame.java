package javafxdemo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LearnJavaGame extends Application {
	Stage thestage;
	ImageView background;
	Button tileGame;
	Button triviaGame;
	Button variableStackGame;
	Scene tileGameScene;
	Scene triviaGameScene;
        
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		//game name
		thestage=primaryStage;
		primaryStage.setTitle("Learn Java Game");
		
		// loading images
		Image image = new Image("javaGameBackground.jpg");
		
		/*------------------------------------------------------------*/

		// preparing background
		background = new ImageView();
		background.setImage(image);
		
		/*------------------------------------------------------------*/
		
		//game list
		tileGame = new Button("Tile Game");
		tileGame.setTranslateY(-100);
		triviaGame = new Button("Trivia Game"); 
		variableStackGame = new Button("Variable Stack Game");
		variableStackGame.setTranslateY(100);
		
		/*------------------------------------------------------------*/
		
		//button clicked listener
		tileGame.setOnAction(e-> ButtonClicked(e));
		triviaGame.setOnAction(e-> ButtonClicked(e));
		
		titleScreen(thestage);
		thestage.sizeToScene(); 
	    primaryStage.show();
	}
	
	public void titleScreen(Stage primaryStage)
	{
		//title screen display
		StackPane root = new StackPane();
		//root.getChildren().add(background);	
		root.setStyle("-fx-background-color: lightsteelblue");
		root.getChildren().add(tileGame);
		root.getChildren().add(triviaGame);
		root.getChildren().add(variableStackGame);
		primaryStage.setScene(new Scene(root, 1366, 768));
	}
	
	public void tileGame(Stage primaryStage) {
		//tileGame display
		TileGameGUI game = new TileGameGUI();
		game.start(primaryStage);
		tileGameScene = new Scene(game.root, 1366,768);
	}
	
	public void triviaGame(Stage primaryStage) {
		//triviaGame display
		TriviaGameGUI game = new TriviaGameGUI();
		game.start(primaryStage);
		triviaGameScene = new Scene(game.root, 1366,768);
	}
	
	public void ButtonClicked(ActionEvent e)
    {
        if (e.getSource()==tileGame)
        {
        	tileGame(thestage);
            thestage.setScene(tileGameScene);
        }
        else if(e.getSource()==triviaGame)
        {
        	triviaGame(thestage);
        	thestage.setScene(triviaGameScene);
        }
        else if(e.getSource()==variableStackGame){
        	//variableStackGame(thestage);
        	//thestage.setScene(variableStackGame);
        }
    }
}

