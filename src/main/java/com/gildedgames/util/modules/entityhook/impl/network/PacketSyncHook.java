package com.gildedgames.util.modules.entityhook.impl.network;

import com.gildedgames.util.core.io.MessageHandlerClient;
import com.gildedgames.util.modules.entityhook.EntityHookModule;
import com.gildedgames.util.modules.entityhook.api.IEntityHookFactory;
import com.gildedgames.util.modules.entityhook.impl.hooks.EntityHook;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSyncHook implements IMessage
{
	private String providerId;

	private EntityHook hook;

	private int entityId;

	private boolean isOwner;

	public PacketSyncHook()
	{
	}

	public PacketSyncHook(String providerId, EntityHook hook, Entity entity)
	{
		this.providerId = providerId;
		this.hook = hook;
		this.entityId = entity.getEntityId();

		if (this.hook.getEntity() == entity)
		{
			this.isOwner = true;
		}
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entityId = buf.readInt();

		this.providerId = ByteBufUtils.readUTF8String(buf);

		IEntityHookFactory<EntityHook> factory = EntityHookModule.impl().getProviderFromId(this.providerId).getFactory();
		this.hook = factory.createHook();

		factory.readFull(buf, this.hook);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.entityId);

		ByteBufUtils.writeUTF8String(buf, this.providerId);

		IEntityHookFactory<EntityHook> factory = EntityHookModule.impl().getProviderFromId(this.providerId).getFactory();
		factory.writeFull(buf, this.hook);
	}

	public static class Handler extends MessageHandlerClient<PacketSyncHook, IMessage>
	{
		@Override
		public IMessage onMessage(PacketSyncHook message, EntityPlayer player)
		{
			Entity entity = player.worldObj.getEntityByID(message.entityId);
			entity.registerExtendedProperties(message.providerId, message.hook);

			return null;
		}
	}
}
