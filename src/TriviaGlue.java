import javafxdemo.TriviaGameGUI;

public class TriviaGlue {

	public void startGame()
	{
		//Create and initialize model.
		TriviaGame theModel = new TriviaGame();
		theModel.initializeGame();
		
		//Create View
		TriviaGameGUI theView = new TriviaGameGUI();
		
		
		//Create Controller and start gameplay.
		TriviaController theController = new TriviaController(theModel,theView);
		theController.gamePlayLoop();
	}
	
	
}
