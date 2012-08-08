package org.sample.drivers;

import org.sample.ExhaustingMovesGameSimulator;
import org.sample.bridge.BridgeFlashlightGameBoard;
import org.sample.bridge.Crosser;

public final class BridgeFlashlightGameDriver {
	private BridgeFlashlightGameDriver(){
		
	}
	
	public static void main(String[] args) {
		ExhaustingMovesGameSimulator sim = new ExhaustingMovesGameSimulator(
				new BridgeFlashlightGameBoard.InitialBuilder(new Crosser(1L,
						"One"), new Crosser(2L, "Two"),
						new Crosser(5L, "Five"), new Crosser(10L, "Ten"))
						.build());
		sim.calculateAndPrintQuickestWayToFinish();
	}
}
