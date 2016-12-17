

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.geometry.Orientation;

public class LearnJavaGame extends Application {
	Stage thestage;
	ImageView background;
	Button tileGame;
	Button triviaGame;
	Scene tileGameScene;
	static Scene triviaGameScene;
	private ScrollBar sc = new ScrollBar();
        
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
		tileGame = new Button("Operations Game");
		tileGame.setTranslateY(-100);
		triviaGame = new Button("Trivia Game"); 
		
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
		primaryStage.setScene(new Scene(root, 1366, 768));
	}
	
	public void tileGame(Stage primaryStage) {
		//tileGame display
		TileGameGUI game = new TileGameGUI();
		game.start(primaryStage);
		tileGameScene = new Scene(game.root, 1366,768);
	}
	
	public void triviaGame(Stage primaryStage) {
		//TriviaGlue.initializeGame();
		TriviaGlue.initializeTopics();
		TriviaGlue.theView = new TriviaGameGUI();
		TriviaGlue.theView.start(primaryStage);
		triviaGameScene = new Scene(TriviaGlue.theView.root, 1366,768);
		sc.setTranslateX(triviaGameScene.getWidth()/2-sc.getWidth()/2);
        sc.setMin(0);
        sc.setOrientation(Orientation.VERTICAL);
        sc.setMax(700);
        
        sc.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            	TriviaGlue.theView.root.setLayoutY(-new_val.doubleValue());
            }
        });
		TriviaGlue.theView.root.getChildren().add(sc);
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
    }
}

