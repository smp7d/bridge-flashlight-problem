package org.sample;

/**
 * A game where the objective is to exhaust all moves and keep track of the
 * total "duration". The board will represent the current state of the game.
 * 
 */
public interface ExhaustingMovesGameBoard {

	/**
	 * Determine of there are possible moves left (used to end game)
	 * 
	 * @return true if no more moves
	 */
	boolean hasPossibleMoves();

	/**
	 * Get the duration in units of what it took to get to this state of the
	 * game.
	 * 
	 * @return the duration
	 */
	long getDuration();

	/**
	 * Provide the move strategy context through whatever means.
	 * 
	 * @return the context
	 */
	GameBoardMoveContext<? extends ExhaustingMovesGameBoard> getMoveContext();

}
