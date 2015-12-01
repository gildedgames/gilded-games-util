package com.gildedgames.util.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import com.gildedgames.util.core.io.MCSyncableDispatcher;
import com.gildedgames.util.core.nbt.NBTFile;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.util.IOManagerDefault;
import com.gildedgames.util.menu.client.MenuClientEvents.MenuConfig;
import com.gildedgames.util.ui.data.AssetLocation;
import com.gildedgames.util.world.common.WorldHookPool;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

public class UtilServices
{

	private IOManager io;

	private static final String MANAGER_NAME = "GildedGamesUtil";

	public UtilServices()
	{

	}

	private void startIOManager()
	{
		this.io = new IOManagerDefault(MANAGER_NAME);

		IORegistry registry = this.io.getRegistry();

		registry.registerClass(NBTFile.class, 0);
		registry.registerClass(WorldHookPool.class, 1);
		registry.registerClass(MenuConfig.class, 2);
		registry.registerClass(MCSyncableDispatcher.class, 3);
	}

	public IOManager getIOManager()
	{
		if (this.io == null)
		{
			this.startIOManager();
		}

		return this.io;
	}

	public IORegistry getIORegistry()
	{
		return this.getIOManager().getRegistry();
	}

	public IResource getResourceFrom(AssetLocation asset)
	{
		ResourceLocation resource = new ResourceLocation(asset.getDomain(), asset.getPath());

		try
		{
			return Minecraft.getMinecraft().getResourceManager().getResource(resource);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings("resource")
	public InputStream getStreamFromAsset(AssetLocation asset) throws IOException
	{
		if (asset.getDomain().equals("minecraft"))
		{
			return this.getResourceFrom(asset).getInputStream();
		}

		File source = null;

		String path = "assets/" + asset.getDomain() + "/" + asset.getPath();

		for (ModContainer container : Loader.instance().getActiveModList())
		{
			if (container.getModId().equals(asset.getDomain()))
			{
				source = container.getSource();
			}
		}

		if (source != null)
		{
			if (source.isFile())
			{
				ZipFile zipfile = new ZipFile(source);
				ZipEntry zipentry = zipfile.getEntry(path);

				return zipfile.getInputStream(zipentry);
			}

			return new FileInputStream(new File(source, path));
		}

		return null;
	}

	public BufferedImage getBufferedImage(AssetLocation asset)
	{
		try
		{
			return ImageIO.read(UtilCore.locate().getStreamFromAsset(asset));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}

}
