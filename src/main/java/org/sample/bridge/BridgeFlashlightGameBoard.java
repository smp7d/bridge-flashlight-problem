package org.sample.bridge;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.sample.ExhaustingMovesGameBoard;
import org.sample.GameBoardMoveContext;

/**
 * A representation of a bridge-flashlight game and its "crossers" at a
 * particular point in time.
 * 
 */
/**
 * @author Steve
 *
 */
/**
 * @author Steve
 * 
 */
public class BridgeFlashlightGameBoard implements ExhaustingMovesGameBoard {
	/**
	 * The group of crossers on the destination side of the bridge.
	 */
	private final Set<Crosser> destinationSidePeople;

	/**
	 * The group of crossers on the original side of the bridge.
	 */
	private final Set<Crosser> originalSidePeople;

	private boolean flashlightOnOriginalSide;

	/**
	 * The time (in units) that it has taken for the bridge to make it to this
	 * state.
	 */
	private long duration;

	/**
	 * Internally this uses separate strategies based on state of bridge.
	 */
	private final GameBoardMoveContext<BridgeFlashlightGameBoard> crossContext = new GameBoardMoveContext<BridgeFlashlightGameBoard>(
			new AggressiveCrossStrategy());

	private final GameBoardMoveContext<BridgeFlashlightGameBoard> backtrackContext = new GameBoardMoveContext<BridgeFlashlightGameBoard>(
			new SimpleBackTrackStrategy());

	public BridgeFlashlightGameBoard(final Set<Crosser> originalSidePeople,
			final Set<Crosser> destinationSidePeople) {
		this(originalSidePeople, destinationSidePeople, 0, true);
	}

	public BridgeFlashlightGameBoard(final Set<Crosser> originalSidePeople,
			final Set<Crosser> destinationSidePeople, long duration,
			boolean flashlightOnOriginalSide) {
		this.destinationSidePeople = destinationSidePeople;
		this.originalSidePeople = originalSidePeople;
		this.duration = duration;
		this.flashlightOnOriginalSide = flashlightOnOriginalSide;
	}

	@Override
	public GameBoardMoveContext<BridgeFlashlightGameBoard> getMoveContext() {
		if (flashlightOnOriginalSide) {
			return crossContext;
		} else {
			return backtrackContext;
		}
	}

	/*
	 * (non-Javadoc) for the purposes of this game, there will be no possible
	 * moves when all have crossed
	 * 
	 * @see org.sample.ExhaustingMovesGameBoard#hasPossibleMoves()
	 */
	@Override
	public boolean hasPossibleMoves() {
		return originalSidePeople.size() > 0;
	}

	@Override
	public long getDuration() {
		return duration;
	}

	@Override
	public boolean equals(final Object other) {
		if (other == null || other.getClass() != this.getClass()) {
			return false;
		}
		final BridgeFlashlightGameBoard board = (BridgeFlashlightGameBoard) other;
		return board.isFlashlightOnOriginalSide() == this.flashlightOnOriginalSide
				&& this.destinationSidePeople.equals(board
						.getDestinationSidePeople())
				&& this.originalSidePeople
						.equals(board.getOriginalSidePeople());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(19, 39).append(originalSidePeople).append(
				destinationSidePeople).append(flashlightOnOriginalSide)
				.toHashCode();
	}

	public Set<Crosser> getDestinationSidePeople() {
		return destinationSidePeople;
	}

	public Set<Crosser> getOriginalSidePeople() {
		return originalSidePeople;
	}

	public boolean isFlashlightOnOriginalSide() {
		return flashlightOnOriginalSide;
	}

	/**
	 * Convenience builder for initial state of game.
	 * 
	 */
	public static class InitialBuilder {
		private final Set<Crosser> people;

		public InitialBuilder(final Crosser... people) {
			this.people = new HashSet<Crosser>(people.length);
			Collections.addAll(this.people, people);
		}

		public BridgeFlashlightGameBoard build() {
			return new BridgeFlashlightGameBoard(this.people,
					new HashSet<Crosser>(people.size()));
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (Crosser originalSideCrosser : originalSidePeople) {
			builder.append(originalSideCrosser.getName());
			builder.append("|");
		}
		if (!flashlightOnOriginalSide) {
			builder.append("no ");
		}
		builder.append("flashlight]");
		builder.append("---BRIDGE TO DESTINATION--->");
		builder.append("[");
		for (Crosser destinationSideCrosser : destinationSidePeople) {
			builder.append(destinationSideCrosser.getName());
			builder.append("|");
		}
		if (flashlightOnOriginalSide) {
			builder.append("no ");
		}
		builder.append("flashlight] ");
		builder.append("duration: ");
		builder.append(duration);
		return builder.toString();
	}
}
