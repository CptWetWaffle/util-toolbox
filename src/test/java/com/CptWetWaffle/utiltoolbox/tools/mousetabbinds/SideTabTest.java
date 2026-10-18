package com.CptWetWaffle.utiltoolbox.tools.mousetabbinds;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SideTabTest
{
	@Test
	public void noneHasNoWidgets()
	{
		assertEquals(-1, SideTab.NONE.getFixedWidgetId());
		assertEquals(-1, SideTab.NONE.getResizableClassicWidgetId());
		assertEquals(-1, SideTab.NONE.getResizableModernWidgetId());
	}

	@Test
	public void everyRealTabHasADistinctWidgetInEveryLayout()
	{
		final Set<Integer> seen = new HashSet<>();
		for (SideTab tab : SideTab.values())
		{
			if (tab == SideTab.NONE)
			{
				continue;
			}

			assertTrue(tab + " fixed", seen.add(tab.getFixedWidgetId()));
			assertTrue(tab + " classic", seen.add(tab.getResizableClassicWidgetId()));
			assertTrue(tab + " modern", seen.add(tab.getResizableModernWidgetId()));
		}
	}

	@Test
	public void realTabWidgetsAreValid()
	{
		for (SideTab tab : SideTab.values())
		{
			if (tab != SideTab.NONE)
			{
				assertTrue(tab.toString(), tab.getFixedWidgetId() > 0);
				assertTrue(tab.toString(), tab.getResizableClassicWidgetId() > 0);
				assertTrue(tab.toString(), tab.getResizableModernWidgetId() > 0);
			}
		}
	}

	@Test
	public void displayNamesAreUniqueAndNotBlank()
	{
		final Set<String> names = new HashSet<>();
		for (SideTab tab : SideTab.values())
		{
			assertFalse(tab.name(), tab.toString().trim().isEmpty());
			assertTrue(tab.toString(), names.add(tab.toString()));
		}
	}
}
