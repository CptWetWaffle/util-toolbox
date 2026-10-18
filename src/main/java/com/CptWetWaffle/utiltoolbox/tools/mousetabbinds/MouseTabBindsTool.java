package com.CptWetWaffle.utiltoolbox.tools.mousetabbinds;

import com.CptWetWaffle.utiltoolbox.UtilToolboxConfig;
import com.CptWetWaffle.utiltoolbox.tools.Tool;
import java.awt.event.MouseEvent;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.MenuAction;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.input.MouseAdapter;
import net.runelite.client.input.MouseManager;

/**
 * Opens side panel tabs from mouse buttons, in place of the F-key binds. Works by activating the tab stone's own
 * click action, never by injecting input events.
 */
@Singleton
public class MouseTabBindsTool extends MouseAdapter implements Tool
{
	private static final TabStone[] STONES = TabStone.values();

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private MouseManager mouseManager;

	@Inject
	private UtilToolboxConfig config;

	/**
	 * AWT button whose press was consumed, so its matching release and click are swallowed too.
	 */
	private int consumedButton = MouseEvent.NOBUTTON;

	@Override
	public boolean isEnabled()
	{
		return config.mouseTabBindsEnabled();
	}

	@Override
	public void startUp()
	{
		mouseManager.registerMouseListener(this);
	}

	@Override
	public void shutDown()
	{
		mouseManager.unregisterMouseListener(this);
		consumedButton = MouseEvent.NOBUTTON;
	}

	@Override
	public MouseEvent mousePressed(MouseEvent e)
	{
		final TabStone stone = findBound(e.getButton());
		if (stone == null || client.getGameState() != GameState.LOGGED_IN)
		{
			return e;
		}

		consumedButton = e.getButton();
		e.consume();
		clientThread.invoke(() -> openTab(stone));
		return e;
	}

	@Override
	public MouseEvent mouseReleased(MouseEvent e)
	{
		return swallowIfConsumed(e);
	}

	@Override
	public MouseEvent mouseClicked(MouseEvent e)
	{
		return swallowIfConsumed(e);
	}

	private MouseEvent swallowIfConsumed(MouseEvent e)
	{
		if (consumedButton != MouseEvent.NOBUTTON && e.getButton() == consumedButton)
		{
			e.consume();
		}
		return e;
	}

	private TabStone findBound(int awtButton)
	{
		if (awtButton == MouseEvent.NOBUTTON)
		{
			return null;
		}

		for (TabStone stone : STONES)
		{
			if (stone.getBinding().apply(config).getAwtButton() == awtButton)
			{
				return stone;
			}
		}
		return null;
	}

	private void openTab(TabStone stone)
	{
		final Widget widget = findStoneWidget(stone);
		if (widget == null)
		{
			return;
		}

		final String[] actions = widget.getActions();
		if (actions == null)
		{
			return;
		}

		for (int i = 0; i < actions.length; i++)
		{
			if (actions[i] != null)
			{
				client.menuAction(-1, widget.getId(), MenuAction.CC_OP, i + 1, -1, actions[i], "");
				return;
			}
		}
	}

	private Widget findStoneWidget(TabStone stone)
	{
		final int[] ids = {stone.getFixedWidgetId(), stone.getResizableClassicWidgetId(), stone.getResizableModernWidgetId()};
		for (int id : ids)
		{
			final Widget widget = client.getWidget(id);
			if (widget != null && !widget.isHidden())
			{
				return widget;
			}
		}
		return null;
	}
}
