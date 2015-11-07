package com.gildedgames.util.core.gui.util.decorators;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;

import com.gildedgames.util.ui.common.GuiDecorator;
import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.input.MouseInputPool;

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
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
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
