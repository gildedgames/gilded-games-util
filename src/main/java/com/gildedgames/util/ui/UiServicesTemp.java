package com.gildedgames.util.ui;

import java.io.File;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.nbt.NBTFactory;
import com.gildedgames.util.core.nbt.NBTFile;
import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.common.GuiViewer;

public class UiServicesTemp
{

	private final Side side;
	
	private final File saveLocation;
	
	private String currentUniqueSaveName;
	
	private GuiFrame currentFrame;

	public UiServicesTemp(Side side)
	{
		this.side = side;
		
		if (side.isClient())
		{
			this.saveLocation = new File(Minecraft.getMinecraft().mcDataDir, "mod-config\\ui\\");
		}
		else
		{
			this.saveLocation = new File(UtilCore.instance.getWorldDirectory(), "mod-config\\ui\\");
		}
	}
	
	public void open(String uniqueSaveName, GuiFrame frame, GuiViewer viewer)
	{
		//this.load(uniqueSaveName, frame, viewer);
		
		viewer.open(frame);
		
		this.currentUniqueSaveName = uniqueSaveName;
		this.currentFrame = frame;
	}
	
	public void close(GuiViewer viewer)
	{
		//this.save(this.currentUniqueSaveName, this.currentFrame, viewer);
		
		viewer.close();
		
		this.currentFrame = null;
	}
	
	private void save(String uniqueSaveName, GuiFrame frame, GuiViewer viewer)
	{
		File save = new File(this.saveLocation, uniqueSaveName + ".dat");
		
		try
		{
			IOCore.io().readFile(save, new NBTFile(save, frame, GuiFrame.class), new NBTFactory());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void load(String uniqueSaveName, GuiFrame frame, GuiViewer viewer)
	{
		File load = new File(this.saveLocation, uniqueSaveName + ".dat");
		
		try
		{
			IOCore.io().writeFile(load, new NBTFile(load, frame, GuiFrame.class), new NBTFactory());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
