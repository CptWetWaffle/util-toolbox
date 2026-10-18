package com.CptWetWaffle.utiltoolbox.tools;

/**
 * A self-contained utility hosted by the toolbox plugin.
 * <p>
 * The host calls {@link #startUp()} when {@link #isEnabled()} turns true and registers the tool on the event bus,
 * so a tool may declare its own {@code @Subscribe} methods. It unregisters the tool and calls {@link #shutDown()}
 * when {@link #isEnabled()} turns false or the plugin stops. Tools must release every listener and overlay they add.
 */
public interface Tool
{
	/**
	 * Whether the tool should currently run. Read from the tool's own config toggle.
	 */
	boolean isEnabled();

	void startUp();

	void shutDown();
}
