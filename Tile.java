/*
This is the abstract Tile class for the LearnJava: Operations Game.
*/

public abstract class Tile {
    // Abstract method to check if tile has been played
    abstract boolean isPlayed();
    
    // Abstract method to change tile as played
    abstract void setTileAsPlayed();
    
    // Abstract method to get double value of tile
    abstract double getValue();
    
    // Abstract method to get String value of tile
    abstract String getValueStr();
}
