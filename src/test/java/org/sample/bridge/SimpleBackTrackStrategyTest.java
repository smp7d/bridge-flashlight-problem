package org.sample.bridge;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

public class SimpleBackTrackStrategyTest {
	@Test
	public void testCalculateNextMoves_NoCrossers() {
		Assert
				.assertNull(new SimpleBackTrackStrategy()
						.calculateNextMoves(new BridgeFlashlightGameBoard.InitialBuilder(
								new Crosser(0L, "0"), new Crosser(1L, "1"),
								new Crosser(5L, "5")).build()));
	}

	@Test
	public void testCalculateNextMoves() {
		final SimpleBackTrackStrategy strategy = new SimpleBackTrackStrategy();
		final Set<Crosser> originalSidePeople = new HashSet<Crosser>();
		final long duration = 10L;
		final Set<Crosser> destinationSidePeople = new HashSet<Crosser>();
		destinationSidePeople.add(new Crosser(1L, "One"));
		destinationSidePeople.add(new Crosser(2L, "Two"));
		final BridgeFlashlightGameBoard board = new BridgeFlashlightGameBoard(
				originalSidePeople, destinationSidePeople, duration, false);
		Set<BridgeFlashlightGameBoard> nextStates = strategy
				.calculateNextMoves(board);
		Assert.assertEquals(destinationSidePeople.size(), nextStates.size());
		for (BridgeFlashlightGameBoard nextState : nextStates) {
			Assert.assertNull(strategy.calculateNextMoves(nextState));
			Assert.assertEquals(1, nextState.getOriginalSidePeople().size());
			Assert.assertEquals(destinationSidePeople.size() - 1, nextState
					.getDestinationSidePeople().size());
			Assert.assertFalse(nextState.getDestinationSidePeople().contains(
					nextState.getOriginalSidePeople().iterator().next()));
			Assert.assertEquals(duration
					+ nextState.getOriginalSidePeople().iterator().next()
							.getCrossSpeed(), nextState.getDuration());
		}
	}
}
