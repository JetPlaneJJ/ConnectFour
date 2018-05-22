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
		int playernum = g.getNextPlayer();
		int colnumb = 0;
		while (g.isColumnFull(colnumb) && colnumb <7)
		{
			colnumb++;
		}
		int[] a = minimaxGetScore(g, 6, playernum);
		
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
		int result = 0;
		GridUtilities gu = new GridUtilities(g);
		for (int x = 0; x < g.getRows(); x++)
		{
			for (int y = 0; y < g.getCols(); y++)
			{
				if (gu.doesVertical4StartHere(x, y))
				{
					result = x;
				}
				if (gu.doesHorizontal4StartHere(x, y))
				{
					result = x;
				}
				if (gu.doesDiagonalRight4StartHere(x, y))
				{
					result = x;
				}
				if (gu.doesDiagonalLeft4StartHere(x, y))
				{
					result = x;
				}
				
			}
		}
		
		return result;
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
