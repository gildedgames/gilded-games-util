package com.gildedgames.util.core.gui.util.decorators;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;

import com.gildedgames.util.ui.common.UIDecorator;
import com.gildedgames.util.ui.common.UIView;
import com.gildedgames.util.ui.data.UIContainer;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.input.MouseInputPool;

public class MinecraftButtonSounds extends UIDecorator<UIView>
{

	protected Minecraft mc = Minecraft.getMinecraft();
	
	protected MouseInput event;
	
	public MinecraftButtonSounds(UIView view)
	{
		this(view, new MouseInput(MouseButton.LEFT, ButtonState.PRESSED));
	}
	
	public MinecraftButtonSounds(UIView decoratedView, MouseInput event)
	{
		super(decoratedView);
		
		this.event = event;
	}
	
	@Override
	public void onMouseInput(InputProvider input, MouseInputPool pool)
	{
		UIView view = this.getDecoratedElement();
		
		if (input.isHovered(view.getDim()) && pool.contains(this.event))
		{
			this.playPressSound(this.mc.getSoundHandler());
		}
		
		super.onMouseInput(input, pool);
	}
	
	public void playPressSound(SoundHandler soundHandlerIn)
    {
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }

	@Override
	public UIContainer assembleAllContent()
	{
		return this.getDecoratedElement().seekContent();
	}
	
}
