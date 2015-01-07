package com.gildedgames.util.playerhook.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraftforge.event.entity.player.PlayerEvent;

import com.gildedgames.util.playerhook.PlayerHookCore;
import com.gildedgames.util.playerhook.common.IPlayerHookPool;
import com.gildedgames.util.playerhook.common.player.IPlayerHook;
import com.gildedgames.util.playerhook.common.player.PlayerProfile;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerHookSaveHandler
{

	public File playerDirectory;

	public PlayerHookSaveHandler()
	{

	}

	@SubscribeEvent
	public void onSavePlayerFile(PlayerEvent.SaveToFile event)
	{
		this.playerDirectory = event.playerDirectory;

		this.writePlayerData(UUID.fromString(event.playerUUID), event.entityPlayer);
	}

	@SubscribeEvent
	public void onLoadPlayerFile(PlayerEvent.LoadFromFile event)
	{
		this.playerDirectory = event.playerDirectory;

		this.readPlayerData(UUID.fromString(event.playerUUID), event.entityPlayer);
	}

	public void writePlayerData(UUID uuid, EntityPlayer entityplayer)
	{
		for (IPlayerHookPool manager : PlayerHookCore.locate().getPools())
		{
			if (!manager.shouldSave())
			{
				continue;
			}

			IPlayerHook playerHook = manager.get(entityplayer);

			NBTTagCompound tag = new NBTTagCompound();

			playerHook.getProfile().write(tag);

			playerHook.write(tag);

			File prefix = new File(this.playerDirectory, manager.getName());

			prefix.mkdirs();

			File tempPlayerFile = new File(prefix, uuid + ".dat.tmp");
			File playerFile = new File(prefix, uuid + ".dat");

			try
			{
				CompressedStreamTools.writeCompressed(tag, new FileOutputStream(tempPlayerFile));

				if (playerFile.exists())
				{
					playerFile.delete();
				}

				tempPlayerFile.renameTo(playerFile);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public NBTTagCompound readPlayerData(UUID uuid, EntityPlayer entityplayer)
	{
		for (IPlayerHookPool manager : PlayerHookCore.locate().getPools())
		{
			if (!manager.shouldSave())
			{
				continue;
			}

			File prefix = new File(this.playerDirectory, manager.getName());

			prefix.mkdirs();

			File playerFile = new File(prefix, uuid + ".dat");

			if (playerFile.exists() && playerFile.isFile())
			{
				try
				{
					FileInputStream inputStream = new FileInputStream(playerFile);

					NBTTagCompound tag = CompressedStreamTools.readCompressed(inputStream);

					IPlayerHook playerHook = (IPlayerHook) manager.getPlayerHookType().newInstance();

					PlayerProfile profile = new PlayerProfile();

					profile.read(tag);

					profile.setEntity(entityplayer);

					playerHook.setProfile(profile);

					playerHook.read(tag);

					manager.add(playerHook);

					return tag;
				}
				catch (InstantiationException e)
				{
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		return new NBTTagCompound();
	}

	public void flushData()
	{
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		ServerConfigurationManager configManager = server.getConfigurationManager();

		for (int i = 0; i < configManager.playerEntityList.size(); ++i)
		{
			EntityPlayerMP player = (EntityPlayerMP) configManager.playerEntityList.get(i);

			this.writePlayerData(player.getUniqueID(), player);
		}
	}

}
