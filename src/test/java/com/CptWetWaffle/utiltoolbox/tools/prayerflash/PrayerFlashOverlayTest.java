package com.CptWetWaffle.utiltoolbox.tools.prayerflash;

import com.CptWetWaffle.utiltoolbox.UtilToolboxConfig;
import java.awt.Color;
import java.awt.Graphics2D;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Skill;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PrayerFlashOverlayTest
{
	private static final Color FLASH = new Color(144, 238, 144, 100);

	@Mock
	private Client client;

	@Mock
	private UtilToolboxConfig config;

	@Mock
	private Graphics2D graphics;

	private PrayerFlashOverlay overlay;

	@Before
	public void setUp()
	{
		overlay = new PrayerFlashOverlay(client, config);
	}

	@Test
	public void doesNotDrawOutsideTheGame()
	{
		when(client.getGameState()).thenReturn(GameState.LOGIN_SCREEN);

		overlay.render(graphics);

		verifyNoInteractions(graphics);
	}

	@Test
	public void doesNotDrawWhenPrayerEqualsThreshold()
	{
		loggedInWithPrayer(10, 10);

		overlay.render(graphics);

		verifyNoInteractions(graphics);
	}

	@Test
	public void doesNotDrawWhenPrayerIsAboveThreshold()
	{
		loggedInWithPrayer(50, 10);

		overlay.render(graphics);

		verifyNoInteractions(graphics);
	}

	@Test
	public void doesNotDrawWhenThresholdIsZero()
	{
		loggedInWithPrayer(0, 0);

		overlay.render(graphics);

		verifyNoInteractions(graphics);
	}

	@Test
	public void fillsTheScreenAtFullStrengthMidCycle()
	{
		loggedInWithPrayer(9, 10);
		drawing(800, 600, PrayerFlashOverlay.PERIOD_MS / 2);

		overlay.render(graphics);

		verify(graphics).setColor(FLASH);
		verify(graphics).fillRect(0, 0, 800, 600);
	}

	@Test
	public void isTransparentAtTheStartOfTheCycle()
	{
		loggedInWithPrayer(0, 10);
		drawing(800, 600, 0);

		overlay.render(graphics);

		verify(graphics).setColor(new Color(144, 238, 144, 0));
	}

	@Test
	public void fadesLinearlyThroughTheCycle()
	{
		loggedInWithPrayer(1, 10);
		drawing(800, 600, PrayerFlashOverlay.PERIOD_MS / 4);

		overlay.render(graphics);

		verify(graphics).setColor(new Color(144, 238, 144, 50));
	}

	@Test
	public void fadesBackOutInTheSecondHalf()
	{
		loggedInWithPrayer(1, 10);
		drawing(800, 600, PrayerFlashOverlay.PERIOD_MS * 3 / 4);

		overlay.render(graphics);

		verify(graphics).setColor(new Color(144, 238, 144, 50));
	}

	@Test
	public void repeatsEveryPeriod()
	{
		loggedInWithPrayer(1, 10);
		drawing(800, 600, PrayerFlashOverlay.PERIOD_MS * 5 + PrayerFlashOverlay.PERIOD_MS / 2);

		overlay.render(graphics);

		verify(graphics).setColor(FLASH);
	}

	@Test
	public void stopsDrawingOnceThresholdIsRestored()
	{
		loggedInWithPrayer(10, 10);

		overlay.render(graphics);

		verify(graphics, never()).fillRect(anyInt(), anyInt(), anyInt(), anyInt());
	}

	private void loggedInWithPrayer(int prayer, int threshold)
	{
		when(client.getGameState()).thenReturn(GameState.LOGGED_IN);
		when(client.getBoostedSkillLevel(Skill.PRAYER)).thenReturn(prayer);
		when(config.lowPrayerThreshold()).thenReturn(threshold);
	}

	private void drawing(int width, int height, long nowMs)
	{
		when(config.lowPrayerFlashColor()).thenReturn(FLASH);
		when(client.getCanvasWidth()).thenReturn(width);
		when(client.getCanvasHeight()).thenReturn(height);
		overlay.clock = () -> nowMs;
	}
}
