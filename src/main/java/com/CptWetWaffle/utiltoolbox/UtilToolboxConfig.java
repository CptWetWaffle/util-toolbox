package com.CptWetWaffle.utiltoolbox;

import com.CptWetWaffle.utiltoolbox.tools.mousetabbinds.MouseButton;
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
		keyName = "mouseTabCombat",
		name = "Combat Options",
		description = "Mouse button that opens the Combat Options tab",
		section = mouseTabBindsSection,
		position = 1
	)
	default MouseButton mouseTabCombat()
	{
		return MouseButton.NONE;
	}

	@ConfigItem(
		keyName = "mouseTabSkills",
		name = "Skills",
		description = "Mouse button that opens the Skills tab",
		section = mouseTabBindsSection,
		position = 2
	)
	default MouseButton mouseTabSkills()
	{
		return MouseButton.NONE;
	}

	@ConfigItem(
		keyName = "mouseTabQuests",
		name = "Quest List",
		description = "Mouse button that opens the Quest List tab",
		section = mouseTabBindsSection,
		position = 3
	)
	default MouseButton mouseTabQuests()
	{
		return MouseButton.NONE;
	}

	@ConfigItem(
		keyName = "mouseTabInventory",
		name = "Inventory",
		description = "Mouse button that opens the Inventory tab",
		section = mouseTabBindsSection,
		position = 4
	)
	default MouseButton mouseTabInventory()
	{
		return MouseButton.NONE;
	}

	@ConfigItem(
		keyName = "mouseTabEquipment",
		name = "Worn Equipment",
		description = "Mouse button that opens the Worn Equipment tab",
		section = mouseTabBindsSection,
		position = 5
	)
	default MouseButton mouseTabEquipment()
	{
		return MouseButton.NONE;
	}

	@ConfigItem(
		keyName = "mouseTabPrayer",
		name = "Prayer",
		description = "Mouse button that opens the Prayer tab",
		section = mouseTabBindsSection,
		position = 6
	)
	default MouseButton mouseTabPrayer()
	{
		return MouseButton.NONE;
	}

	@ConfigItem(
		keyName = "mouseTabMagic",
		name = "Spellbook",
		description = "Mouse button that opens the Spellbook tab",
		section = mouseTabBindsSection,
		position = 7
	)
	default MouseButton mouseTabMagic()
	{
		return MouseButton.NONE;
	}
}
