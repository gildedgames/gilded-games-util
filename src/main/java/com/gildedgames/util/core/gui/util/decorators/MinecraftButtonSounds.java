package com.gildedgames.util.core.gui.util.decorators;

import com.gildedgames.util.modules.ui.common.Gui;
import com.gildedgames.util.modules.ui.common.GuiDecorator;
import com.gildedgames.util.modules.ui.input.ButtonState;
import com.gildedgames.util.modules.ui.input.InputProvider;
import com.gildedgames.util.modules.ui.input.MouseButton;
import com.gildedgames.util.modules.ui.input.MouseInput;
import com.gildedgames.util.modules.ui.input.MouseInputPool;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.init.SoundEvents;

public class MinecraftButtonSounds extends GuiDecorator<Gui>
{

	protected Minecraft mc = Minecraft.getMinecraft();

	protected MouseInput event;

	public MinecraftButtonSounds(Gui view)
	{
		this(view, new MouseInput(MouseButton.LEFT, ButtonState.PRESS));
	}

	public MinecraftButtonSounds(Gui decoratedView, MouseInput event)
	{
		super(decoratedView);

		this.event = event;
	}

	@Override
	public void onMouseInput(MouseInputPool pool, InputProvider input)
	{
		Gui view = this.getDecoratedElement();

		if (input.isHovered(view.dim()) && pool.contains(this.event))
		{
			this.playPressSound(this.mc.getSoundHandler());
		}

		super.onMouseInput(pool, input);
	}

	public void playPressSound(SoundHandler soundHandlerIn)
	{
		soundHandlerIn.playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
	}

	@Override
	protected void preInitContent(InputProvider input)
	{

	}

	@Override
	protected void postInitContent(InputProvider input)
	{

	}

}
