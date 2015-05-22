package com.gildedgames.util.testutil.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;

import com.gildedgames.util.testutil.TestCore;
import com.gildedgames.util.testutil.instances.DefaultHandler;

public class ReturnFromInstance implements ICommand
{
	private List<String> aliases;

	public ReturnFromInstance()
	{
		this.aliases = new ArrayList<String>();
		this.aliases.add("ggreturn");
	}

	@Override
	public String getCommandName()
	{
		return "ggreturn";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "ggreturn";
	}

	@Override
	public List<String> getCommandAliases()
	{
		return this.aliases;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring)
	{
		if (icommandsender instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP) icommandsender;
			DefaultHandler handler = TestCore.locate().getHandler();
			handler.teleportBack(player);
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
