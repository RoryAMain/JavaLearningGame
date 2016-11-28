import java.util.ArrayList;

public class OpPlayer
{
    public ArrayList<OperatorTile> operatorsHand;   // Player hand of operator tiles
    public ArrayList<OperandTile> operandsHand;     // Player hand of operand tiles
    public double goalCompare;
    
    // Default constructor; new player starts with empty hand
    public OpPlayer()
    {
        operatorsHand = new ArrayList<>();
        operandsHand = new ArrayList<>();
        goalCompare = 0;
    }
    
    ///////////// goalComparison get/set /////////////
    
    public double getGoalCompare()
    {
        return goalCompare;
    }
    
    public void setGoalCompare (double newGoalCompare)
    {
        goalCompare = newGoalCompare;
    }
    
    ///////////// ADD TO HAND /////////////
    
    // Add new operator tile to hand
    public void addToHandOperator(OperatorTile newOperator)
    {
        operatorsHand.add(newOperator);
    }
    
    // Add new operand tile to hand
    public void addToHandOperand (OperandTile newOperand)
    {
        operandsHand.add(newOperand);
    }
    
    ///////////// CHECK HAND /////////////
    // Checks specified tile (does not remove from hand)
    
    // Checks specified operator tile
    public OperatorTile checkOperatorTile(int choiceIndex)
    {
        return operatorsHand.get(choiceIndex);
    }
    
    // Checks specified operator tile
    public OperandTile checkOperandTile(int choiceIndex)
    {
        return operandsHand.get(choiceIndex);
    }
    
    ///////////// PLAY FROM HAND /////////////
    // Sets specified tile to "played" and returns same tile to play
    public OperatorTile playOperatorTile(int choiceIndex)
    {
        OperatorTile temp = operatorsHand.get(choiceIndex);
        operatorsHand.get(choiceIndex).setTileAsPlayed();
        return temp;
    }
    
    public OperandTile playOperandTile(int choiceIndex)
    {
        OperandTile temp = operandsHand.get(choiceIndex);
        operandsHand.get(choiceIndex).setTileAsPlayed();
        return temp;
    }
    
    ///////////// COMPARE SOLUTION TO GOAL /////////////
    public void compareToGoal(double solutionValue, double goalValue)
    {
        goalCompare = Math.abs(goalValue - solutionValue);
    }
}
