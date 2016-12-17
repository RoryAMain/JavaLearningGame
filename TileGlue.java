import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class TileGlue {
	static TileGameGUI theView;
	static OperationsGame theModel;
	private static int numPlayers;
	
	public static void initializeGame()
	{
		numPlayers = TileGameGUI.getPlayerCount();
		theModel = new OperationsGame(numPlayers);
		theModel.startHand();
	}
	
	public static void update(){
		theModel.startHand();
	}
	
	public static double round(double value, int places) {
		//http://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public static int getPlayerCount(){
		return TileGameGUI.getPlayerCount();
	}
	
	public static int getRound(){
		return (theModel.getRound()+1);
	}
	
	public static double getGoal(){
		return theModel.getGoal();
	}
	
	public static String getPlayerTurn(){
		int num = theModel.getPlayerTurn();
		num++;
		return Integer.toString(num);
	}
	
	public static OpPlayer getPlayer(){
		OpPlayer[] temp = theModel.getPlayers();
		return temp[theModel.getPlayerTurn()];
	}
	
	public static ArrayList<String> getOperands(){
		OpPlayer temp = getPlayer();
		ArrayList<String> returnList = new ArrayList<String>();
		ArrayList<OperandTile> operandslist = temp.getOperandsHand();
		if(TileGameGUI.getFlag()==false){
		for(int i = 0; i < operandslist.size(); i++){
			returnList.add(operandslist.get(i).getValueStr());
		}
		return returnList;
		}
		else{
			for(int i = 0; i < operandslist.size(); i++){
				returnList.add(Integer.toString((int)Double.parseDouble(operandslist.get(i).getValueStr())));
			}
			return returnList;
		}
	}
	
	public static ArrayList<String> getOperators(){
		OpPlayer temp = getPlayer();
		ArrayList<String> returnList = new ArrayList<String>();
		ArrayList<OperatorTile> operatorslist = temp.getOperatorsHand();
		if(TileGameGUI.getFlag()==false){
		for(int i = 0; i < operatorslist.size(); i++){
			returnList.add(operatorslist.get(i).getValueStr());
		}
		return returnList;
		}
		else{
			for(int i = 0; i < operatorslist.size(); i++){
				returnList.add(operatorslist.get(i).getValueStr());
			}
			return returnList;
		}
	}
	
	public static ArrayList<String> getSolutionDeck(){
		return TileGameGUI.getSolutionDeck();
	}
	
	public static void calculate(){
		if(TileGameGUI.getFlag() == false)
			theModel.calculation();
		else
			theModel.intCalculation();
	}
	
	public static String getSolutionValue(){
		return Double.toString(round(theModel.getSolutionValue(), 2));
	}
	
	public static String getGoalCompare(){
		return Double.toString(round(getPlayer().getGoalCompare(), 2));
	}
	
	public static void nextPlayer(){
		theModel.nextPlayer();
	}
	
	public static void incrementRound(){
		theModel.nextRound();
	}
	
	public static void resetPlayer(){
		theModel.resetPlayer();
	}
	
	public static int getWinnerIndex(){
		return theModel.getWinnerIndex();
	}
	
	public static void finishRound(){
		theModel.finishRound();
	}
	
	public static void finishGame(){
		theModel.finishGame();
	}
}
