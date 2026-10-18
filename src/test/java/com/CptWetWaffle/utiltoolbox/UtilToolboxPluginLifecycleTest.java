package com.CptWetWaffle.utiltoolbox;

import com.CptWetWaffle.utiltoolbox.tools.mousetabbinds.MouseTabBindsTool;
import com.CptWetWaffle.utiltoolbox.tools.prayerflash.PrayerFlashTool;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.events.ConfigChanged;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UtilToolboxPluginLifecycleTest
{
	@Mock
	private EventBus eventBus;

	@Mock
	private MouseTabBindsTool mouseTabBindsTool;

	@Mock
	private PrayerFlashTool prayerFlashTool;

	@InjectMocks
	private UtilToolboxPlugin plugin;

	@Test
	public void startUpStartsOnlyEnabledTools()
	{
		when(mouseTabBindsTool.isEnabled()).thenReturn(true);
		when(prayerFlashTool.isEnabled()).thenReturn(false);

		plugin.startUp();

		verify(mouseTabBindsTool).startUp();
		verify(eventBus).register(mouseTabBindsTool);
		verify(prayerFlashTool, never()).startUp();
		verify(eventBus, never()).register(prayerFlashTool);
	}

	@Test
	public void toolStartsBeforeItIsRegisteredOnTheEventBus()
	{
		when(mouseTabBindsTool.isEnabled()).thenReturn(true);

		plugin.startUp();

		final InOrder order = inOrder(mouseTabBindsTool, eventBus);
		order.verify(mouseTabBindsTool).startUp();
		order.verify(eventBus).register(mouseTabBindsTool);
	}

	@Test
	public void enablingAToolStartsIt()
	{
		plugin.startUp();
		when(prayerFlashTool.isEnabled()).thenReturn(true);

		plugin.onConfigChanged(configChanged(UtilToolboxConfig.GROUP));

		verify(prayerFlashTool).startUp();
		verify(eventBus).register(prayerFlashTool);
	}

	@Test
	public void disablingAToolStopsIt()
	{
		when(prayerFlashTool.isEnabled()).thenReturn(true);
		plugin.startUp();
		when(prayerFlashTool.isEnabled()).thenReturn(false);

		plugin.onConfigChanged(configChanged(UtilToolboxConfig.GROUP));

		final InOrder order = inOrder(eventBus, prayerFlashTool);
		order.verify(eventBus).unregister(prayerFlashTool);
		order.verify(prayerFlashTool).shutDown();
	}

	@Test
	public void aToolThatIsAlreadyRunningIsNotStartedTwice()
	{
		when(prayerFlashTool.isEnabled()).thenReturn(true);
		plugin.startUp();

		plugin.onConfigChanged(configChanged(UtilToolboxConfig.GROUP));

		verify(prayerFlashTool, times(1)).startUp();
		verify(eventBus, times(1)).register(prayerFlashTool);
	}

	@Test
	public void aToolThatIsAlreadyStoppedIsNotStoppedAgain()
	{
		plugin.startUp();

		plugin.onConfigChanged(configChanged(UtilToolboxConfig.GROUP));

		verify(prayerFlashTool, never()).shutDown();
		verify(eventBus, never()).unregister(prayerFlashTool);
	}

	@Test
	public void ignoresOtherConfigGroups()
	{
		plugin.startUp();
		when(prayerFlashTool.isEnabled()).thenReturn(true);

		plugin.onConfigChanged(configChanged("some-other-plugin"));

		verify(prayerFlashTool, never()).startUp();
	}

	@Test
	public void ignoresConfigChangesBeforeStartUp()
	{
		when(prayerFlashTool.isEnabled()).thenReturn(true);

		plugin.onConfigChanged(configChanged(UtilToolboxConfig.GROUP));

		verify(prayerFlashTool, never()).startUp();
	}

	@Test
	public void shutDownStopsRunningToolsOnly()
	{
		when(mouseTabBindsTool.isEnabled()).thenReturn(true);
		plugin.startUp();

		plugin.shutDown();

		verify(eventBus).unregister(mouseTabBindsTool);
		verify(mouseTabBindsTool).shutDown();
		verify(prayerFlashTool, never()).shutDown();
	}

	@Test
	public void toolsCanBeStartedAgainAfterShutDown()
	{
		when(mouseTabBindsTool.isEnabled()).thenReturn(true);
		plugin.startUp();
		plugin.shutDown();

		plugin.startUp();

		verify(mouseTabBindsTool, times(2)).startUp();
	}

	@Test
	public void ignoresConfigChangesAfterShutDown()
	{
		plugin.startUp();
		plugin.shutDown();
		when(prayerFlashTool.isEnabled()).thenReturn(true);

		plugin.onConfigChanged(configChanged(UtilToolboxConfig.GROUP));

		verify(prayerFlashTool, never()).startUp();
	}

	private static ConfigChanged configChanged(String group)
	{
		final ConfigChanged event = new ConfigChanged();
		event.setGroup(group);
		return event;
	}
}
