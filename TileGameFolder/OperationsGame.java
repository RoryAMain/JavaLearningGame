/*
PLAYER PROCESS:
Player starts turn
Player given 3 operands and 2 operators
Player given goal number
Player give solution in proper format (and-tor-and-tor-and)
Player hit submit
Game calculates player solution
*/

import java.util.ArrayList;
import java.util.Random;

public class OperationsGame 
{
    public double goal;
    private ArrayList<Tile> solution;
    private double solutionValue;
    
    public OperationsGame()
    {
        goal = 0;
        solution = new ArrayList<>();
    }
    
    // Generates a random operand tile and adds this tile to player hand
    public void drawOperand(OpPlayer player, int numTiles)
    {
        for (int i = 0; i < numTiles; i++)
        {
            // Generate random double between 0 and 10 (up to 2 decimal places)
            Random r = new Random();
            double randomOperand = (double) Math.round(10.0 * r.nextDouble() * 100.0) / 100.0;
            OperandTile newTile = new OperandTile(randomOperand);
            player.addToHandOperand(newTile);
        }
    }
    
    // Generates a random operator tile and add this tile to player hand
    public void drawOperator(OpPlayer player, int numTiles)
    {
        for (int i = 0; i < numTiles; i++)
        {
            char newOperator;

            // Generate random int from 0 to 9 to determine type of tile
            Random r = new Random();
            int random = (int) r.nextInt(10);
            
            // 0 or 1 generates addition tile
            if (random == 0 || random == 1)
                newOperator = '+';
            // 2 or 3 generates subtraction tile
            else if (random == 2 || random == 3)
                newOperator = '-';
            // 4 or 5 generates multiplication tile
            else if (random == 4 || random == 5)
                newOperator = '*';
            // 6 or 7 generates division tile
            else if (random == 6 || random == 7)
                newOperator = '/';
            // 8 or 9 generates modulo tile
            else
                newOperator = '%';
            
            OperatorTile newTile = new OperatorTile(newOperator);
            player.addToHandOperator(newTile);
        }
    }
    
    // Generates random goal number between -100 and 100
    // and sets this as the new goal
    public void createAndSetRandomGoal()
    {
        // Generate random double between 0 and 100
        Random r = new Random();
        double randomGoal = (double) Math.round(r.nextDouble()*200.0 - 100.0);
        //(double) Math.round(10.0 * r.nextDouble() * 100.0) / 100.0;
        goal = randomGoal;
        System.out.println("The goal is: " + goal);
    }
    
    public void addToSolution(Tile selectedTile)
    {
        // Cannot add operator tile if solution is empty
        if (solution.isEmpty())
        {
            if (selectedTile instanceof OperatorTile)
                System.out.println("Cannot start with operator in solution.");
            else
                solution.add(selectedTile);
        }
        else
        {   
            // Cannot play consecutive operator tiles
            if (solution.get(solution.size() - 1) instanceof OperatorTile
                    && selectedTile instanceof OperatorTile) 
            {
                System.out.println("Cannot play an operator tile when the last tile played was an operator.");
            } 
            // Cannot play consecutive operand tiles
            else if (solution.get(solution.size() - 1) instanceof OperandTile
                    && selectedTile instanceof OperandTile) 
            {
                System.out.println("Cannot play an operand tile when the last tile played was an operand.");
            } 
            // Valid play
            else 
            {
                solution.add(selectedTile);
            }
        }
    }
    
    public void calculateSolution()
    {
        solutionValue = 0;
        for (int i = 0; i < solution.size(); i++)
        {
            if (i == 0)
            {
                solutionValue = solutionValue + solution.get(i).getValue();
            }
            else if (solution.get(i) instanceof OperandTile)
            {
                if (solution.get(i-1).getValueStr().equals("+"))
                    solutionValue = solutionValue + solution.get(i).getValue();
                else if (solution.get(i-1).getValueStr().equals("-"))
                    solutionValue = solutionValue - solution.get(i).getValue();
                else if (solution.get(i-1).getValueStr().equals("*"))
                    solutionValue = solutionValue * solution.get(i).getValue();
                else if (solution.get(i-1).getValueStr().equals("/"))
                    solutionValue = solutionValue / solution.get(i).getValue();
                else
                    solutionValue = solutionValue % solution.get(i).getValue();
            }
        }
        System.out.println("Your solution outputs: " + solutionValue);
    }
    
    // SAMPLE MAIN METHOD TO TEST VALUES
    // I know. These should be in the test cases, but WUTEVS.
    public static void main(String[] args)
    {
        OperationsGame game = new OperationsGame();
        OpPlayer player1 = new OpPlayer();
        
        // Generate goal for player
        game.createAndSetRandomGoal();
        
        // Draw 3 operands and 2 operators
        game.drawOperand(player1, 3);
        game.drawOperator(player1, 2);
        
        // Display hand
        System.out.print("Your operands: \n| ");
        for (int i = 0; i < player1.operandsHand.size(); i++)
        {
            System.out.print(player1.checkOperandTile(i).getValueStr() + " | ");
        }
        
        System.out.print("\nYour operators: \n| ");
        for (int i = 0; i < player1.operatorsHand.size(); i++)
        {
            System.out.print(player1.checkOperatorTile(i).getValueStr() + " | ");
        }
        
        System.out.println();
        
        int choice = 0;
        game.addToSolution(player1.playOperandTile(choice));
        game.addToSolution(player1.playOperatorTile(choice));
        choice = 1;
        game.addToSolution(player1.playOperandTile(choice));
        game.addToSolution(player1.playOperatorTile(choice));
        choice = 2;
        game.addToSolution(player1.playOperandTile(choice));

        System.out.print("Solution: \n| ");
        for (int i = 0; i < game.solution.size(); i++)
        {
            System.out.print(game.solution.get(i).getValueStr() + " | ");
        }

        System.out.println();
        
        game.calculateSolution();
        player1.compareToGoal(game.solutionValue, game.goal);
        System.out.println("Solution comparison to goal: " + player1.getGoalCompare());
    }
}
