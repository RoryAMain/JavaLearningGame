/*
This is the main method class for the LearnJava: Operations Game.
While waiting on GUI stuff, the game can be played through the console.
*/

import java.util.ArrayList;
import java.util.Scanner;

public class OperationsMain {
    public static void main(String[] args)
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Welcome to LearnJava: Operations Game!\n"
                + "Please enter the number of players (1-4):");
        int numPlayers = keyboard.nextInt();
        
        OperationsGame game = new OperationsGame(numPlayers);
        game.playGame();
    }
}