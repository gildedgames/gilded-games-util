package com.gildedgames.util.testutil;

import com.gildedgames.util.core.ICore;
import com.gildedgames.util.core.SidedObject;
import com.gildedgames.util.modules.instances.InstanceCore;
import com.gildedgames.util.modules.instances.InstanceHandler;
import com.gildedgames.util.testutil.commands.CreateDimension;
import com.gildedgames.util.testutil.commands.ReturnFromInstance;
import com.gildedgames.util.testutil.commands.SendNotification;
import com.gildedgames.util.testutil.instances.DefaultHandler;
import com.gildedgames.util.testutil.instances.DefaultInstance;
import com.gildedgames.util.testutil.instances.DefaultInstancesFactory;

import net.minecraft.world.WorldProviderSurface;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

public class TestCore implements ICore
{

	public static TestCore INST = new TestCore();

	private SidedObject<TestServices> sided = new SidedObject<TestServices>(new TestServices(), new TestServices());

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{

	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		DefaultInstancesFactory factory = new DefaultInstancesFactory(5, WorldProviderSurface.class);

		InstanceHandler<DefaultInstance> client = InstanceCore.INST.createClientInstanceHandler(factory);
		InstanceHandler<DefaultInstance> server = InstanceCore.INST.createServerInstanceHandler(factory);

		this.sided.client().setHandler(new DefaultHandler(client));
		this.sided.server().setHandler(new DefaultHandler(server));
	}

	public static TestServices locate()
	{
		return INST.sided.instance();
	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{

	}

	@Override
	public void serverAboutToStart(FMLServerAboutToStartEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void serverStopping(FMLServerStoppingEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void serverStopped(FMLServerStoppedEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CreateDimension());
		event.registerServerCommand(new ReturnFromInstance());
		event.registerServerCommand(new SendNotification());
	}

	@Override
	public void serverStarted(FMLServerStartedEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void flushData()
	{
		// TODO Auto-generated method stub

	}

}
