package com.gildedgames.util.core.gui.util.wrappers;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseInputPool;

public class MinecraftButton extends GuiFrame
{
	
	protected final static Minecraft mc = Minecraft.getMinecraft();
	
	protected String text;
	
	protected GuiButton button;

	public MinecraftButton(GuiButton button, boolean centered)
	{
		super(Dim2D.build().pos(new Pos2D(button.xPosition, button.yPosition)).area(button.getButtonWidth(), button.height).center(centered).flush());
		
		this.button = button;
	}
	
	public MinecraftButton(Dim2D dim, String text)
	{
		super(dim);
		
		this.text = text;
		
		this.button = new GuiButton(-1, dim.x(), dim.y(), dim.width(), dim.height(), text);
	}
	
	@Override
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		
		this.button.enabled = enabled;
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);
	
		this.button.visible = visible;
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
		
		this.button.drawButton(mc, input.getMouseX(), input.getMouseY());
		
		this.button.xPosition = this.getDim().x();
		this.button.yPosition = this.getDim().y();
		
		this.button.width = this.getDim().width();
		this.button.height = this.getDim().height();
	}
	
	@Override
	public void onMouseInput(MouseInputPool pool, InputProvider input)
	{
		if (pool.contains(ButtonState.PRESSED))
		{
			this.button.mousePressed(mc, input.getMouseX(), input.getMouseY());
		}
		
		if (pool.contains(ButtonState.RELEASED))
		{
			this.button.mouseReleased(input.getMouseX(), input.getMouseY());
		}
	}
	
	@Override
	public boolean query(Object... input)
	{
		List<String> strings = ObjectFilter.getTypesFrom(input, String.class);
		
		for (String string : strings)
		{
			if (string != null && this.text.toLowerCase().contains(string.toLowerCase()))
			{
				return true;
			}
		}
		
		return false;
	}

}
