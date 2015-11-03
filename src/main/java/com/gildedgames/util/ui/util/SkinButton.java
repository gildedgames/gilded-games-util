package com.gildedgames.util.ui.util;

import java.util.Map;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.core.gui.util.MinecraftAssetLocation;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.AssetLocation;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.graphics.Sprite;
import com.gildedgames.util.ui.graphics.Sprite.UV;
import com.gildedgames.util.ui.input.InputProvider;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

public class SkinButton extends GuiFrame
{
	private final EntityPlayer player;

	public SkinButton(EntityPlayer player, int x, int y)
	{
		super(Dim2D.build().area(8, 8).pos(x, y).flush());
		this.player = player;
	}

	public SkinButton(EntityPlayer player)
	{
		super(Dim2D.build().area(8, 8).flush());
		this.player = player;
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		Minecraft minecraft = Minecraft.getMinecraft();
		Map map = minecraft.getSkinManager().loadSkinFromCache(this.player.getGameProfile());
		ResourceLocation location;

		if (map.containsKey(Type.SKIN))
		{
			location = minecraft.getSkinManager().loadSkin((MinecraftProfileTexture) map.get(Type.SKIN), Type.SKIN);
		}
		else
		{
			UUID uuid = EntityPlayer.getUUID(this.player.getGameProfile());
			location = DefaultPlayerSkin.getDefaultSkin(uuid);
		}

		AssetLocation asset = new MinecraftAssetLocation(location);
		Sprite sprite = Sprite.createWithArea(asset, UV.build().min(8f, 8f).max(16, 16f).flush(), 64, 64);
		this.content().set("head", GuiFactory.texture(sprite));
	}

}
