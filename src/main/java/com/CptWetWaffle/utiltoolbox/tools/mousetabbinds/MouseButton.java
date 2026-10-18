package com.CptWetWaffle.utiltoolbox.tools.mousetabbinds;

import java.awt.event.MouseEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MouseButton
{
	NONE("None", MouseEvent.NOBUTTON),
	MIDDLE("Middle click", MouseEvent.BUTTON2),
	BUTTON_4("Mouse button 4", 4),
	BUTTON_5("Mouse button 5", 5);

	private final String name;
	private final int awtButton;

	@Override
	public String toString()
	{
		return name;
	}
}
