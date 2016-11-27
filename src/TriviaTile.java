
public class TriviaTile {
	
	private String question;
	private String answer;
	private int points;
	private boolean tileChosen = false;
	
	TriviaTile(String questionIn, String answerIn, int pointsIn)
	{
		question = questionIn;
		answer = answerIn;
		points = pointsIn;
	}
	
	public String getQuestion()
	{
		return question;
	}
	
	public String getAnswer()
	{
		return answer;
	}
	
	public int getPoints()
	{
		return points;
	}
	
	public boolean isTileChosen()
	{
		return tileChosen;
	}
	
	public void setTileChosen(boolean chosenIn)
	{
		tileChosen  = chosenIn;
	}
	
	public String displayTile()
	{
		if(tileChosen)
		{
			return "XXX";
		}
		else
		{
			return Integer.toString(points);
			//return question;
		}
	}
	
}
