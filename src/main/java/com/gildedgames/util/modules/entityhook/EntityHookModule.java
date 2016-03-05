package com.gildedgames.util.modules.entityhook;

import com.gildedgames.util.core.Module;
import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.modules.entityhook.api.EntityHookServices;
import com.gildedgames.util.modules.entityhook.impl.EntityHookEventHandler;
import com.gildedgames.util.modules.entityhook.impl.EntityHookImpl;
import com.gildedgames.util.modules.entityhook.impl.network.PacketSyncHook;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class EntityHookModule extends Module
{
	public static final EntityHookModule INSTANCE = new EntityHookModule();

	private static final EntityHookImpl impl = new EntityHookImpl();

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new EntityHookEventHandler());

		UtilModule.NETWORK.registerMessage(PacketSyncHook.Handler.class, PacketSyncHook.class, Side.CLIENT);
	}

	public static EntityHookImpl impl()
	{
		return EntityHookModule.impl;
	}

	public static EntityHookServices api()
	{
		return EntityHookModule.impl;
	}
}
