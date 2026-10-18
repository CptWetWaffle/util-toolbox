package com.CptWetWaffle.utiltoolbox;

import com.CptWetWaffle.utiltoolbox.tools.Tool;
import com.CptWetWaffle.utiltoolbox.tools.mousetabbinds.MouseTabBindsTool;
import com.google.inject.Provides;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Util Toolbox",
	description = "A toolbox of small utilities missing from RuneLite",
	tags = {"utility", "toolbox", "mouse", "keybind"}
)
public class UtilToolboxPlugin extends Plugin
{
	@Inject
	private EventBus eventBus;

	@Inject
	private MouseTabBindsTool mouseTabBindsTool;

	private List<Tool> tools;
	private final Set<Tool> running = new HashSet<>();

	@Override
	protected void startUp()
	{
		// To add a tool: inject it above, list it here, and give it a toggle in UtilToolboxConfig.
		tools = List.of(mouseTabBindsTool);
		syncTools();
	}

	@Override
	protected void shutDown()
	{
		for (Tool tool : running)
		{
			stop(tool);
		}
		running.clear();
		tools = null;
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (tools != null && UtilToolboxConfig.GROUP.equals(event.getGroup()))
		{
			syncTools();
		}
	}

	@Provides
	UtilToolboxConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(UtilToolboxConfig.class);
	}

	private void syncTools()
	{
		for (Tool tool : tools)
		{
			if (tool.isEnabled())
			{
				if (running.add(tool))
				{
					log.debug("Starting tool {}", tool.getClass().getSimpleName());
					tool.startUp();
					eventBus.register(tool);
				}
			}
			else if (running.remove(tool))
			{
				stop(tool);
			}
		}
	}

	private void stop(Tool tool)
	{
		log.debug("Stopping tool {}", tool.getClass().getSimpleName());
		eventBus.unregister(tool);
		tool.shutDown();
	}
}
