/*
This is the Logic class for the LearnJava: Operations Game.

Error checking of inputs for console version available.
Actual error checking done on View (GUI) component of game.

- this version includes the int/double flag version of the game.
- added int solutionValueInt to calculate player solution of int type
- added boolean intFlag to determine if game played with doubles or ints
    -- if true, game plays with ints; otherwise, play with doubles
- added calculateSolutionInt to calculate solution of int type
- determineRoundWinner now adds a gamePoint to the round-winning player via
    the OpPlayer method "addGamePoint()"
- added determineOverallWinner to determine overall winner of all 5 rounds

*/

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class OperationsGame 
{
    private double goal;                    // goal number for each round
    private ArrayList<String> solution;     // ArrayList of player solution
    private double solutionValue;           // solution value, double version
    private int solutionValueInt;           // solution value, int version
    private boolean intFlag;                // flag to determine if game plays doubles or ints
    private int round;                      // round count
    private int numPlayers;                 // number of players
    private OpPlayer[] players;             // array of players
    private int playerTurn;                 // int stating whose turn it is
    private int winnerIndex;                // index of player winner for round
    
    // Game constructor
    public OperationsGame(int numPlayersIn)
    {
        // Set up game logic variables
        goal = 0;
        solution = new ArrayList<>();
        solutionValue = 0;
        round = 0;
        
        // Set up player-specific variables
        numPlayers = numPlayersIn;
        players = new OpPlayer[numPlayers];
        for (int i = 0; i < numPlayersIn; i++)
            players[i] = new OpPlayer();
        playerTurn = 0;
    }
    
    ////////////////////////////////////////////////////////////////////////
    // Get/Set Methods
    ////////////////////////////////////////////////////////////////////////
    
    // Get Goal
    public double getGoal()
    {
        return goal;
    }
    
    // Set Goal
    public void setGoal(double newGoal)
    {
        goal = newGoal;
    }
    
    // Get ArrayList of solution
    public ArrayList<String> getSolutionAL()
    {
        return solution;
    }
    
    // Set ArrayList of solution
    public void setSolutionAL(ArrayList<String> newSolution)
    {
        solution.clear();
        solution = newSolution;
    }
    
    // Get solution value
    public double getSolutionValue()
    {
        return solutionValue;
    }
    
    // Set solution value
    public void setSolutionValue(double newSolutionValue)
    {
        solutionValue = newSolutionValue;
    }
    
    // Get array of players
    public OpPlayer[] getPlayers()
    {
        return players;
    }
    
    // Set array of players
    public void setPlayers(OpPlayer[] newPlayers)
    {
        players = newPlayers;
    }
    
    // Get number of players
    public int getNumPlayers()
    {
        return numPlayers;
    }
    
    // Set number of players
    public void setNumPlayers(int newNumPlayers)
    {
        numPlayers = newNumPlayers;
    }
    
    // Get round count
    public int getRound()
    {
        return round;
    }
    
    // Set round count
    public void setRound(int newRound)
    {
        round = newRound;
    }
    
    // Get player turn
    public int getPlayerTurn()
    {
        return playerTurn;
    }
    
    // Set player turn
    public void setPlayerTurn(int newPlayerTurn)
    {
        playerTurn = newPlayerTurn;
    }
    
    ////////////////////////////////////////////////////////////////////////
    // Game Logic
    ////////////////////////////////////////////////////////////////////////
    
    // Generates random goal number between -100 and 100
    // Then sets this as the new goal
    public void createAndSetRandomGoal()
    {
        // Generate random double between 0 and 100
        Random r = new Random();
        double randomGoal = (double) Math.round(r.nextDouble()*200.0 - 100.0);
        //(double) Math.round(10.0 * r.nextDouble() * 100.0) / 100.0;
        setGoal(randomGoal);
    }
    
    // Generates a random operand tile
    // Then adds this tile to specified player's hand
    public void drawOperand(OpPlayer player, int numTiles)
    {
        for (int i = 0; i < numTiles; i++)
        {
            // Generate random double between 0 and 10 (up to 2 decimal places)
            Random r = new Random();
            double randomOperand = (double) Math.round(10.0 * r.nextDouble() * 100.0) / 100.0;
            
            // Determine if operand is positive or negative randomly
            double signRand = (double) Math.round(r.nextDouble()*200.0 - 100.0);
            if (signRand <= 0.0)
                randomOperand *= -1;
            
            // Create tile with randomly generated operand value
            OperandTile newTile = new OperandTile(randomOperand);
            
            // Add new operand tile to player hand
            player.addToHandOperand(newTile);
        }
    }
    
    // Generates a random operator tile
    // Then adds this tile to player hand
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
            
            // Create operator tile with randomly generated operator
            OperatorTile newTile = new OperatorTile(newOperator);
            
            // Add new operator tile to player hand
            player.addToHandOperator(newTile);
        }
    }
    
    // Show the specified player's hand to console
    public void displayHand(OpPlayer player)
    {
        // Display operands
        for (int i = 0; i < player.getOperandsHandSize(); i++)
            System.out.print("| " + player.checkOperandTile(i).getValueStr() + " | ");

        System.out.println();

        // Display operators
        for (int i = 0; i < player.getOperatorsHandSize(); i++)
            System.out.print("| " + player.checkOperatorTile(i).getValueStr() + " | ");
    }
    
    // Calculates the value of the current ArrayList of solution
    // This method assumes that the entered ArrayList is in the correct format
    // Right now method calculates left to right. Does not follow PEMDAS
    public void calculateSolution()
    {
        solutionValue = 0;
        
        for (int i = 0; i < solution.size(); i++)
        {
            if (i == 0)
            {
                double temp = Double.parseDouble(solution.get(i));
                if (solution.get(i).charAt(0) == '-')
                    temp = temp * -1;
                solutionValue += Double.parseDouble(solution.get(i));
            }
            else if (solution.get(i).equals("+")
                    || solution.get(i).equals("-")
                    || solution.get(i).equals("*")
                    || solution.get(i).equals("/")
                    || solution.get(i).equals("%"))
            {
                // intentionally left blank
                // currently looking at operator, so no work done
            }
            else
            {
                double temp = Double.parseDouble(solution.get(i));
                
                // Check what operation preceded tile
                if (solution.get(i-1).equals("+"))
                {
                    solutionValue = solutionValue + temp;
                }
                else if (solution.get(i-1).equals("-"))
                {
                    solutionValue = solutionValue - temp;
                }
                else if (solution.get(i-1).equals("*"))
                {
                    solutionValue = solutionValue * temp;
                }
                else if (solution.get(i-1).equals("/"))
                {
                    solutionValue = solutionValue / temp;
                }
                else
                {
                    solutionValue = solutionValue % temp;
                }
            }
        }
    }
    
    // Calculates the value of the current ArrayList of solution
    // This method assumes that the entered ArrayList is in the correct format
    // Right now method calculates left to right. Does not follow PEMDAS
    
    // This method runs if the flag for int values is on in the game
    public void calculateSolutionInt()
    {
        solutionValue = 0;
        
        for (int i = 0; i < solution.size(); i++)
        {
            if (i == 0)
            {
                int temp = Integer.parseInt(solution.get(i));
                if (solution.get(i).charAt(0) == '-')
                    temp = temp * -1;
                solutionValue += Integer.parseInt(solution.get(i));
            }
            else if (solution.get(i).equals("+")
                    || solution.get(i).equals("-")
                    || solution.get(i).equals("*")
                    || solution.get(i).equals("/")
                    || solution.get(i).equals("%"))
            {
                // intentionally left blank
                // currently looking at operator, so no work done
            }
            else
            {
                int temp = Integer.parseInt(solution.get(i));
                
                // Check what operation preceded tile
                if (solution.get(i-1).equals("+"))
                {
                    solutionValue = solutionValue + temp;
                }
                else if (solution.get(i-1).equals("-"))
                {
                    solutionValue = solutionValue - temp;
                }
                else if (solution.get(i-1).equals("*"))
                {
                    solutionValue = solutionValue * temp;
                }
                else if (solution.get(i-1).equals("/"))
                {
                    solutionValue = solutionValue / temp;
                }
                else
                {
                    solutionValue = solutionValue % temp;
                }
            }
        }
    }
    
    // Return percent error difference between goal and solution
    public double compareToGoal()
    {
        return (Math.abs(goal - solutionValue));
    }
    
    // Determine winner of round by checking goalCompare scores of each player
    public void determineRoundWinner()
    {
        for (int i = 0; i < numPlayers; i++)
        {
            // First player checked
            if (i == 0)
                winnerIndex = 0;
            // Subsequent players compared to previous winner index
            else
            {
                // Player wins if goalCompare is smallest of all players
                if (players[i].getGoalCompare() < players[winnerIndex].getGoalCompare())
                    winnerIndex = i;
            }
        }
        
        // Add point to winning player's game points for winning round
        players[winnerIndex].addGamePoint();
        
        System.out.println("The winner for round " + (round+1) + " is Player " + (winnerIndex+1) + "!");
    }
    
    // Determine winner of game by checking gamePoints scores of each player
    public void determineOverallWinner()
    {
        for (int i = 0; i < numPlayers; i++)
        {
            // First player checked
            if (i == 0)
                winnerIndex = 0;
            // Subsequent players compared to previous winner index
            else
            {
                // Player wins if goalCompare is smallest of all players
                if (players[i].getGamePoints() < players[winnerIndex].getGamePoints())
                    winnerIndex = i;
            }
        }
        
        System.out.println("The overall winner is Player " + (winnerIndex+1) + "!\nCongratulations!");
    }
    
    // Console version of game
    public void playGame()
    {
        for (round = 0; round < 5; round++)
        {
            Scanner key = new Scanner(System.in);
            System.out.println("\n----------------------------------------------------------------------");
            System.out.println("------------------------------Round " + (round+1) + "---------------------------------");
            System.out.println("----------------------------------------------------------------------");
            
            // Generate round goal
            createAndSetRandomGoal();
            System.out.println("This round's goal: " + getGoal() + "\n");
            
            for (playerTurn = 0; playerTurn < numPlayers; playerTurn++)
            {
                System.out.println("Player " + (playerTurn+1) + "'s turn!");
                
                // First round: everyone starts with 3 operands and 2 operators
                if (round == 0)
                {
                    drawOperand(players[playerTurn], 3);
                    drawOperator(players[playerTurn], 2);
                }
                // Following rounds, each player adds 1 new operand and 1 new operator to hand
                else
                {
                    drawOperand(players[playerTurn], 1);
                    drawOperator(players[playerTurn], 1);
                }
                
                // Display player's hand
                System.out.println("Player " + (playerTurn+1) + "'s hand:");
                displayHand(players[playerTurn]);
                
                System.out.println("\n\nThis round's goal: " + getGoal());
                
                // Start collecting player solution
                ArrayList<String> playerSolution = new ArrayList<String>(players[playerTurn].getHandSize());
                for (int tilesPlayed = 0; tilesPlayed < players[playerTurn].getHandSize(); tilesPlayed++)
                {
                    int tilePicked;
                    boolean continueToNextTile = false;
                    
                    // Operand tile to be played
                    if ((tilesPlayed % 2) == 0)
                    {
                        do{
                            System.out.print("Please enter the index of the operand to play: ");
                            tilePicked = key.nextInt();
                            
                            // Tile out of index
                            if (tilePicked < 0 || tilePicked >= players[playerTurn].getOperandsHandSize())
                            {
                                System.out.println("Index out of bounds. Please choose another tile.");
                            }
                            // Selected tile has already been played
                            else if (players[playerTurn].checkOperandTile(tilePicked).isPlayed())
                            {
                                System.out.println("Tile has already been played. Please choose another tile.");
                            }
                            // Selected tile has not been played yet; valid play
                            else
                            {
                                // Set tile as played
                                players[playerTurn].checkOperandTile(tilePicked).setTileAsPlayed();
                                // Get String value of tile
                                String tileToSolution = players[playerTurn].checkOperandTile(tilePicked).getValueStr();
                                // Add to player solution
                                playerSolution.add(tileToSolution);
                                // Added a tile to solution; Continue iteration.
                                continueToNextTile = true;
                            }
                        // Continue until player selects a tile that has not been played
                        } while (!continueToNextTile); 
                    }
                    
                    // Operator tile to be played
                    else
                    {
                        do{
                            System.out.print("Please enter the index of the operator to play: ");
                            tilePicked = key.nextInt();
                            
                            // Tile out of index
                            if (tilePicked < 0 || tilePicked >= players[playerTurn].getOperatorsHandSize())
                            {
                                System.out.println("Index out of bounds. Please choose another tile.");
                            }
                            // Selected tile has already been played
                            else if (players[playerTurn].checkOperatorTile(tilePicked).isPlayed())
                            {
                                System.out.println("Tile has already been played. Please choose another tile.");
                            }
                            // Selected tile has not been played yet; valid play
                            else
                            {
                                // Set tile as played
                                players[playerTurn].checkOperatorTile(tilePicked).setTileAsPlayed();
                                // Get String value of tile
                                String tileToSolution = players[playerTurn].checkOperatorTile(tilePicked).getValueStr();
                                // Add to player solution
                                playerSolution.add(tileToSolution);
                                // Added a tile to solution; Continue iteration.
                                continueToNextTile = true;
                            }
                        // Continue until player selects a tile that has not been played
                        } while (!continueToNextTile); 
                    }
                }
                
                // Calculate player solution
                setSolutionAL(playerSolution);
                if (intFlag){
                    calculateSolutionInt();
                    System.out.println("Player " + (playerTurn+1) + "'s solution: " + solutionValueInt);
                }
                else {
                    calculateSolution();
                    System.out.println("Player " + (playerTurn+1) + "'s solution: " + solutionValue);
                }
                
                
                // Calculate player score compared to goal
                players[playerTurn].compareToGoal(solutionValue, goal);
                System.out.println("Player " + (playerTurn+1) + "'s score: " + players[playerTurn].getGoalCompare());
                
                System.out.println("\n----------------------------------------------------------------------\n");
            }
            
            // Display winner
            determineRoundWinner();
            
            // Reset all tiles in hand as unplayed
            for (playerTurn = 0; playerTurn < numPlayers; playerTurn++)
                players[playerTurn].resetHand();
        }
        
        determineOverallWinner();
        
        System.out.println("Thanks for playing! Hope you had fun!");
    }
}
