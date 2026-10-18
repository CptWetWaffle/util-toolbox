package com.CptWetWaffle.utiltoolbox;

import com.CptWetWaffle.utiltoolbox.tools.mousetabbinds.SideTab;
import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;

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

	@ConfigSection(
		name = "Low Prayer Flash",
		description = "Flash the screen when prayer is low",
		position = 1
	)
	String lowPrayerFlashSection = "lowPrayerFlash";

	@ConfigItem(
		keyName = "lowPrayerFlashEnabled",
		name = "Enable",
		description = "Flash the screen while prayer points are below the threshold",
		section = lowPrayerFlashSection,
		position = 0
	)
	default boolean lowPrayerFlashEnabled()
	{
		return false;
	}

	@Range(max = 99)
	@ConfigItem(
		keyName = "lowPrayerThreshold",
		name = "Threshold",
		description = "Flash while prayer points are below this value",
		section = lowPrayerFlashSection,
		position = 1
	)
	default int lowPrayerThreshold()
	{
		return 10;
	}

	@Alpha
	@ConfigItem(
		keyName = "lowPrayerFlashColor",
		name = "Flash colour",
		description = "Colour of the flash. The alpha sets how strong it is at its brightest.",
		section = lowPrayerFlashSection,
		position = 2
	)
	default Color lowPrayerFlashColor()
	{
		return new Color(144, 238, 144, 110);
	}
}
