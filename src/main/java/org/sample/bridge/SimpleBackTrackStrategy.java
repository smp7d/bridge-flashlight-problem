package org.sample.bridge;

import java.util.HashSet;
import java.util.Set;

import org.sample.GameBoardPotentialMovesStrategy;

/**
 * A bridge backtracking strategy that will find one crosser bringing flashlight
 * back to the original side.
 * 
 */
public class SimpleBackTrackStrategy implements
		GameBoardPotentialMovesStrategy<BridgeFlashlightGameBoard> {

	@Override
	public Set<BridgeFlashlightGameBoard> calculateNextMoves(
			final BridgeFlashlightGameBoard currentBridgeState) {
		if (noLegalMovesExistForStrategy(currentBridgeState)) {
			return null;
		}

		final Set<BridgeFlashlightGameBoard> nextMoves = new HashSet<BridgeFlashlightGameBoard>();
		for (Crosser destinationSideCrosser : currentBridgeState
				.getDestinationSidePeople()) {
			final Set<Crosser> destinationSideCrossers = new HashSet<Crosser>(
					currentBridgeState.getDestinationSidePeople());
			final Set<Crosser> originalSideCrossers = new HashSet<Crosser>(
					currentBridgeState.getOriginalSidePeople());
			destinationSideCrossers.remove(destinationSideCrosser);
			originalSideCrossers.add(destinationSideCrosser);
			nextMoves.add(new BridgeFlashlightGameBoard(originalSideCrossers,
					destinationSideCrossers, currentBridgeState.getDuration()
							+ destinationSideCrosser.getCrossSpeed(), true));
		}

		return nextMoves;
	}

	private boolean noLegalMovesExistForStrategy(
			final BridgeFlashlightGameBoard currentBridgeState) {
		return currentBridgeState.getDestinationSidePeople().size() == 0
				|| currentBridgeState.isFlashlightOnOriginalSide();
	}

}
