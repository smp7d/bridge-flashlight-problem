package org.sample;

import java.util.Set;

/**
 * An algorithm to compute the possible states of the board after the next move.
 * 
 */
public interface GameBoardPotentialMovesStrategy<E extends ExhaustingMovesGameBoard> {
	/**
	 * Calculates all next possible moves based on actual game strategy being
	 * modeled.
	 * 
	 * @param currentState
	 *            the current state of the board
	 * @return a collection of game board states representing the possible moves
	 */
	Set<E> calculateNextMoves(E currentState);
}
