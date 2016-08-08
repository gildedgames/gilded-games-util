package com.gildedgames.util.core.client;

import com.gildedgames.util.core.ServerProxy;
import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.core.gui.viewing.MinecraftGuiViewer;
import com.gildedgames.util.modules.tab.TabModule;
import com.gildedgames.util.modules.tab.client.TabClientEvents;
import com.gildedgames.util.modules.tab.client.social.TabChat;
import com.gildedgames.util.modules.tab.common.util.TabGroupHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.input.Keyboard;

public class ClientProxy extends ServerProxy
{

	private final Minecraft mc = Minecraft.getMinecraft();

	public static final KeyBinding keyBindHopUniverse = new KeyBinding(I18n.translateToLocal("keybindings.hopUniverse"), Keyboard.KEY_H, "key.categories.misc");

	@Override
	public EntityPlayer getPlayer()
	{
		return this.mc.thePlayer;
	}

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);

		UtilModule.registerEventHandler(MinecraftGuiViewer.instance().getTickInfo());

		TabClientEvents clientEvents = new TabClientEvents();
		UtilModule.registerEventHandler(clientEvents);

		TabGroupHandler socialTab = new TabGroupHandler();
		socialTab.registerServerTab(new TabChat());

		if (UtilModule.isClient())
		{
			socialTab.registerClientTab(new TabChat.Client());
		}

		TabModule.api().registerGroup(socialTab);
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		ClientRegistry.registerKeyBinding(keyBindHopUniverse);
	}

	@Override
	public void addScheduledTask(Runnable runnable)
	{
		Minecraft.getMinecraft().addScheduledTask(runnable);
	}

}
