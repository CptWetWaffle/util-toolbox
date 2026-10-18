package com.CptWetWaffle.utiltoolbox.tools.mousetabbinds;

import com.CptWetWaffle.utiltoolbox.UtilToolboxConfig;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.gameval.InterfaceID;

/**
 * Side panel tab stones, with the widget id of each in the fixed, resizable classic and resizable modern layouts.
 */
@Getter
@RequiredArgsConstructor
enum TabStone
{
	COMBAT(UtilToolboxConfig::mouseTabCombat,
		InterfaceID.Toplevel.STONE0, InterfaceID.ToplevelOsrsStretch.STONE0, InterfaceID.ToplevelPreEoc.STONE0),
	SKILLS(UtilToolboxConfig::mouseTabSkills,
		InterfaceID.Toplevel.STONE1, InterfaceID.ToplevelOsrsStretch.STONE1, InterfaceID.ToplevelPreEoc.STONE1),
	QUESTS(UtilToolboxConfig::mouseTabQuests,
		InterfaceID.Toplevel.STONE2, InterfaceID.ToplevelOsrsStretch.STONE2, InterfaceID.ToplevelPreEoc.STONE2),
	INVENTORY(UtilToolboxConfig::mouseTabInventory,
		InterfaceID.Toplevel.STONE3, InterfaceID.ToplevelOsrsStretch.STONE3, InterfaceID.ToplevelPreEoc.STONE3),
	EQUIPMENT(UtilToolboxConfig::mouseTabEquipment,
		InterfaceID.Toplevel.STONE4, InterfaceID.ToplevelOsrsStretch.STONE4, InterfaceID.ToplevelPreEoc.STONE4),
	PRAYER(UtilToolboxConfig::mouseTabPrayer,
		InterfaceID.Toplevel.STONE5, InterfaceID.ToplevelOsrsStretch.STONE5, InterfaceID.ToplevelPreEoc.STONE5),
	MAGIC(UtilToolboxConfig::mouseTabMagic,
		InterfaceID.Toplevel.STONE6, InterfaceID.ToplevelOsrsStretch.STONE6, InterfaceID.ToplevelPreEoc.STONE6);

	private final Function<UtilToolboxConfig, MouseButton> binding;
	private final int fixedWidgetId;
	private final int resizableClassicWidgetId;
	private final int resizableModernWidgetId;
}
