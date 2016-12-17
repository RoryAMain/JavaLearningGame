/*
This is the main method class for the LearnJava: Operations Game.
While waiting on GUI stuff, the game can be played through the console.
*/

import java.util.Scanner;

public class OperationsMain {
    public static void main(String[] args)
    {
        //Scanner keyboard = new Scanner(System.in);
        //System.out.println("Welcome to LearnJava: Operations Game!");
        
        int numPlayers;
        numPlayers = TileGlue.getPlayerCount();
        /*do{
            System.out.print("Please enter the number of players (1-4):");
            numPlayers = keyboard.nextInt();
            if (numPlayers < 1 || numPlayers > 4)
            {
                System.out.println("Invalid entry. Please enter a valid number (1, 2, 3, or 4).\n");
            }
        } while (numPlayers < 1 || numPlayers > 4);*/
        
        //OperationsGame game = new OperationsGame(numPlayers);
        //game.playGame();
    }
}