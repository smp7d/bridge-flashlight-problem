package org.sample.bridge;

import java.util.Set;

/**
 * Algorithm to get possible moves from original to destination side. On a hunch
 * (based on trying to clear bridge as quickly as we can), we will always try to
 * move two people across the bridge if possible. This can be thought of as a
 * "business logic optimization" on PossibleCrossStrategy. Use
 * PossibleCrossStrategy if you want to see more potential paths.
 * 
 */
public class AggressiveCrossStrategy extends PossibleCrossStrategy {

	@Override
	public Set<BridgeFlashlightGameBoard> calculateNextMoves(
			final BridgeFlashlightGameBoard currentBridgeState) {
		if (noLegalMovesExistForStrategy(currentBridgeState)) {
			return null;
		}
		if (onlyOneLeft(currentBridgeState)) {
			return moveSingleCrosser(currentBridgeState);
		} else {
			return moveTwoCrossers(currentBridgeState);
		}
	}

	private boolean onlyOneLeft(
			final BridgeFlashlightGameBoard currentBridgeState) {
		return currentBridgeState.getOriginalSidePeople().size() == 1;
	}

}
