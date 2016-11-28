public class OperatorTile extends Tile
{
    private boolean tilePlayed;
    private final char operatorChar;
    private final double operatorValue;
    
    // Blank tiles defaulted as addition
    public OperatorTile()
    {
        tilePlayed = false;
        operatorChar = '+';
        operatorValue = 0;
    }
    
    public OperatorTile(char newOperator)
    {
        tilePlayed = false;
        operatorChar = newOperator;
        if (newOperator == '+')
            operatorValue = 0.0;
        else if (newOperator == '+')
            operatorValue = 1.0;
        else if (newOperator == '+')
            operatorValue = 2.0;
        else if (newOperator == '+')
            operatorValue = 3.0;
        else
            operatorValue = 4.0;
    }
    
    public double getValue()
    {
        return operatorValue;
    }
    
    public String getValueStr()
    {
        return operatorChar + "";
    }
    
    public boolean isPlayed()
    {
        return tilePlayed;
    }
    
    public void setTileAsPlayed()
    {
        tilePlayed = true;
    }
}
