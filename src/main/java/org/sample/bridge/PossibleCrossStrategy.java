package org.sample.bridge;

import java.util.HashSet;
import java.util.Set;

import org.sample.GameBoardPotentialMovesStrategy;

/**
 * Algo that calculates all possible states after a cross over the bridge.
 * 
 */
public class PossibleCrossStrategy implements
		GameBoardPotentialMovesStrategy<BridgeFlashlightGameBoard> {

	/**
	 * Needs to determine all potential 2 person crosses.
	 * 
	 * @param currentBridgeState the current bridge state
	 * @return possible results
	 */
	protected Set<BridgeFlashlightGameBoard> moveTwoCrossers(
			final BridgeFlashlightGameBoard currentBridgeState) {
		final Set<BridgeFlashlightGameBoard> nextMoves = new HashSet<BridgeFlashlightGameBoard>();
		final Set<Crosser> potentialPassengerCrossers = new HashSet<Crosser>(
				currentBridgeState.getOriginalSidePeople());
		for (Crosser mainCrosser : currentBridgeState.getOriginalSidePeople()) {
			potentialPassengerCrossers.remove(mainCrosser);
			for (Crosser passengerCrosser : potentialPassengerCrossers) {
				final Set<Crosser> destinationSideCrossers = new HashSet<Crosser>(
						currentBridgeState.getDestinationSidePeople());
				final Set<Crosser> originalSideCrossers = new HashSet<Crosser>(
						currentBridgeState.getOriginalSidePeople());
				moveCrosser(passengerCrosser, destinationSideCrossers,
						originalSideCrossers);
				moveCrosser(mainCrosser, destinationSideCrossers,
						originalSideCrossers);
				nextMoves.add(new BridgeFlashlightGameBoard(
						originalSideCrossers, destinationSideCrossers,
						currentBridgeState.getDuration()
								+ calculateCrossSpeed(mainCrosser,
										passengerCrosser),
						false));
			}
		}

		return nextMoves;
	}

	private long calculateCrossSpeed(Crosser mainCrosser,
			Crosser passengerCrosser) {
		return Math.max(mainCrosser.getCrossSpeed(),
				passengerCrosser.getCrossSpeed());
	}

	private void moveCrosser(final Crosser passengerCrosser,
			final Set<Crosser> destinationSideCrossers,
			final Set<Crosser> originalSideCrossers) {
		destinationSideCrossers.add(passengerCrosser);
		originalSideCrossers.remove(passengerCrosser);
	}

	protected Set<BridgeFlashlightGameBoard> moveSingleCrosser(
			final BridgeFlashlightGameBoard currentBridgeState) {
		final Set<BridgeFlashlightGameBoard> nextMoves = new HashSet<BridgeFlashlightGameBoard>();
		for (Crosser crosser : currentBridgeState.getOriginalSidePeople()) {
			final Set<Crosser> destinationSideCrossers = new HashSet<Crosser>(
					currentBridgeState.getDestinationSidePeople());
			final Set<Crosser> currentSideCrossers = new HashSet<Crosser>(
					currentBridgeState.getOriginalSidePeople());
			moveCrosser(crosser, destinationSideCrossers, currentSideCrossers);
			nextMoves.add(new BridgeFlashlightGameBoard(currentSideCrossers,
					destinationSideCrossers, currentBridgeState.getDuration()
							+ crosser.getCrossSpeed(), false));
		}
		return nextMoves;
	}

	@Override
	public Set<BridgeFlashlightGameBoard> calculateNextMoves(
			BridgeFlashlightGameBoard currentBridgeState) {
		if (noLegalMovesExistForStrategy(currentBridgeState)) {
			return null;
		}

		final Set<BridgeFlashlightGameBoard> nextMoves = moveSingleCrosser(currentBridgeState);
		nextMoves.addAll(moveTwoCrossers(currentBridgeState));
		
		return nextMoves;
	}

	protected boolean noLegalMovesExistForStrategy(
			BridgeFlashlightGameBoard currentBridgeState) {
		return !currentBridgeState.hasPossibleMoves()
				|| !currentBridgeState.isFlashlightOnOriginalSide();
	}
}
