package com.CptWetWaffle.utiltoolbox.tools.mousetabbinds;

import com.CptWetWaffle.utiltoolbox.UtilToolboxConfig;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class MouseButtonTest
{
	@Test
	public void mapsToAwtButtonNumbers()
	{
		assertEquals(MouseEvent.BUTTON2, MouseButton.MIDDLE.getAwtButton());
		assertEquals(4, MouseButton.BUTTON_4.getAwtButton());
		assertEquals(5, MouseButton.BUTTON_5.getAwtButton());
	}

	@Test
	public void eachButtonHasItsOwnNumber()
	{
		final Set<Integer> seen = new HashSet<>();
		for (MouseButton button : MouseButton.values())
		{
			assertTrue(button.name(), seen.add(button.getAwtButton()));
		}
	}

	@Test
	public void readsItsOwnConfigItem()
	{
		final UtilToolboxConfig config = mock(UtilToolboxConfig.class, CALLS_REAL_METHODS);
		doReturn(SideTab.COMBAT).when(config).mouseTabMiddle();
		doReturn(SideTab.PRAYER).when(config).mouseTabButton4();
		doReturn(SideTab.MAGIC).when(config).mouseTabButton5();

		assertEquals(SideTab.COMBAT, MouseButton.MIDDLE.getBinding().apply(config));
		assertEquals(SideTab.PRAYER, MouseButton.BUTTON_4.getBinding().apply(config));
		assertEquals(SideTab.MAGIC, MouseButton.BUTTON_5.getBinding().apply(config));
	}
}
