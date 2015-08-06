package com.gildedgames.util.ui;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.gui.viewing.MinecraftGuiViewer;
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
	
	private Map<String, Overlay> overlays = new LinkedHashMap<String, Overlay>();
	
	public static enum RenderOrder
	{
		PRE, NORMAL, POST;
	}
	
	public static class Overlay
	{
		
		private GuiFrame frame;
		
		private GuiViewer viewer;
		
		private RenderOrder renderOrder;
		
		public Overlay(GuiFrame frame, GuiViewer viewer, RenderOrder renderOrder)
		{
			this.frame = frame;
			this.viewer = viewer;
			this.renderOrder = renderOrder;
		}
		
		public GuiFrame getFrame()
		{
			return this.frame;
		}
		
		public GuiViewer getViewer()
		{
			return this.viewer;
		}
		
		public RenderOrder getRenderOrder()
		{
			return this.renderOrder;
		}
		
		@Override
		public int hashCode()
		{
			return this.frame.hashCode() ^ this.viewer.hashCode() ^ this.renderOrder.hashCode();
		}

		@Override
		public boolean equals(Object o)
		{
			if (!(o instanceof Overlay))
			{
				return false;
			}
		   
			Overlay overlay = (Overlay) o;
		    
			return this.frame.equals(overlay.getFrame()) && this.viewer.equals(overlay.getViewer()) && this.renderOrder.equals(overlay.getRenderOrder());
		}
		
	}

	public UiServices(Side side)
	{
		this.side = side;
	}
	
	public ImmutableList<Overlay> overlays()
	{
		return ImmutableList.copyOf(this.overlays.values());
	}
	
	public void overlay(String uniqueSaveName, GuiFrame frame, GuiViewer viewer)
	{
		this.overlay(uniqueSaveName, frame, viewer, RenderOrder.NORMAL);
	}
	
	public void overlay(String uniqueSaveName, GuiFrame frame, GuiViewer viewer, RenderOrder renderOrder)
	{
		frame.init(viewer.getInputProvider());
		
		this.overlays.put(uniqueSaveName, new Overlay(frame, viewer, renderOrder));
	}
	
	public void remove(String uniqueSaveName)
	{
		this.overlays.remove(uniqueSaveName);
	}
	
	public boolean hasGuiScreen()
	{
		return Minecraft.getMinecraft().currentScreen != null;
	}
	
	public GuiScreen getGuiScreen()
	{
		return Minecraft.getMinecraft().currentScreen;
	}
	
	public GuiFrame currentFrame()
	{
		return this.currentFrame;
	}
	
	public String currentFrameName()
	{
		return this.currentUniqueSaveName;
	}
	
	public void open(String uniqueSaveName, GuiFrame frame)
	{
		this.open(uniqueSaveName, frame, MinecraftGuiViewer.instance());
	}
	
	public void close()
	{
		this.close(MinecraftGuiViewer.instance());
	}
	
	public void close(String uniqueSaveName)
	{
		this.close(uniqueSaveName, MinecraftGuiViewer.instance());
	}
	
	public void open(String uniqueSaveName, GuiFrame frame, GuiViewer viewer)
	{
		//this.load(uniqueSaveName, frame, viewer);

		viewer.open(frame);
		
		this.currentUniqueSaveName = uniqueSaveName;
		this.currentFrame = frame;
	}
	
	public void close(String uniqueSaveName, GuiViewer viewer)
	{
		if (this.currentUniqueSaveName != null && this.currentUniqueSaveName.equals(uniqueSaveName))
		{
			this.close(viewer);
		}
	}
	
	public void close(GuiViewer viewer)
	{
		//this.save(this.currentUniqueSaveName, this.currentFrame, viewer);
		
		viewer.close();
		
		this.currentFrame = null;
		this.currentUniqueSaveName = null;
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
