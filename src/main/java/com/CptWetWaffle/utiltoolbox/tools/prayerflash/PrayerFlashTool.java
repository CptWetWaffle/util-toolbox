package com.CptWetWaffle.utiltoolbox.tools.prayerflash;

import com.CptWetWaffle.utiltoolbox.UtilToolboxConfig;
import com.CptWetWaffle.utiltoolbox.tools.Tool;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.client.ui.overlay.OverlayManager;

/**
 * Flashes the screen when prayer points drop below a player-set threshold.
 */
@Singleton
public class PrayerFlashTool implements Tool
{
	@Inject
	private OverlayManager overlayManager;

	@Inject
	private PrayerFlashOverlay overlay;

	@Inject
	private UtilToolboxConfig config;

	@Override
	public boolean isEnabled()
	{
		return config.lowPrayerFlashEnabled();
	}

	@Override
	public void startUp()
	{
		overlayManager.add(overlay);
	}

	@Override
	public void shutDown()
	{
		overlayManager.remove(overlay);
	}
}
