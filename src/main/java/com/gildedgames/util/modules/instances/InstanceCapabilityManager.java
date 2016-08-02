package com.gildedgames.util.modules.instances;

import com.gildedgames.util.core.UtilModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class InstanceCapabilityManager
{

    public void init()
    {
        MinecraftForge.EVENT_BUS.register(this);

        CapabilityManager.INSTANCE.register(PlayerInstances.class, new PlayerInstances.Storage(), PlayerInstances.class);
    }

    @SubscribeEvent
    public void onEntityLoad(AttachCapabilitiesEvent.Entity event)
    {
        if (event.getEntity() instanceof EntityPlayer)
        {
            event.addCapability(UtilModule.getResource("PlayerInstances"), new PlayerInstancesProvider(new PlayerInstances((EntityPlayer) event.getEntity())));
        }
    }

}
