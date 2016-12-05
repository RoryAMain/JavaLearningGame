import java.util.*;
public class TriviaGame {
	
	private int playerCount = 0;
	private int currentPlayer = 0;
	private TriviaBoard theGameBoard = new TriviaBoard();
	private TriviaTile currentTile;
	private String currentQuestion;
	private String currentAnswer;
	private ArrayList<TriviaPlayer> playerList = new ArrayList<TriviaPlayer>();
	private boolean gameOver = false;
	private static SQLManager app = new SQLManager();
	
	////////////Methods to be called from the GUI//////////////
	
	public String getCurrentQuestion()
	{
		return currentQuestion;
	}
	
	public String getCurrentAnswer()
	{
		return currentAnswer;
	}
	
	public String whoIsWinning()
	{
		int bestScore = 0;
		int bestPlayer = 0;
		for (int y = 0; y<playerCount;y++)
		{
			int tempScore = playerList.get(y).getPointTotal();
			if(tempScore > bestScore)
			{
				bestScore = tempScore;
				bestPlayer = y+1;
			}
		}
		
		return "Player " + bestPlayer;
	}
	
	public String winningScore()
	{
		int bestScore = 0;
		for (int y = 0; y<playerCount;y++)
		{
			int tempScore = playerList.get(y).getPointTotal();
			if(tempScore > bestScore)
			{
				bestScore = tempScore;
			}
		}
		
		return "Score: " + bestScore;
	}
	
	public ArrayList<Integer> getAllScores()
	{
		ArrayList<Integer> allScores = new ArrayList<Integer>();
		
		for (int y = 0; y<playerCount;y++)
		{
			allScores.add(playerList.get(y).getPointTotal());
		}
		return allScores;
	}
	
	//////////////////////////////////////////////////////////
	
	///////Methods to be called from Controller////////////////
	
	public void questionChosen(int x, int y)
	{
		theGameBoard.chooseTile(x,y);
		currentTile = theGameBoard.getTile(x, y);
		currentQuestion = currentTile.getQuestion();
		currentAnswer = "";
	}
	
	public Boolean answerGiven(String playerAnswer)
	{
		currentAnswer = currentTile.getAnswer();
		if(playerAnswer.equals(currentAnswer))
		{
			//System.out.println("\nCorrect Answer!\n");
			givePoints();
			nextTurn();
			return true;
		}
		else{
			nextTurn();
			return false;
		}
	}
	
	public void nextTurn()
	{
		if(currentPlayer == playerCount-1)
		{
			currentPlayer = 0;
		}
		else if(currentPlayer > playerCount-1)
		{
			System.out.println("Error in nextTurn function.Setting current player to 1.");
			currentPlayer =1;
		}
		else
		{
			currentPlayer++;
		}
		if(theGameBoard.allChosen())
		{
			gameOver = true;
		}
	}
	
	public void givePoints()
	{
			playerList.get(currentPlayer).addToTotal(currentTile.getPoints());
	}
	
	public boolean getGameOver()
	{
		return gameOver;
	}
	
	public String getCurrentPlayerScore()
	{
		return(Integer.toString(playerList.get(currentPlayer).getPointTotal()));
	}
	
	public String getCurrentPlayer()
	{
		return Integer.toString(currentPlayer+1);
	}
	
	
	public ArrayList<String> topicNames;
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	
	private void getNumberOfPlayers()
	{
		playerCount = TriviaGlue.getPlayerCount();
	}
	
	public static void initializeTopics(){
		app.initializeAllTopics();
	}
	
	public void initializeGame()
	{
		theGameBoard = new TriviaBoard();
		app.generateBoard(app, theGameBoard);
		getNumberOfPlayers();
		
		playerList = new ArrayList<TriviaPlayer>();
		for(int x = 1; x <playerCount+1;x++)
		{
			TriviaPlayer newPlayer = new TriviaPlayer();
			playerList.add(newPlayer);
		}
		
		topicNames = app.topicNames;
	}
	
	
	public void testGUI()
	{
		System.out.println("Question: " + currentQuestion);
		System.out.println("Answer: " + currentAnswer);
		System.out.println("Current Player: " + (currentPlayer+1));
		for(int x = 0; x < playerCount;x++)
		{
			System.out.println("Player " + (x+1) + ": " + playerList.get(x).getPointTotal());
		}
	}
	
	/*
	public static void main(String[] args)
	{
		
		TriviaGame theGame = new TriviaGame();
		theGame.initializeGame();
		theGame.testGUI();
		Scanner reader2 = new Scanner(System.in);
		
		while(!theGame.gameOver){
		System.out.println("Choose X:");
		int x = reader2.nextInt();
		System.out.println("Choose Y:");
		int y = reader2.nextInt();
		//theGame.questionChosen(y,x);
		theGame.questionChosen(x, y);
		theGame.theGameBoard.displayBoard();
		System.out.println("Current question: \n" + theGame.currentQuestion + "\n");
		String tempAnswer;
		System.out.println("\nInput answer: \n");
		tempAnswer = reader2.next();
		theGame.answerGiven(tempAnswer);
		theGame.nextTurn();
		theGame.testGUI();
		}
		
		System.out.println(theGame.whoIsWinning() + " wins!");
	}*/
	
}
