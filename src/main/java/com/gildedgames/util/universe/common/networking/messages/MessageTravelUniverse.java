package com.gildedgames.util.universe.common.networking.messages;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import com.gildedgames.util.core.CustomPacket;
import com.gildedgames.util.universe.common.UniverseAPI;
import com.gildedgames.util.universe.common.util.IUniverse;

public class MessageTravelUniverse extends CustomPacket<MessageTravelUniverse>
{

	private String universeID;

	public MessageTravelUniverse()
	{

	}

	public MessageTravelUniverse(IUniverse universe)
	{
		this.universeID = UniverseAPI.instance().getIDFrom(universe);
	}

	public MessageTravelUniverse(String universeID)
	{
		this.universeID = universeID;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.universeID = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.universeID);
	}

	@Override
	public void handleClientSide(MessageTravelUniverse message, EntityPlayer player)
	{

	}

	@Override
	public void handleServerSide(MessageTravelUniverse message, EntityPlayer player)
	{
		IUniverse universe = UniverseAPI.instance().getFromID(message.universeID);

		if (universe != null)
		{
			UniverseAPI.instance().travelTo(universe, player);
		}
	}

}
