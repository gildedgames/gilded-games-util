package com.gildedgames.util.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.gui.util.decorators.MinecraftGui;
import com.gildedgames.util.core.gui.viewing.MinecraftGuiViewer;
import com.gildedgames.util.core.gui.viewing.MinecraftGuiWrapper;
import com.gildedgames.util.core.nbt.NBTFactory;
import com.gildedgames.util.core.nbt.NBTFile;
import com.gildedgames.util.io_manager.IOCore;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.common.GuiViewer;
import com.gildedgames.util.ui.util.factory.Factory;
import com.google.common.collect.ImmutableList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;

public class UiServices
{

	private final Side side;

	private File saveLocation;

	private String currentUniqueSaveName;

	private GuiFrame currentFrame;

	private List<GuiFrame> openingFrames = new ArrayList<>();

	private List<GuiFrame> closingFrames = new ArrayList<>();

	private Map<String, Overlay> overlays = new LinkedHashMap<>();

	private Map<String, RegisteredOverlay> registeredOverlays = new LinkedHashMap<>();

	public enum RenderOrder
	{
		PRE, POST
	}

	public static class RegisteredOverlay extends Overlay
	{

		private Factory<GuiFrame> factory;

		public RegisteredOverlay(Factory<GuiFrame> factory, GuiViewer viewer, RenderOrder renderOrder)
		{
			super(null, viewer, renderOrder);

			this.factory = factory;
		}

		public Factory<GuiFrame> getFactory()
		{
			return this.factory;
		}

		public GuiFrame createFrame()
		{
			this.frame = this.factory.create();

			return this.frame;
		}

	}

	public static class Overlay
	{

		protected GuiFrame frame;

		protected GuiViewer viewer;

		protected RenderOrder renderOrder;

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

	public ImmutableList<RegisteredOverlay> registeredOverlays()
	{
		return ImmutableList.copyOf(this.registeredOverlays.values());
	}

	public ImmutableList<Overlay> overlays()
	{
		return ImmutableList.copyOf(this.overlays.values());
	}

	public void createRegisteredOverlays()
	{
		for (Map.Entry<String, RegisteredOverlay> entry : this.registeredOverlays.entrySet())
		{
			String uniqueSaveName = entry.getKey();
			RegisteredOverlay overlay = entry.getValue();

			Factory<GuiFrame> factory = overlay.getFactory();
			GuiViewer viewer = overlay.getViewer();
			RenderOrder renderOrder = overlay.getRenderOrder();
			//TODO: renderOrder isn't used

			UiCore.locate().overlay(uniqueSaveName, factory.create(), viewer);
		}
	}

	public void destroyRegisteredOverlays()
	{
		for (Map.Entry<String, RegisteredOverlay> entry : this.registeredOverlays.entrySet())
		{
			String uniqueSaveName = entry.getKey();

			UiCore.locate().removeOverlay(uniqueSaveName);
		}
	}

	public Overlay getOverlay(String uniqueSaveName)
	{
		return this.overlays.get(uniqueSaveName);
	}

	public void registerOverlay(String uniqueSaveName, Factory<GuiFrame> factory, GuiViewer viewer)
	{
		this.registerOverlay(uniqueSaveName, factory, viewer, RenderOrder.POST);
	}

	public void registerOverlay(String uniqueSaveName, Factory<GuiFrame> factory, GuiViewer viewer, RenderOrder renderOrder)
	{
		this.registeredOverlays.put(uniqueSaveName, new RegisteredOverlay(factory, viewer, renderOrder));
	}

	public void overlay(String uniqueSaveName, GuiFrame frame, GuiViewer viewer)
	{
		this.overlay(uniqueSaveName, frame, viewer, RenderOrder.POST);
	}

	public void overlay(String uniqueSaveName, GuiFrame frame, GuiViewer viewer, RenderOrder renderOrder)
	{
		frame.init(viewer.getInputProvider());

		this.overlays.put(uniqueSaveName, new Overlay(frame, viewer, renderOrder));
	}

	public void removeOverlay(String uniqueSaveName)
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

	/**
	 * Returns true if the given screen is a wrapper around the GuiFrame class
	 * given.
	 */
	public boolean containsFrame(GuiScreen screen, Class<? extends GuiFrame>... frames)
	{
		if (screen instanceof MinecraftGuiWrapper)
		{
			GuiFrame wrapped = ((MinecraftGuiWrapper) screen).getFrame();
			for (Class<? extends GuiFrame> frame : frames)
			{
				if (wrapped.getClass().equals(frame))
				{
					return true;
				}
				if (wrapped instanceof MinecraftGui)
				{
					MinecraftGui mcGui = (MinecraftGui) wrapped;
					if (mcGui.getDecoratedElement().getClass().equals(frame))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean hasFrame()
	{
		return this.getCurrentFrame() != null;
	}

	public GuiFrame getCurrentFrame()
	{
		return this.currentFrame;
	}

	public String getCurrentFrameName()
	{
		return this.currentUniqueSaveName;
	}

	public void open(String uniqueSaveName, GuiFrame frame)
	{
		this.open(uniqueSaveName, frame, MinecraftGuiViewer.instance());
	}

	public void open(String uniqueSaveName, GuiFrame frame, GuiViewer viewer)
	{
		//this.load(uniqueSaveName, frame, viewer);

		if (frame.ticksOpening() > 0)
		{
			//this.openingFrames.add(frame);

			//return;
		}

		viewer.open(frame);

		this.currentUniqueSaveName = uniqueSaveName;
		this.currentFrame = frame;
	}

	public void close()
	{
		this.close(MinecraftGuiViewer.instance());
	}

	public void close(String uniqueSaveName)
	{
		this.close(uniqueSaveName, MinecraftGuiViewer.instance());
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

		viewer.close(this.currentFrame);

		if (this.currentFrame != null)
		{
			this.currentFrame.onClose(viewer.getInputProvider());
		}

		this.currentFrame = null;
		this.currentUniqueSaveName = null;
	}

	private void refreshSaveLocation()
	{
		if (this.side.isClient())
		{
			this.saveLocation = new File(Minecraft.getMinecraft().mcDataDir, "mod-config\\ui\\");
		}
		else
		{
			this.saveLocation = new File(UtilCore.getWorldDirectory(), "mod-config\\ui\\");
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
