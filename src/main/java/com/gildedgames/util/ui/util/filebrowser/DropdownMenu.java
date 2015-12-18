package com.gildedgames.util.ui.util.filebrowser;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.core.gui.util.wrappers.MinecraftTextBackground;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.event.view.MouseEventGui;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.InputHelper;

public class DropdownMenu extends GuiFrame
{
	
	private DropdownEntry[] entries; 
	
	private List<GuiFrame> backgrounds = new ArrayList<GuiFrame>();
	
	public DropdownMenu(DropdownEntry... entries)
	{
		this.entries = entries;
	}

	@Override
	public void initContent(InputProvider input)
	{
		int count = 0;
		
		float lastHeight = 0, maxWidth = 0;
		
		this.backgrounds.clear();

		for (final DropdownEntry entry : this.entries)
		{
			GuiFrame visuals = entry.createVisuals();

			final MinecraftTextBackground background = new MinecraftTextBackground(visuals.dim().clone().mod().center(false).pos(0, lastHeight).addArea(5, 6).flush());
			
			visuals.dim().mod().center(false).pos(3, lastHeight + 3).flush();
			
			background.events().set("click", new MouseEventGui(new MouseInput(MouseButton.LEFT, ButtonState.PRESS))
			{
				
				private int originalColor = background.getBorderColor();
				
				@Override
				public void draw(Graphics2D graphics, InputProvider input)
				{
					if (input.isHovered(background))
					{
						background.setBorderColor(new Color(0, 100, 255, 100).getRGB());
						
						entry.onHover(InputHelper.cursorPos(input));
					}
					else
					{
						background.setBorderColor(this.originalColor);
					}
					
					super.draw(graphics, input);
				}

				@Override
				protected void onTrue(InputProvider input, MouseInputPool pool)
				{
					entry.onOpen();
				}

				@Override
				protected void onFalse(InputProvider input, MouseInputPool pool)
				{
					
				}

				@Override
				public void initEvent()
				{
					
				}
				
			});
			
			this.content().set("entry" + count, background);
			this.content().set("entryVisuals" + count, visuals);
			
			lastHeight += background.dim().height();
			
			maxWidth = Math.max(maxWidth, visuals.dim().width() + 5);
			
			this.backgrounds.add(background);
			
			count++;
		}
		
		for (GuiFrame back : this.backgrounds)
		{
			back.dim().mod().width(maxWidth).flush();
		}
	}
	
}
