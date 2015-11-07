package com.gildedgames.util.ui.util;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.ui.UiCore;
import com.gildedgames.util.ui.common.GuiFrame;

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
							UtilCore.proxy.addScheduledTask(new Runnable()
							{
								@Override
								public void run()
								{
									GuiPolling.this.onCondition();
								}
							});
							return;
						}
						i++;
					}
				}
				catch (InterruptedException e)
				{
				}
				UiCore.locate().close();
			}
		});
		t.start();
	}

	protected abstract boolean condition();

	protected abstract void onCondition();
}
