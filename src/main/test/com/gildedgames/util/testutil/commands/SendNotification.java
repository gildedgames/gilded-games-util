package com.gildedgames.util.testutil.commands;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.modules.notifications.NotificationCore;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;

public class SendNotification implements ICommand
{
	private List<String> aliases;

	public SendNotification()
	{
		this.aliases = new ArrayList<String>();
		this.aliases.add("notifi");
	}

	@Override
	public String getCommandName()
	{
		return "notifi";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "notifi <text> <text>";
	}

	@Override
	public List<String> getCommandAliases()
	{
		return this.aliases;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring)
	{
		if (astring.length == 0)
		{
			return;
		}
		if (icommandsender instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP) icommandsender;
			NotificationCore.sendMessage(astring[0], astring[1], player.getGameProfile().getId(), player.getGameProfile().getId());
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
	{
		return true;
	}

	@Override
	public boolean isUsernameIndex(String[] astring, int i)
	{
		return false;
	}

	@Override
	public int compareTo(Object o)
	{
		return 0;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
	{
		return null;
	}

}
