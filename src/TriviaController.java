import javafxdemo.TriviaGameGUI;

public class TriviaController {

	TriviaGame theModel;
	TriviaGameGUI theView;
	
	public TriviaController(TriviaGame modelIn,TriviaGameGUI viewIn)
	{
		theModel = modelIn;
		theView = viewIn;
	}
	
	
	public void gamePlayLoop()
	{
		
		theView.getCategoryName(theModel.topicNames);
		
		while(!theModel.getGameOver())
		{
			//Set current player and points.
			theView.setPlayerTurn(theModel.getCurrentPlayer());
			theView.setPlayerScore(theModel.getCurrentPlayerScore());
			
			while(!theView.buttonPushed)
			{
				//Wait until question is chosen.
			}
			
			//Tell the model which question was chosen.
			int currentButton = theView.currentButtonId;
			int currentX = currentButton/3;
			int currentY = currentButton%3;
			theModel.questionChosen(currentX,currentY);
			//Actual order of x and y may change.
			
			//I haven't gotten X and Y to return values yet, I still need to work on that.
			
			
			//Tell the view what the current question and correct answer are.
			theView.getQuestion(theModel.getCurrentQuestion());
			theView.setCorrectAnswer(theModel.getCurrentAnswer());
			
			while(!theView.questionAnswered)
			{
				//Wait for the question to be answered.
			}
			
			//Check the answer given, reward points if correct, and return current player's score.
			String tempPlayerAnswer = theView.getAnswer();
			theModel.answerGiven(tempPlayerAnswer);
			
			theView.setPlayerScore(theModel.getCurrentPlayerScore());
			//
			
			//Set the next turn.
			theModel.nextTurn();
			
		}
	}
	
	
	
}
