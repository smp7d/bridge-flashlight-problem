package org.sample.bridge;

import junit.framework.Assert;

import org.junit.Test;

public class CrosserTest {
	@Test
	public void testEquals() {
		Assert.assertTrue(new Crosser(10L, "Ten")
				.equals(new Crosser(10L, "Ten")));
		Assert.assertFalse(new Crosser(10L, "Teno").equals(new Crosser(10L,
				"Ten")));
		Assert.assertFalse(new Crosser(11L, "Ten").equals(new Crosser(10L,
				"Ten")));
	}

	@Test
	public void testHashCode() {
		Assert.assertEquals(new Crosser(10L, "Ten").hashCode(), new Crosser(
				10L, "Ten").hashCode());
		Assert.assertNotSame(new Crosser(10L, "Teno").hashCode(), new Crosser(
				10L, "Ten").hashCode());
		Assert.assertNotSame(new Crosser(11L, "Ten").hashCode(), new Crosser(
				10L, "Ten").hashCode());
	}
}
