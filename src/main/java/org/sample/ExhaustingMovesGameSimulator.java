package org.sample;

import java.util.ArrayDeque;
import java.util.Deque;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A simulator to attempt to determine the fastest way to exhaust all moves in
 * an exhausting moves game.
 * 
 */
public final class ExhaustingMovesGameSimulator {
	private static final Log logger = LogFactory
			.getLog(ExhaustingMovesGameSimulator.class);

	private final ExhaustingMovesGameBoard initialBoard;

	public ExhaustingMovesGameSimulator(
			final ExhaustingMovesGameBoard initialBoard) {
		this.initialBoard = initialBoard;
	}

	public void calculateAndPrintQuickestWayToFinish() {
		printMoves(getQuickestFinalMoveState(new MoveNode(null, initialBoard)));
	}

	private void printMoves(final MoveNode finalNode) {
		logger.info("THE BEST PLAN IS:");
		logger.info(System.getProperty("line.separator")
				+ finalNode.toResultString());
	}

	@SuppressWarnings("unchecked")
	private MoveNode getQuickestFinalMoveState(final MoveNode node) {
		if (node.isRepeat()) {
			return null;
		}
		if (node.moveState.hasPossibleMoves()) {
			MoveNode leastPossibleDurationPath = null;
			// attempting to calculate the least duration of the possible
			// options for the rest of the game.
			for (ExhaustingMovesGameBoard newState : ((GameBoardMoveContext<ExhaustingMovesGameBoard>) node.moveState
					.getMoveContext()).execute(node.moveState)) {
				final MoveNode bestPathStartingAtNextPossibleMove = getQuickestFinalMoveState(new MoveNode(
						node, newState));
				if (nextPathBeatsPreviousBestPath(leastPossibleDurationPath,
						bestPathStartingAtNextPossibleMove)) {
					leastPossibleDurationPath = bestPathStartingAtNextPossibleMove;
				}
			}
			return leastPossibleDurationPath;
		}
		logger.debug("A possible solution is: ");
		logger.debug(System.getProperty("line.separator")
				+ node.toResultString());

		return node;
	}

	private boolean nextPathBeatsPreviousBestPath(
			final MoveNode leastPossibleDurationPath,
			final MoveNode bestPathStartingAtNextPossibleMove) {
		return bestPathStartingAtNextPossibleMove != null
				&& (leastPossibleDurationPath == null || bestPathStartingAtNextPossibleMove.moveState
						.getDuration() < leastPossibleDurationPath.moveState
						.getDuration());
	}

	private final class MoveNode {
		private final MoveNode parent;
		private final ExhaustingMovesGameBoard moveState;

		private MoveNode(final MoveNode parent,
				final ExhaustingMovesGameBoard moveState) {
			this.parent = parent;
			this.moveState = moveState;
		}

		/**
		 * Decides if this move state has been encountered in this game path. In
		 * this case we would probably want to abandon the game path for an
		 * Exhausting moves game.
		 * 
		 * @return true if we are repeating ourselves.
		 */
		public boolean isRepeat() {
			MoveNode currentParent = parent;
			while (currentParent != null) {
				if (currentParent.moveState.equals(this.moveState)) {
					return true;
				}
				currentParent = currentParent.parent;
			}
			return false;
		}

		public Object toResultString() {
			final Deque<MoveNode> stack = new ArrayDeque<MoveNode>();
			MoveNode currentNode = this;
			while (currentNode != null) {
				stack.push(currentNode);
				currentNode = currentNode.parent;
			}

			final StringBuilder builder = new StringBuilder();
			for (MoveNode node : stack) {
				builder.append(node.toString()
						+ System.getProperty("line.separator"));
			}

			return builder.toString();
		}

		@Override
		public String toString() {
			final StringBuilder builder = new StringBuilder();
			for (int i = 0; i < countLevels(); i++) {
				builder.append(" ");
			}
			builder.append(moveState.toString());
			return builder.toString();
		}

		/**
		 * Count the number of moves it took to make it to this state.
		 * 
		 * @return
		 */
		private int countLevels() {
			int levels = 0;
			MoveNode currentParent = parent;
			while (currentParent != null) {
				levels++;
				currentParent = currentParent.parent;
			}

			return levels;
		}
	}
}
