package org.sample.bridge;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

public class BridgeFlashlightGameBoardTest {

	@Test
	public void testBuilder() {
		Crosser p1 = new Crosser(1L, "1");
		Crosser p2 = new Crosser(2L, "2");
		Crosser p3 = new Crosser(5L, "5");
		Crosser p4 = new Crosser(10L, "10");
		BridgeFlashlightGameBoard board = new BridgeFlashlightGameBoard.InitialBuilder(
				p1, p2, p3, p4).build();
		Assert.assertTrue(board.isFlashlightOnOriginalSide());
		Assert.assertEquals(0, board.getDestinationSidePeople().size());
		Set<Crosser> crossers = new HashSet<Crosser>();
		crossers.add(p1);
		crossers.add(p2);
		crossers.add(p3);
		crossers.add(p4);
		Assert.assertEquals(crossers, board.getOriginalSidePeople());
	}

	@Test
	public void testEqualsAndHashCode() {
		Crosser p1 = new Crosser(1L, "1");
		Crosser p2 = new Crosser(2L, "2");
		Crosser p3 = new Crosser(5L, "5");
		Crosser p4 = new Crosser(10L, "10");
		Crosser p5 = new Crosser(10L, "10");
		BridgeFlashlightGameBoard board1 = new BridgeFlashlightGameBoard.InitialBuilder(
				p1, p2, p3, p4).build();
		BridgeFlashlightGameBoard board2 = new BridgeFlashlightGameBoard.InitialBuilder(
				p1, p2, p3, p5).build();
		Assert.assertEquals(board1, board2);
		Assert.assertEquals(board1.hashCode(), board2.hashCode());
	}
}
