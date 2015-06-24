package com.gildedgames.util.core;

import com.gildedgames.util.core.gui.TestUI;
import com.gildedgames.util.core.gui.util.decorators.DarkenedBackground;
import com.gildedgames.util.core.gui.util.decorators.MinecraftButtonSounds;
import com.gildedgames.util.core.gui.util.wrappers.MinecraftDefaultButton;
import com.gildedgames.util.core.io.MCSyncableDispatcher;
import com.gildedgames.util.core.nbt.NBTFile;
import com.gildedgames.util.io_manager.overhead.IOManager;
import com.gildedgames.util.io_manager.overhead.IORegistry;
import com.gildedgames.util.io_manager.util.IOManagerDefault;
import com.gildedgames.util.menu.client.MenuClientEvents.MenuConfig;
import com.gildedgames.util.ui.common.UIFrame;
import com.gildedgames.util.ui.common.UIDecorator;
import com.gildedgames.util.ui.common.UIElement;
import com.gildedgames.util.ui.common.UIFrame;
import com.gildedgames.util.ui.common.UIView;
import com.gildedgames.util.ui.data.UIContainerMutable;
import com.gildedgames.util.ui.event.ElementEvent;
import com.gildedgames.util.ui.event.FrameEvent;
import com.gildedgames.util.ui.event.KeyboardEvent;
import com.gildedgames.util.ui.event.MouseEvent;
import com.gildedgames.util.ui.event.view.MouseEventView;
import com.gildedgames.util.ui.event.view.MouseEventViewFocus;
import com.gildedgames.util.ui.util.Button;
import com.gildedgames.util.ui.util.ButtonList;
import com.gildedgames.util.ui.util.RectangleElement;
import com.gildedgames.util.ui.util.ScrollBar;
import com.gildedgames.util.ui.util.ScrollBar.ButtonScrollEvent;
import com.gildedgames.util.ui.util.TextureElement;
import com.gildedgames.util.ui.util.decorators.RepeatableUI;
import com.gildedgames.util.ui.util.decorators.ScissorableUI;
import com.gildedgames.util.ui.util.decorators.ScrollableUI;
import com.gildedgames.util.world.common.WorldHookPool;

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
		registry.registerClass(ScrollBar.class, 5);
		registry.registerClass(Button.class, 6);
		registry.registerClass(ButtonList.class, 7);
		registry.registerClass(RectangleElement.class, 8);
		registry.registerClass(TextureElement.class, 9);
		registry.registerClass(RepeatableUI.class, 10);
		registry.registerClass(ScissorableUI.class, 11);
		registry.registerClass(ScrollableUI.class, 12);
		registry.registerClass(DarkenedBackground.class, 13);
		registry.registerClass(MinecraftButtonSounds.class, 14);
		registry.registerClass(MinecraftDefaultButton.class, 15);
		registry.registerClass(UIElement.class, 16);
		registry.registerClass(UIView.class, 17);
		registry.registerClass(UIDecorator.class, 18);
		registry.registerClass(UIFrame.class, 19);
		registry.registerClass(UIFrame.class, 20);
		registry.registerClass(TestUI.class, 21);
		registry.registerClass(UIContainerMutable.class, 22);
		registry.registerClass(ElementEvent.class, 23);
		registry.registerClass(FrameEvent.class, 24);
		registry.registerClass(KeyboardEvent.class, 25);
		registry.registerClass(MouseEvent.class, 26);
		registry.registerClass(MouseEventView.class, 27);
		registry.registerClass(MouseEventViewFocus.class, 28);
		registry.registerClass(ButtonScrollEvent.class, 29);
	}

	public IOManager getIOManager()
	{
		if (this.io == null)
		{
			this.startIOManager();
		}

		return this.io;
	}

}
