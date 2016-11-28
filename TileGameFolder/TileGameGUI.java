package javafxdemo;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
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
	
	StackPane root;
	 private static final Integer STARTTIME = 60;
	 private Timeline timeline;
	 private Label timerLabel = new Label();
	 private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);

	public String getValue() {
		//get values from model code
		return "button";
	}
	
	public String getOperator(){
		//get operator from model code
		return " + ";
	}
	
	public String getTargetNum()
	{
		//get target value from model code
		return "1000.0";
	}
	
	public int getTileAmount()
	{
		//get number of tiles from model code
		return 5;
	}

	public void createTiles(ArrayList<Button> buttons, ArrayList<Text> values, int tileAmount, Image tile, Image tileClicked, int yPosition) {
		int xPosition = -613 -110;
		
		for (int i = 0; i < tileAmount; i++) {
			xPosition += 110;
			buttons.add(new Button());
			buttons.get(i).setGraphic(new ImageView(tile));
			buttons.get(i).setStyle("-fx-background-color: rgba(0, 0, 0, 0.0); -fx-background-radius: 10;");
			buttons.get(i).setTranslateX(xPosition );
			buttons.get(i).setTranslateY(yPosition);

			if(yPosition == 300)
				values.add(new Text(10, 50, getValue()));
			else
				values.add(new Text(10, 50, getOperator()));
			values.get(i).setFont(new Font(20));
			values.get(i).setFill(Color.GREY);
			values.get(i).setTranslateX(xPosition );
			values.get(i).setTranslateY(yPosition);
			
			Button btn = buttons.get(i);
			Text value = values.get(i);

			buttons.get(i).addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					btn.setGraphic(new ImageView(tileClicked));
					value.setFill(Color.LIGHTGREY);
					System.out.println("Hello World!");
					//enter control
				}
			});
			buttons.get(i).addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					btn.setGraphic(new ImageView(tile));
					value.setFill(Color.GREY);
				}
			});
			values.get(i).addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					btn.setGraphic(new ImageView(tileClicked));
					value.setFill(Color.LIGHTGREY);
					System.out.println("Hello World!");
					//enter control
				}
			});
			values.get(i).addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					btn.setGraphic(new ImageView(tile));
					value.setFill(Color.GREY);
				}
			});
		}
	}
	
	public void timer(){
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
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(STARTTIME+1),
                new KeyValue(timeSeconds, 0)));
        timeline.playFromStart();
	}
	
	public void start(Stage primaryStage) {
		//game name
		primaryStage.setTitle("Tile Game");
		
		// loading images
		Image image = new Image("javaGameBackground.jpg");
		Image tile = new Image("Tile.png");
		Image tileClicked = new Image("TileClicked.png");
		Image information = new Image("information.png");
		
		/*------------------------------------------------------------*/

		// preparing background
		ImageView background = new ImageView();
		background.setImage(image);
		
		/*------------------------------------------------------------*/
		
		//preparing information bar
		Label info = new Label();
		info.setGraphic(new ImageView(information));
		info.setTranslateY(-358);
		Text target = new Text(10, 50, "Target Number: ");
		target.setFont(new Font(20));
		target.setFill(Color.DIMGREY);
		target.setTranslateX(-600);
		target.setTranslateY(-358);
		
		Text targetNum = new Text(10, 50, getTargetNum());
		targetNum.setFont(new Font(25));
		targetNum.setFill(Color.DARKSLATEGREY);
		targetNum.setTranslateX(-475);
		targetNum.setTranslateY(-358);
		
		//timer stuff
		Text timertext = new Text(10, 50, "Time Remaing: ");
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
		
		//Operands
		ArrayList<Button> buttons = new ArrayList<Button>();
		ArrayList<Text> values = new ArrayList<Text>();
		createTiles(buttons, values, getTileAmount(), tile, tileClicked, 300);	
		
		/*------------------------------------------------------------*/
		
		//Operators
		ArrayList<Button> operatorTiles = new ArrayList<Button>();
		ArrayList<Text> operator = new ArrayList<Text>();
		createTiles(operatorTiles, operator, getTileAmount() - 1, tile, tileClicked, 150);
		 
		/*------------------------------------------------------------*/
		
		root = new StackPane();
		//root.getChildren().add(background);	
		root.setStyle("-fx-background-color: lightsteelblue");
		root.getChildren().add(info);
		root.getChildren().add(timerLabel);
		root.getChildren().add(seconds);
		root.getChildren().add(timertext);
		root.getChildren().add(target);
		root.getChildren().add(targetNum);
		for (int i = 0; i < getTileAmount(); i++)
		{
			root.getChildren().add(buttons.get(i));
			root.getChildren().add(values.get(i));
		}
		
		for (int i = 0; i < getTileAmount()-1; i++)
		{
			root.getChildren().add(operatorTiles.get(i));
			root.getChildren().add(operator.get(i));
		}
	}
}

/************
   Note:
   		When clicked, move the tile to the open space.
   		If the tile at the open space is clicked again, then move it back to where it used to be.
   		
   		Consult Joseph about data you want in the game.
   		Try to figure out how you're going to update the scene when consulting him.
   		
   		Talk to Rory about the listeners and what he wanted to set up.
   		Consult Rory about the actions you are expecting.
*************/
