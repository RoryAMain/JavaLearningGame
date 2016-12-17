import java.util.ArrayList;

public class TriviaGlue {
	static TriviaGameGUI theView;
	static TriviaGame theModel;
	
	public static void initializeGame()
	{
		theModel = new TriviaGame();
		theModel.initializeGame();
	}
	
	public static void initializeTopics(){
		TriviaGame.initializeTopics();
	}
	
	public static ArrayList<String> getAllTopicNames(){
		return SQLManager.getAllTopicNames();
	}
	public static String getPlayerTurn(){
		return theModel.getCurrentPlayer();
	}
	
	public static String getPlayerScore(){
		return theModel.getCurrentPlayerScore();
	}
	
	public static String getQuestion(){
		int currentButton = theView.currentButtonId;
		int currentX = currentButton/3;
		int currentY = currentButton%3;
		theModel.questionChosen(currentX,currentY);
		return theModel.getCurrentQuestion();
	}
	
	public static String answerChecking(){
		if(theModel.answerGiven(theView.getAnswer())==true)
			return "You are correct!";
		else{
			String answerMessage;
			answerMessage = "You are not correct! The answer is " + theModel.getCurrentAnswer();
			return answerMessage;
		}	
	}
	
	public static void challenged(){
		theModel.answerGiven("wrong");
		theModel.answerGiven(theModel.getCurrentAnswer());
	}
	
	public static Boolean gameOver(){
		return theModel.getGameOver();
	}
	
	public static String getWinner(){
		return theModel.whoIsWinning();
	}
	
	public static String getWinnerScore(){
		return theModel.winningScore();
	}
	
	public static ArrayList<Integer> getAllScores(){
		return theModel.getAllScores();
	}
	
	public static int getNumberOfTopics(){
		return TriviaGameGUI.getNumberOfTopics();
	}
	
	public static ArrayList<String> getCategoryNames(){
		return TriviaGameGUI.getCategoryName();
	}
	
	public static int getPlayerCount(){
		return TriviaGameGUI.getPlayerCount();
	}
}