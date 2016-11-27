
public class TriviaPlayer {

	private int pointTotal = 0;
	
	public int getPointTotal()
	{
		return pointTotal;
	}
	
	public void setPointTotal(int totalIn)
	{
		pointTotal = totalIn;
	}
	
	public void addToTotal(int pointIn)
	{
		pointTotal = pointTotal + pointIn;
	}
}
