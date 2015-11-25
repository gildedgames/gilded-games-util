package com.gildedgames.util.threadedlighting.world;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.profiler.Profiler;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.WorldSettings;

import com.gildedgames.util.threadedlighting.ThreadedLighting;

public class ThreadedWorld extends WorldClient implements Runnable
{

	private class UpdateSkylight
	{
		int x, y, z;

		UpdateSkylight(int x, int y, int z)
		{
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}

	public class RelightBlock
	{
		ThreadedChunk chunk;

		int x, y, z;

		RelightBlock(ThreadedChunk chunk, int x, int y, int z)
		{
			this.chunk = chunk;
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}

	private Thread thread;
	
	private boolean delayedStart = false;

	public ArrayList queue = new ArrayList();

	public ReentrantLock queueLock = new ReentrantLock();

	public ThreadedWorld(NetHandlerPlayClient par1NetClientHandler, WorldSettings par2WorldSettings, int par3, EnumDifficulty par4, Profiler par5Profiler)
	{
		super(par1NetClientHandler, par2WorldSettings, par3, par4, new DummyProfiler(par5Profiler));

		this.thread = new Thread(this);
		
		if (this.delayedStart)
		{
			this.thread.start();
		}

		((DummyProfiler) this.theProfiler).threadToIgnore = this.thread.getId();
	}

	@Override
	public void addWorldAccess(IWorldAccess par1IWorldAccess)
	{
		if (this.worldAccesses.isEmpty())
		{
			if (this.thread != null)
			{
				this.thread.start();
			}
			else
			{
				this.delayedStart = true;
			}
		}

		super.addWorldAccess(par1IWorldAccess);
	}

	@Override
	public void removeWorldAccess(IWorldAccess par1IWorldAccess)
	{
		super.removeWorldAccess(par1IWorldAccess);

		if (this.worldAccesses.isEmpty())
		{
			this.thread.stop();
		}
	}

	@Override
	public boolean updateLightByType(EnumSkyBlock type, int x, int y, int z)
	{
		if (type == EnumSkyBlock.Block)
		{
			return super.updateLightByType(type, x, y, z);
		}

		if (!this.doChunksNearChunkExist(x, y, z, 17))
		{
			return false;
		}

		this.queueLock.lock();
		this.queue.add(new UpdateSkylight(x, y, z));
		this.queueLock.unlock();

		return true;
	}

	@Override
	public void run()
	{
		while (true)
		{
			long start = System.currentTimeMillis();

			this.queueLock.lock();

			if (this.queue.size() > 0)
			{
				Object[] tempQueue = new Object[this.queue.size()];
				this.queue.toArray(tempQueue);
				this.queue.clear();

				this.queueLock.unlock();

				for (Object object : tempQueue)
				{
					if (object instanceof UpdateSkylight)
					{
						UpdateSkylight data = (UpdateSkylight) object;
						super.updateLightByType(EnumSkyBlock.Sky, data.x, data.y, data.z);
					}
					else if (object instanceof RelightBlock)
					{
						RelightBlock data = (RelightBlock) object;
						data.chunk.superRelightBlock(data.x, data.y, data.z);
					}
				}
			}
			else
			{
				this.queueLock.unlock();
			}

			long time = System.currentTimeMillis() - start;

			if (time > 100)
			{
				ThreadedLighting.print("prevented lag lasting " + time + " milliseconds.");
			}

			if (time < 10)
			{
				try
				{
					Thread.currentThread().sleep(10 - time);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

}
