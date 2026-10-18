package com.CptWetWaffle.utiltoolbox;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class UtilToolboxPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(UtilToolboxPlugin.class);
		RuneLite.main(args);
	}
}
