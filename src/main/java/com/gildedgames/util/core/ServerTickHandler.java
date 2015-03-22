package com.gildedgames.util.core;

import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ServerTickHandler
{
	private int tickCounter = 0;

	public void onTick(ServerTickEvent event)
	{
		if (event.side == Side.SERVER)
		{
			if (event.phase == Phase.START)
			{
				this.tickStart();
			}
			else
			{
				this.tickEnd();
			}
		}
	}

	private void tickEnd()
	{

	}

	private void tickStart()
	{
		this.tickCounter++;
	}

	public boolean minutesHasPassed(int minutes)
	{
		return this.tickCounter % (1200 * minutes) == 0;
	}
}
