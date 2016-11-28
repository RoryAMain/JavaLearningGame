public class OperandTile extends Tile
{
    private boolean tilePlayed;
    private final double value;
    private final String valueStr;
    
    // Blank tiles are defaulted as zeros
    public OperandTile()
    {
        tilePlayed = false;
        value = 0;
        valueStr = "0";
    }
    
    public OperandTile(double newValueNum)
    {
        tilePlayed = false;
        value = newValueNum;
        valueStr = Double.toString(newValueNum);
    }
    
    public double getValue()
    {
        return value;
    }
    
    public String getValueStr()
    {
        return valueStr;
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
