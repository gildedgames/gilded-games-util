package com.gildedgames.util.ui;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.relauncher.Side;

import org.apache.commons.lang3.tuple.Pair;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.nbt.NBTFactory;
import com.gildedgames.util.core.nbt.NBTFile;
import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.common.GuiViewer;
import com.google.common.collect.ImmutableList;

public class UiServices
{

	private final Side side;
	
	private File saveLocation;
	
	private String currentUniqueSaveName;
	
	private GuiFrame currentFrame;
	
	private Map<String, Pair<GuiFrame, GuiViewer>> overlayedFrames = new LinkedHashMap<String, Pair<GuiFrame, GuiViewer>>();

	public UiServices(Side side)
	{
		this.side = side;
	}
	
	public ImmutableList<Pair<GuiFrame, GuiViewer>> overlays()
	{
		return ImmutableList.copyOf(this.overlayedFrames.values());
	}
	
	public void overlay(String uniqueSaveName, GuiFrame frame, GuiViewer viewer)
	{
		frame.init(viewer.getInputProvider());
		
		this.overlayedFrames.put(uniqueSaveName, Pair.of(frame, viewer));
	}
	
	public void remove(String uniqueSaveName)
	{
		this.overlayedFrames.remove(uniqueSaveName);
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
	
	private void refreshSaveLocation()
	{
		if (side.isClient())
		{
			this.saveLocation = new File(Minecraft.getMinecraft().mcDataDir, "mod-config\\ui\\");
		}
		else
		{
			this.saveLocation = new File(UtilCore.instance.getWorldDirectory(), "mod-config\\ui\\");
		}
	}
	
	private void save(String uniqueSaveName, GuiFrame frame, GuiViewer viewer)
	{
		this.refreshSaveLocation();
		
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
		this.refreshSaveLocation();
		
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
