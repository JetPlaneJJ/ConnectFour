package contestants;

import connectFour.Grid;
import connectFour.Player;

/* 
	----------------------------------------------------------------------------------
	SIMPLE PLAYER

	For step 3 of the ConnectFour doc, you will fill out this class to create
	your first, simple player.  See doc for details.
	----------------------------------------------------------------------------------
*/

public class SimplePlayer2 implements Player 
{

	@Override
	public int getMoveColumn(Grid g) 
	{
		int a = (int) (Math.random()*7);
		while (g.isColumnFull(a) && a <7)
		{
			a = (int) (Math.random()*7);
		}
		return (int) (Math.random()*7);
	}

	@Override
	public String getPlayerName() 
	{
		return "My Simple Player";
	}

}
