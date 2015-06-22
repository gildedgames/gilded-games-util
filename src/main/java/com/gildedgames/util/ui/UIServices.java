package com.gildedgames.util.ui;

import java.io.File;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.nbt.NBTFactory;
import com.gildedgames.util.core.nbt.NBTFile;
import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.ui.common.UIFrame;
import com.gildedgames.util.ui.common.UIViewer;

public class UIServices
{

	private final Side side;
	
	private final File saveLocation;
	
	private String currentUniqueSaveName;
	
	private UIFrame currentFrame;

	public UIServices(Side side)
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
	
	public void open(String uniqueSaveName, UIFrame frame, UIViewer viewer)
	{
		this.load(uniqueSaveName, frame, viewer);
		
		viewer.open(frame);
		
		this.currentUniqueSaveName = uniqueSaveName;
		this.currentFrame = frame;
	}
	
	public void close(UIViewer viewer)
	{
		this.save(this.currentUniqueSaveName, this.currentFrame, viewer);
		
		viewer.close();
		
		this.currentFrame = null;
	}
	
	private void save(String uniqueSaveName, UIFrame frame, UIViewer viewer)
	{
		File save = new File(this.saveLocation, uniqueSaveName + ".dat");
		
		try
		{
			IOCore.io().readFile(save, new NBTFile(save, frame, UIFrame.class), new NBTFactory());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void load(String uniqueSaveName, UIFrame frame, UIViewer viewer)
	{
		File load = new File(this.saveLocation, uniqueSaveName + ".dat");
		
		try
		{
			IOCore.io().writeFile(load, new NBTFile(load, frame, UIFrame.class), new NBTFactory());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
