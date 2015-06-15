package com.gildedgames.util.core.gui.util.wrappers;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import com.gildedgames.util.core.ObjectFilter;
import com.gildedgames.util.ui.common.AbstractUI;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseInputPool;

public class MinecraftDefaultButton extends AbstractUI
{
	
	protected final static Minecraft mc = Minecraft.getMinecraft();
	
	protected String text;
	
	protected GuiButton button;

	public MinecraftDefaultButton(GuiButton button, boolean centered)
	{
		super(null, new Dim2D().setPos(new Pos2D(button.xPosition, button.yPosition)).setArea(button.getButtonWidth(), button.height).setCentering(centered));
		
		this.button = button;
	}
	
	public MinecraftDefaultButton(Dim2D dim, String text)
	{
		super(null, dim);
		
		this.text = text;
		
		this.button = new GuiButton(-1, (int)dim.getX(), (int)dim.getY(), (int)dim.getWidth(), (int)dim.getHeight(), text);
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
		
		this.button.xPosition = (int) this.getDimensions().getX();
		this.button.yPosition = (int) this.getDimensions().getY();
		
		this.button.width = (int) this.getDimensions().getWidth();
		this.button.height = (int) this.getDimensions().getHeight();
	}
	
	@Override
	public void onMouseInput(InputProvider input, MouseInputPool pool)
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