package com.gildedgames.util.ui.util;

import com.gildedgames.util.ui.common.GuiFrame;

import net.minecraft.client.Minecraft;

public abstract class GuiPolling extends GuiFrame
{
	public GuiPolling()
	{
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				int i = 0;
				try
				{
					while (i < 50)
					{
						Thread.sleep(100L);
						if (GuiPolling.this.condition())
						{
							GuiPolling.this.onCondition();
							return;
						}
						i++;
					}
				}
				catch (InterruptedException e)
				{
				}
				Minecraft.getMinecraft().displayGuiScreen(null);
			}
		});
		t.start();
	}

	protected abstract boolean condition();

	protected abstract void onCondition();
}
