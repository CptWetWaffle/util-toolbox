package com.CptWetWaffle.utiltoolbox.tools.prayerflash;

import com.CptWetWaffle.utiltoolbox.UtilToolboxConfig;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Skill;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

@Singleton
class PrayerFlashOverlay extends Overlay
{
	private static final long PERIOD_MS = 1200;

	private final Client client;
	private final UtilToolboxConfig config;

	@Inject
	private PrayerFlashOverlay(Client client, UtilToolboxConfig config)
	{
		this.client = client;
		this.config = config;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ALWAYS_ON_TOP);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (client.getGameState() != GameState.LOGGED_IN
			|| client.getBoostedSkillLevel(Skill.PRAYER) >= config.lowPrayerThreshold())
		{
			return null;
		}

		// Triangle wave: fades in and out once per period
		final double phase = (System.currentTimeMillis() % PERIOD_MS) / (double) PERIOD_MS;
		final double pulse = 1 - Math.abs(2 * phase - 1);

		final Color color = config.lowPrayerFlashColor();
		graphics.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (color.getAlpha() * pulse)));
		graphics.fillRect(0, 0, client.getCanvasWidth(), client.getCanvasHeight());
		return null;
	}
}
