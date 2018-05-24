package contestants;

import connectFour.Grid;
import connectFour.GridUtilities;

public class JLinMiniMax implements connectFour.Player
{

	// It must have a public constructor that takes no parameters
	public JLinMiniMax()
	{
		
	}
	
	@Override
	public int getMoveColumn(Grid g)
	{
		int colnumb = 3;
		while (g.isColumnFull(colnumb) && colnumb <7)
		{
			colnumb++;
		}
		int[] a = minimaxGetScore(g, 6, g.getNextPlayer());
		
		int min = Integer.MIN_VALUE;
		for (int x = 0; x < a.length; x++)
		{
			if (a[x] > min)
			{
				colnumb = x;
				min = a[x];
			}
		}
		
		return colnumb;
	}

	@Override
	public String getPlayerName()
	{
		// TODO Auto-generated method stub
		return "J";
	}

	
	public int getHeuristicScore(Grid g)
	{
		int score = 1; int min = 0;
		int[] dir = {Grid.RIGHT, -1*Grid.RIGHT, Grid.UP, Grid.UPLEFT, Grid.UPRIGHT};
		int checkifMe = 1; //get number id of player positive if me, negative if opp
		if (g.getNextPlayer() == 2)
		{
			checkifMe*=-1;
		}
		GridUtilities gu = new GridUtilities(g);
		
		for (int x = 0; x < g.getRows(); x++) //for every row, check
		{
			for (int y = 0; y < g.getCols(); y++) //for every col, check
			{
				for (int z = 0; z < dir.length; z++) //each direction
				{
					int[] lenspaces = gu.getLengthAndSpaces(x, y, z); //check left, right, up... etc
						/*  4 elements as follows: [0] = length of chain found [1] = number of spaces surrounding the chain
						 *  (either 0, 1, or 2) ([2], [3]) = the row and column of one of the spaces surrounding the chain. 
						 *  If no spaces surround the chain, these are just (-1, -1)
						 */
					if (lenspaces[2] > -1) //if the chain exists
					{
						if (lenspaces[0] >= 1) //length of chain might be 1,2,3,4
						{
							if (lenspaces[0] >= 3 && lenspaces[1] > 1) //best possible situation:  3 or more empty spaces and some space around the chain
							{
								score*=3000;
							}
							else if (lenspaces[0] < 3 && lenspaces[1] > 1)
							{
								score*=2000;
							}
							else
							{
								score*=1000;
							}
						}
					}
				}	
			}	
		}
		
		return checkifMe*score;
	}

	//Mr George's MiniMax
	private int[] minimaxGetScore(Grid g, int remainingDepth, int myPlayer)
    {
        // Did this move end the game?  If so, score it now based on whether we won.
        if (g.getWinningPlayer() == myPlayer)
        {
            // We won!
            return new int[] { 1000 * (remainingDepth + 1), -1 };
        }
        else if (g.getWinningPlayer() == (3 - myPlayer))
        {
            // They won
            return new int[] { -1000 * (remainingDepth + 1), -1 };
        }
        else if (g.getWinningPlayer() == -1)
        {
            // Game ends in a draw.
            return new int[] { 0, -1 };
        }

        int nextPlayer = g.getNextPlayer();

        // We don't want to go any deeper, so just return the immediate heuristic score
        // for this board
        if (remainingDepth <= 0)
        {
            // TODO: FOR YOU TO DO!  WRITE THIS getHeuristicScore METHOD
            // TO EXAMINE THE GRID AND COME UP WITH A NUMERIC SCORE FOR IT.
            // THE SCORE SHOULD BE FROM THE POINT OF VIEW OF YOUR PLAYER
            // (HIGH VALUES MEANS GOOD FOR YOU, LOW VALUES MEAN BAD FOR YOU).
            // THEN REPLACE '= 1' WITH '= getHeuristicScore(g)'
            int score = getHeuristicScore(g);
            return new int[] { score, -1 };
        }

        // Call self recursively for next player's moves' scores
        minimaxGetScore(g, remainingDepth, myPlayer);
        // Is this nextPlayer trying to minimize or maximize the score?  If it's us,
        // maximize.  If opponent, minimize.
        boolean isMax = (nextPlayer == myPlayer);
        int bestMove = -1;
        int bestScore;
        if (isMax)
        {
            bestScore = Integer.MIN_VALUE;
        }
        else
        {
            bestScore = Integer.MAX_VALUE;
        }        

        for (int nextCol = 0; nextCol < g.getCols(); nextCol++)
        {
            if (!g.isColumnFull(nextCol))
            {
                // Apply the move (temporarily) to the grid...
                g.makeMove(nextCol);
                
                // ... and then call ourselves recursively to move down the decision tree
                // and come up with a score                
                int scoreCur = minimaxGetScore(g, remainingDepth - 1, myPlayer)[0];
                
                // ... and we must remember to UNDO that move now that the call is done.
                g.undo();
                
                // Update bestScore with what the recursive call returned
                if (isMax)
                {
                    if (scoreCur > bestScore)
                    {
                        bestScore = scoreCur;
                        bestMove = nextCol;
                    }
                }
                else
                {
                    // minimizing!
                    if (scoreCur < bestScore)
                    {
                        bestScore = scoreCur;
                        bestMove = nextCol;
                    }
                }
            }
        }

        // Return the best score (and the recommended move)
        return new int[] { bestScore, bestMove };        
    }
	
}
