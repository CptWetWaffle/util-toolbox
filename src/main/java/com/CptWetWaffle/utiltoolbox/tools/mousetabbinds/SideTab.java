package com.CptWetWaffle.utiltoolbox.tools.mousetabbinds;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.gameval.InterfaceID;

/**
 * Side panel tabs a mouse button can open, with the widget id of each tab stone in the fixed, resizable classic and
 * resizable modern layouts.
 */
@Getter
@RequiredArgsConstructor
public enum SideTab
{
	NONE("None", -1, -1, -1),
	COMBAT("Combat Options",
		InterfaceID.Toplevel.STONE0, InterfaceID.ToplevelOsrsStretch.STONE0, InterfaceID.ToplevelPreEoc.STONE0),
	SKILLS("Skills",
		InterfaceID.Toplevel.STONE1, InterfaceID.ToplevelOsrsStretch.STONE1, InterfaceID.ToplevelPreEoc.STONE1),
	QUESTS("Quest List",
		InterfaceID.Toplevel.STONE2, InterfaceID.ToplevelOsrsStretch.STONE2, InterfaceID.ToplevelPreEoc.STONE2),
	INVENTORY("Inventory",
		InterfaceID.Toplevel.STONE3, InterfaceID.ToplevelOsrsStretch.STONE3, InterfaceID.ToplevelPreEoc.STONE3),
	EQUIPMENT("Worn Equipment",
		InterfaceID.Toplevel.STONE4, InterfaceID.ToplevelOsrsStretch.STONE4, InterfaceID.ToplevelPreEoc.STONE4),
	PRAYER("Prayer",
		InterfaceID.Toplevel.STONE5, InterfaceID.ToplevelOsrsStretch.STONE5, InterfaceID.ToplevelPreEoc.STONE5),
	MAGIC("Spellbook",
		InterfaceID.Toplevel.STONE6, InterfaceID.ToplevelOsrsStretch.STONE6, InterfaceID.ToplevelPreEoc.STONE6);

	private final String name;
	private final int fixedWidgetId;
	private final int resizableClassicWidgetId;
	private final int resizableModernWidgetId;

	@Override
	public String toString()
	{
		return name;
	}
}
