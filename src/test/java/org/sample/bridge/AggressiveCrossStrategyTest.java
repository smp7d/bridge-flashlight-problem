package org.sample.bridge;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

public class AggressiveCrossStrategyTest {

	@Test
	public void testCalculateNextMoves_SingleCrosser() {
		AggressiveCrossStrategy strategy = new AggressiveCrossStrategy();
		BridgeFlashlightGameBoard board = new BridgeFlashlightGameBoard.InitialBuilder(
				new Crosser(0, "0")).build();
		Set<BridgeFlashlightGameBoard> nextStates = strategy
				.calculateNextMoves(board);
		Assert.assertEquals(1, nextStates.size());
		BridgeFlashlightGameBoard state = nextStates.iterator().next();
		Assert.assertEquals(1, state.getDestinationSidePeople().size());
		Assert.assertEquals(0, state.getOriginalSidePeople().size());
		Assert.assertFalse(state.isFlashlightOnOriginalSide());
		Assert.assertNull(strategy.calculateNextMoves(state));
	}

	@Test
	public void testCalculateNextMoves_MultipleCrossers() {
		AggressiveCrossStrategy strategy = new AggressiveCrossStrategy();
		BridgeFlashlightGameBoard board = new BridgeFlashlightGameBoard.InitialBuilder(
				new Crosser(0L, "0"), new Crosser(1L, "1"),
				new Crosser(5L, "5")).build();
		Set<BridgeFlashlightGameBoard> nextStates = strategy
				.calculateNextMoves(board);
		Assert.assertEquals(3, nextStates.size());
		Set<Set<Crosser>> destinationResults = new HashSet<Set<Crosser>>();
		Set<Set<Crosser>> originalResults = new HashSet<Set<Crosser>>();
		for (BridgeFlashlightGameBoard bridge : nextStates) {
			Assert.assertFalse(bridge.isFlashlightOnOriginalSide());
			Assert.assertEquals(bridge.getDestinationSidePeople().size(), 2);
			destinationResults.add(bridge.getDestinationSidePeople());
			Assert.assertEquals(bridge.getOriginalSidePeople().size(), 1);
			originalResults.add(bridge.getOriginalSidePeople());
			Assert.assertFalse(bridge.getDestinationSidePeople().contains(
					bridge.getOriginalSidePeople().iterator().next()));
			long expectedDuration = 0L;
			for (Crosser crossed : bridge.getDestinationSidePeople()) {
				expectedDuration = Math.max(expectedDuration, crossed
						.getCrossSpeed());
			}
			Assert.assertEquals(expectedDuration, bridge.getDuration());
			Assert.assertNull(strategy.calculateNextMoves(bridge));
		}
		// ensure that all results are unique
		Assert.assertEquals(3, destinationResults.size());
		Assert.assertEquals(3, originalResults.size());
	}
}
