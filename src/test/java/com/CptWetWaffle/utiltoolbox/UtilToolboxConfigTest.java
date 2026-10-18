package com.CptWetWaffle.utiltoolbox;

import com.CptWetWaffle.utiltoolbox.tools.mousetabbinds.SideTab;
import java.awt.Color;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

/**
 * Config defaults are user-facing behavior: renaming or changing one silently changes every saved profile.
 */
public class UtilToolboxConfigTest
{
	private final UtilToolboxConfig config = mock(UtilToolboxConfig.class, CALLS_REAL_METHODS);

	@Test
	public void groupNameIsSpecific()
	{
		assertEquals("util-toolbox", UtilToolboxConfig.GROUP);
	}

	@Test
	public void everyToolIsOffByDefault()
	{
		assertFalse(config.mouseTabBindsEnabled());
		assertFalse(config.lowPrayerFlashEnabled());
	}

	@Test
	public void noMouseButtonIsBoundByDefault()
	{
		assertEquals(SideTab.NONE, config.mouseTabMiddle());
		assertEquals(SideTab.NONE, config.mouseTabButton4());
		assertEquals(SideTab.NONE, config.mouseTabButton5());
	}

	@Test
	public void prayerThresholdDefaultsToTen()
	{
		assertEquals(10, config.lowPrayerThreshold());
	}

	@Test
	public void prayerFlashIsPaleGreenAndTranslucent()
	{
		final Color color = config.lowPrayerFlashColor();

		assertTrue(color.getGreen() > color.getRed());
		assertTrue(color.getGreen() > color.getBlue());
		assertTrue(color.getAlpha() > 0 && color.getAlpha() < 255);
	}
}
