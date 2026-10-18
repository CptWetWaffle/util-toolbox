package com.CptWetWaffle.utiltoolbox.tools.mousetabbinds;

import com.CptWetWaffle.utiltoolbox.UtilToolboxConfig;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.MenuAction;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.input.MouseManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MouseTabBindsToolTest
{
	private static final int INVENTORY_FIXED = InterfaceID.Toplevel.STONE3;
	private static final int INVENTORY_CLASSIC = InterfaceID.ToplevelOsrsStretch.STONE3;

	@Mock
	private Client client;

	@Mock
	private ClientThread clientThread;

	@Mock
	private MouseManager mouseManager;

	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private UtilToolboxConfig config;

	@InjectMocks
	private MouseTabBindsTool tool;

	@Before
	public void setUp()
	{
		when(client.getGameState()).thenReturn(GameState.LOGGED_IN);
	}

	@Test
	public void followsItsConfigToggle()
	{
		assertFalse(tool.isEnabled());

		doReturn(true).when(config).mouseTabBindsEnabled();

		assertTrue(tool.isEnabled());
	}

	@Test
	public void startUpAndShutDownManageTheMouseListener()
	{
		tool.startUp();
		verify(mouseManager).registerMouseListener(tool);

		tool.shutDown();
		verify(mouseManager).unregisterMouseListener(tool);
	}

	@Test
	public void ignoresButtonsThatAreNotBound()
	{
		final MouseEvent event = press(4);

		tool.mousePressed(event);

		assertFalse(event.isConsumed());
		verifyNoInteractions(clientThread);
	}

	@Test
	public void ignoresPressesWithNoButton()
	{
		doReturn(SideTab.INVENTORY).when(config).mouseTabButton4();
		final MouseEvent event = press(MouseEvent.NOBUTTON);

		tool.mousePressed(event);

		assertFalse(event.isConsumed());
	}

	@Test
	public void ignoresBoundButtonsBeforeLogin()
	{
		when(client.getGameState()).thenReturn(GameState.LOGIN_SCREEN);
		doReturn(SideTab.INVENTORY).when(config).mouseTabButton4();
		final MouseEvent event = press(4);

		tool.mousePressed(event);

		assertFalse(event.isConsumed());
		verifyNoInteractions(clientThread);
	}

	@Test
	public void consumesABoundButtonAndOpensItsTab()
	{
		doReturn(SideTab.INVENTORY).when(config).mouseTabButton4();
		stone(INVENTORY_FIXED, false, "Inventory");
		final MouseEvent event = press(4);

		tool.mousePressed(event);
		runQueuedClientWork();

		assertTrue(event.isConsumed());
		verify(client).menuAction(-1, INVENTORY_FIXED, MenuAction.CC_OP, 1, -1, "Inventory", "");
	}

	@Test
	public void middleClickCanBeBound()
	{
		doReturn(SideTab.INVENTORY).when(config).mouseTabMiddle();
		stone(INVENTORY_FIXED, false, "Inventory");
		final MouseEvent event = press(MouseEvent.BUTTON2);

		tool.mousePressed(event);
		runQueuedClientWork();

		assertTrue(event.isConsumed());
		verify(client).menuAction(-1, INVENTORY_FIXED, MenuAction.CC_OP, 1, -1, "Inventory", "");
	}

	@Test
	public void usesTheFirstActionTheWidgetHas()
	{
		doReturn(SideTab.INVENTORY).when(config).mouseTabButton4();
		stone(INVENTORY_FIXED, false, null, "Inventory", "Other");

		tool.mousePressed(press(4));
		runQueuedClientWork();

		verify(client).menuAction(-1, INVENTORY_FIXED, MenuAction.CC_OP, 2, -1, "Inventory", "");
	}

	@Test
	public void skipsAHiddenLayoutAndUsesTheVisibleOne()
	{
		doReturn(SideTab.INVENTORY).when(config).mouseTabButton4();
		stone(INVENTORY_FIXED, true, "Inventory");
		stone(INVENTORY_CLASSIC, false, "Inventory");

		tool.mousePressed(press(4));
		runQueuedClientWork();

		verify(client).menuAction(-1, INVENTORY_CLASSIC, MenuAction.CC_OP, 1, -1, "Inventory", "");
	}

	@Test
	public void stillConsumesTheButtonWhenNoTabIsOnScreen()
	{
		doReturn(SideTab.INVENTORY).when(config).mouseTabButton4();
		final MouseEvent event = press(4);

		tool.mousePressed(event);
		runQueuedClientWork();

		assertTrue(event.isConsumed());
		verify(client, never()).menuAction(anyInt(), anyInt(), any(), anyInt(), anyInt(), anyString(), anyString());
	}

	@Test
	public void doesNothingWhenTheTabHasNoActions()
	{
		doReturn(SideTab.INVENTORY).when(config).mouseTabButton4();
		final Widget widget = stone(INVENTORY_FIXED, false);
		when(widget.getActions()).thenReturn(null);

		tool.mousePressed(press(4));
		runQueuedClientWork();

		verify(client, never()).menuAction(anyInt(), anyInt(), any(), anyInt(), anyInt(), anyString(), anyString());
	}

	@Test
	public void swallowsTheReleaseAndClickOfAConsumedButton()
	{
		doReturn(SideTab.INVENTORY).when(config).mouseTabButton4();
		tool.mousePressed(press(4));

		final MouseEvent release = event(4);
		final MouseEvent click = event(4);
		tool.mouseReleased(release);
		tool.mouseClicked(click);

		assertTrue(release.isConsumed());
		assertTrue(click.isConsumed());
	}

	@Test
	public void leavesOtherButtonsAloneWhileAnotherIsConsumed()
	{
		doReturn(SideTab.INVENTORY).when(config).mouseTabButton4();
		tool.mousePressed(press(4));

		final MouseEvent release = event(MouseEvent.BUTTON1);
		tool.mouseReleased(release);

		assertFalse(release.isConsumed());
	}

	@Test
	public void leavesReleasesAloneWhenNothingWasConsumed()
	{
		final MouseEvent release = event(4);

		tool.mouseReleased(release);

		assertFalse(release.isConsumed());
	}

	@Test
	public void stopsSwallowingAfterShutDown()
	{
		doReturn(SideTab.INVENTORY).when(config).mouseTabButton4();
		tool.mousePressed(press(4));
		tool.shutDown();

		final MouseEvent release = event(4);
		tool.mouseReleased(release);

		assertFalse(release.isConsumed());
	}

	private MouseEvent press(int button)
	{
		return event(button);
	}

	/**
	 * A mock rather than a real event: AWT refuses to build events for buttons above 3 without a display.
	 */
	private MouseEvent event(int button)
	{
		final MouseEvent event = mock(MouseEvent.class);
		final AtomicBoolean consumed = new AtomicBoolean();
		when(event.getButton()).thenReturn(button);
		when(event.isConsumed()).thenAnswer(invocation -> consumed.get());
		doAnswer(invocation ->
		{
			consumed.set(true);
			return null;
		}).when(event).consume();
		return event;
	}

	private Widget stone(int widgetId, boolean hidden, String... actions)
	{
		final Widget widget = mock(Widget.class);
		when(widget.getId()).thenReturn(widgetId);
		when(widget.isHidden()).thenReturn(hidden);
		when(widget.getActions()).thenReturn(actions);
		when(client.getWidget(widgetId)).thenReturn(widget);
		return widget;
	}

	private void runQueuedClientWork()
	{
		final ArgumentCaptor<Runnable> work = ArgumentCaptor.forClass(Runnable.class);
		verify(clientThread).invoke(work.capture());
		work.getValue().run();
	}
}
