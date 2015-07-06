package com.gildedgames.util.testutil.commands;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;

import com.gildedgames.util.testutil.TestCore;
import com.gildedgames.util.testutil.instances.DefaultHandler;
import com.gildedgames.util.testutil.instances.DefaultInstance;

public class CreateDimension implements ICommand
{
	private List<String> aliases;

	public CreateDimension()
	{
		this.aliases = new ArrayList<String>();
		this.aliases.add("ggcreate");
	}

	@Override
	public String getCommandName()
	{
		return "ggcreate";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "ggcreate <text>";
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
			DefaultHandler handler = TestCore.locate().getHandler();
			DefaultInstance inst = handler.get(astring[0]);
			handler.teleportToInst(player, inst);
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
