package org.sample;

import org.junit.Ignore;
import org.junit.Test;
import org.sample.bridge.BridgeFlashlightGameBoard;
import org.sample.bridge.Crosser;

public class ExhaustingMovesGameSimulatorTest {

	/**
	 * No assertions, used for debugging and sanity check. THIS IS NOT A UNIT TEST.
	 */
	@Ignore
	@Test
	public void testCalculateAndPrintQuickestWayToFinish() {
		ExhaustingMovesGameSimulator sim = new ExhaustingMovesGameSimulator(
				new BridgeFlashlightGameBoard.InitialBuilder(new Crosser(1L,
						"One"), new Crosser(2L, "Two"),
						new Crosser(5L, "Five"), new Crosser(10L, "Ten"))
						.build());
		sim.calculateAndPrintQuickestWayToFinish();
	}
}
