package com.gildedgames.util.modules.ui.util;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.modules.ui.UiModule;
import com.gildedgames.util.modules.ui.common.GuiFrame;

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
							UtilModule.proxy.addScheduledTask(new Runnable()
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
				UiModule.locate().close();
			}
		});
		t.start();
	}

	protected abstract boolean condition();

	protected abstract void onCondition();
}
