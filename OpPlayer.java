/*
This is the Player class for the LearnJava: Operations Game.

This version includes a player's total game points accumulated per round.
This value determines if the player is the overall winner of the game.
This version also includes the method "addGamePoint()" to increment 
    the variable gamePoints by 1 if the player wins the round.
*/

import java.util.ArrayList;

public class OpPlayer
{
    private ArrayList<OperatorTile> operatorsHand;   // Player hand of operator tiles
    private ArrayList<OperandTile> operandsHand;     // Player hand of operand tiles
    private double goalCompare;
    private int gamePoints;
    
    // Default constructor; new player starts with empty hand
    public OpPlayer()
    {
        operatorsHand = new ArrayList<>();
        operandsHand = new ArrayList<>();
        goalCompare = 0;
        gamePoints = 0;
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
    
    ////////////// COMPARE SOLUTION TO GOAL /////////////
    public void compareToGoal(double solutionValue, double goalValue)
    {
        goalCompare = Math.abs(goalValue - solutionValue);
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
    
    ///////////// GET HAND SIZE /////////////
    public int getOperatorsHandSize()
    {
        return operatorsHand.size();
    }
    
    public int getOperandsHandSize()
    {
        return operandsHand.size();
    }
    
    public int getHandSize()
    {
        return operatorsHand.size() + operandsHand.size();
    }
    
    ///////////// GET/SET GAME POINTS /////////////
    public int getGamePoints() 
    {
        return gamePoints;
    }
    
    public void setGamePoints(int newGamePoints)
    {
        gamePoints = newGamePoints;
    }
    
    public void addGamePoint()
    {
        gamePoints += 1;
    }
    
    ///////////// RESET HAND AS UNPLAYED /////////////
    public void resetHand()
    {
        for (int i = 0; i < operatorsHand.size(); i++)
            operatorsHand.get(i).resetTile();
        for (int i = 0; i < operandsHand.size(); i++)
            operandsHand.get(i).resetTile();
    }
}
