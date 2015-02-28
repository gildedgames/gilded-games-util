package com.gildedgames.util.core.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;

import com.gildedgames.util.ui.UIDecorator;
import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.listeners.ButtonState;
import com.gildedgames.util.ui.listeners.MouseButton;

public class UIButtonSounds extends UIDecorator
{

	protected Minecraft mc = Minecraft.getMinecraft();
	
	protected MouseButton button;
	
	protected ButtonState state;
	
	public UIButtonSounds(UIView view)
	{
		this(view, MouseButton.LEFT, ButtonState.PRESS);
	}
	
	public UIButtonSounds(UIView decoratedView, MouseButton button, ButtonState state)
	{
		super(decoratedView);
		
		this.button = button;
		this.state = state;
	}
	
	@Override
	public void onMouseState(InputProvider input, MouseButton button, ButtonState state)
	{
		UIView view = this.getDecoratedElement();
		
		if (input.isHovered(view.getFocusArea()) && state.equals(this.state) && button.equals(this.button))
		{
			this.playPressSound(this.mc.getSoundHandler());
		}
		
		super.onMouseState(input, button, state);
	}
	
	public void playPressSound(SoundHandler soundHandlerIn)
    {
        soundHandlerIn.playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation("gui.button.press"), 1.0F));
    }
	
}
