package com.gildedgames.util.core.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;

import com.gildedgames.util.ui.UIDecorator;
import com.gildedgames.util.ui.UIView;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseEvent;
import com.gildedgames.util.ui.input.MouseEventPool;

public class UIButtonSounds extends UIDecorator
{

	protected Minecraft mc = Minecraft.getMinecraft();
	
	protected MouseEvent event;
	
	public UIButtonSounds(UIView view)
	{
		this(view, new MouseEvent(MouseButton.LEFT, ButtonState.PRESSED));
	}
	
	public UIButtonSounds(UIView decoratedView, MouseEvent event)
	{
		super(decoratedView);
		
		this.event = event;
	}
	
	@Override
	public void onMouseEvent(InputProvider input, MouseEventPool pool)
	{
		UIView view = this.getDecoratedElement();
		
		if (input.isHovered(view.getDimensions()) && pool.contains(this.event))
		{
			this.playPressSound(this.mc.getSoundHandler());
		}
		
		super.onMouseEvent(input, pool);
	}
	
	public void playPressSound(SoundHandler soundHandlerIn)
    {
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }
	
}
