package org.sample;

import java.util.Set;

/**
 * A context for a GameBoardMoveStrategy
 * 
 * @param <E>
 *            the type of board
 */
public class GameBoardMoveContext<E extends ExhaustingMovesGameBoard> {
	private final GameBoardPotentialMovesStrategy<E> strategy;

	public GameBoardMoveContext(final GameBoardPotentialMovesStrategy<E> strategy) {
		this.strategy = strategy;
	}

	public Set<E> execute(final E currentState) {
		return strategy.calculateNextMoves(currentState);
	}
}
