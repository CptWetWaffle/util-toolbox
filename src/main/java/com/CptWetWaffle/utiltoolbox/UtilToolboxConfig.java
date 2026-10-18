package com.CptWetWaffle.utiltoolbox;

import com.CptWetWaffle.utiltoolbox.tools.mousetabbinds.SideTab;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup(UtilToolboxConfig.GROUP)
public interface UtilToolboxConfig extends Config
{
	String GROUP = "util-toolbox";

	@ConfigSection(
		name = "Mouse Tab Binds",
		description = "Open side panel tabs with mouse buttons",
		position = 0
	)
	String mouseTabBindsSection = "mouseTabBinds";

	@ConfigItem(
		keyName = "mouseTabBindsEnabled",
		name = "Enable",
		description = "Open the bound side panel tab when its mouse button is pressed in the game window.<br>"
			+ "A bound button is consumed, so binding Middle click disables middle-click camera rotation.",
		section = mouseTabBindsSection,
		position = 0
	)
	default boolean mouseTabBindsEnabled()
	{
		return false;
	}

	@ConfigItem(
		keyName = "mouseTabMiddle",
		name = "Middle click",
		description = "Tab opened by middle click",
		section = mouseTabBindsSection,
		position = 1
	)
	default SideTab mouseTabMiddle()
	{
		return SideTab.NONE;
	}

	@ConfigItem(
		keyName = "mouseTabButton4",
		name = "Mouse button 4",
		description = "Tab opened by mouse button 4",
		section = mouseTabBindsSection,
		position = 2
	)
	default SideTab mouseTabButton4()
	{
		return SideTab.NONE;
	}

	@ConfigItem(
		keyName = "mouseTabButton5",
		name = "Mouse button 5",
		description = "Tab opened by mouse button 5",
		section = mouseTabBindsSection,
		position = 3
	)
	default SideTab mouseTabButton5()
	{
		return SideTab.NONE;
	}
}
