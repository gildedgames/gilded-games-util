package com.gildedgames.playerhook.server;

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
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.playerhook.common.PlayerHookManager;
import com.gildedgames.playerhook.common.player.IPlayerHook;
import com.gildedgames.playerhook.common.player.PlayerProfile;

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
		for (PlayerHookManager manager : PlayerHookManager.getManagers())
		{
			IPlayerHook playerHook = manager.get(entityplayer);
			
			NBTTagCompound tag = new NBTTagCompound();
			
			playerHook.getProfile().writeToNBT(tag);
			
			playerHook.writeToNBT(tag);
			
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
		for (PlayerHookManager manager : PlayerHookManager.getManagers())
		{
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
					
					profile.readFromNBT(tag);

					playerHook.readFromNBT(tag);
					
					playerHook.setProfile(profile);
					
					manager.instance(Side.SERVER).addPlayer(playerHook);
					
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
        	EntityPlayerMP player = (EntityPlayerMP)configManager.playerEntityList.get(i);
        	
            this.writePlayerData(player.getUniqueID(), player);
        }
	}

}
