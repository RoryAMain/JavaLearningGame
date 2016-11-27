import java.util.*;

public class TriviaBoard {

	private ArrayList<ArrayList<TriviaTile>> outerBoard = new ArrayList<ArrayList<TriviaTile>>();
	
	//////////////////////////////////////////
	//
	//	addColumn
	//
	//	Input: ArrayList containing TriviaTiles
	//	
	//	Function: Creates a column of TriviaTiles.
	//			  This function can be called multiple times
	//			  to generate the board.
	//
	////////////////////////////////////////////
	public void addColumn(ArrayList<TriviaTile> columnIn)
	{
		outerBoard.add(columnIn);
	}
	
	public ArrayList<ArrayList<TriviaTile>> getOuterBoard()
	{
		return outerBoard;
	}
	
	public void displayBoard()
	{
		System.out.println("*****PICK A TILE*****");
		
		for(int x =0; x < outerBoard.size(); x++)
		{
			for(int y = 0; y < outerBoard.get(x).size();y++)
			{
				//System.out.print(outerBoard.get(y).get(x).displayTile() + "   ");
				System.out.print(outerBoard.get(x).get(y).displayTile() + "   ");

			}
			System.out.println();
		}
	}
	
	public void chooseTile(int x, int y)
	{
		if(x > outerBoard.size() || x<0 || y >outerBoard.get(x).size() || y <0)
		{
			System.out.println(x + "," + y + " Is not a valid tile.");
		}
		else{
		outerBoard.get(x).get(y).setTileChosen(true);
		}
	}
	
	public TriviaTile getTile(int x, int y)
	{
		if(x > outerBoard.size() || x<0 || y >outerBoard.get(x).size() || y <0)
		{
			System.out.println(x + "," + y + " Is not a valid tile.");
			TriviaTile invalidTile = new TriviaTile("Invalid","Invalid",0);
			return invalidTile;
		}
		else
		{
			return outerBoard.get(x).get(y);
		}
	}
	
	public boolean allChosen()
	{
		boolean answer = true;
		for(int x =0; x < outerBoard.size(); x++)
		{
			for(int y = 0; y < outerBoard.get(x).size();y++)
			{

				if(!(outerBoard.get(x).get(y).isTileChosen()))
				{
					answer = false;
				}
			}
			
		}
		
		return answer;
	}
	
}
