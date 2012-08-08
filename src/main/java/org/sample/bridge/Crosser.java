package org.sample.bridge;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A person who crosses bridges at a certain speed.
 * 
 */
public class Crosser {
	/**
	 * Cross speed in time units (positive).
	 */
	private final long crossSpeed;
	private final String name;

	public Crosser(final long crossSpeed, final String name) {
		this.crossSpeed = crossSpeed;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public long getCrossSpeed() {
		return crossSpeed;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object other) {
		return other != null && other.getClass() == this.getClass()
				&& ((Crosser) other).getCrossSpeed() == this.crossSpeed
				&& ((Crosser) other).getName().equals(name);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(crossSpeed).append(name)
				.toHashCode();
	}
}
