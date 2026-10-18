package com.CptWetWaffle.utiltoolbox.tools.prayerflash;

import com.CptWetWaffle.utiltoolbox.UtilToolboxConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import net.runelite.client.ui.overlay.OverlayManager;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PrayerFlashToolTest
{
	@Mock
	private OverlayManager overlayManager;

	@Mock
	private PrayerFlashOverlay overlay;

	@Mock
	private UtilToolboxConfig config;

	@InjectMocks
	private PrayerFlashTool tool;

	@Test
	public void followsItsConfigToggle()
	{
		when(config.lowPrayerFlashEnabled()).thenReturn(true, false);

		assertTrue(tool.isEnabled());
		assertFalse(tool.isEnabled());
	}

	@Test
	public void startUpAddsTheOverlay()
	{
		tool.startUp();

		verify(overlayManager).add(overlay);
	}

	@Test
	public void shutDownRemovesTheOverlay()
	{
		tool.shutDown();

		verify(overlayManager).remove(overlay);
	}
}
