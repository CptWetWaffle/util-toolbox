package com.CptWetWaffle.utiltoolbox.tools.mousetabbinds;

import com.CptWetWaffle.utiltoolbox.UtilToolboxConfig;
import java.awt.event.MouseEvent;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Mouse buttons that can be bound, with the config item holding the tab each one opens.
 */
@Getter
@RequiredArgsConstructor
enum MouseButton
{
	MIDDLE(MouseEvent.BUTTON2, UtilToolboxConfig::mouseTabMiddle),
	BUTTON_4(4, UtilToolboxConfig::mouseTabButton4),
	BUTTON_5(5, UtilToolboxConfig::mouseTabButton5);

	private final int awtButton;
	private final Function<UtilToolboxConfig, SideTab> binding;
}
