package contestants;

import connectFour.Grid;
import connectFour.Player;
public class JTourn implements Player 
{

	@Override
	public int getMoveColumn(Grid g) 
	{
		return (int) (Math.random()*7);
	}

	@Override
	public String getPlayerName() 
	{
		return "JTourn";
	}

}


