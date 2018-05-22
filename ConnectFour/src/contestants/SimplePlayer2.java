package contestants;

import connectFour.Grid;
import connectFour.Player;

public class SimplePlayer2 implements Player
{

	@Override
	public int getMoveColumn(Grid g)
	{
		// TODO Auto-generated method stub
		int colnumb = 0;
		while (g.isColumnFull(colnumb) && colnumb <7)
		{
			colnumb++;
		}
		return colnumb;
	}

	@Override
	public String getPlayerName()
	{
		// TODO Auto-generated method stub
		return "SimplePlayer2";
	}

}
