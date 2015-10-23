package com.gildedgames.util.group.client;

import org.apache.commons.lang3.StringUtils;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.gui.util.wrappers.MinecraftButton;
import com.gildedgames.util.group.GroupCore;
import com.gildedgames.util.group.common.permissions.GroupPermsDefault;
import com.gildedgames.util.group.common.permissions.GroupPermsDefault.PermissionType;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.input.GuiInput;
import com.gildedgames.util.ui.util.input.StringInput;

import net.minecraft.client.Minecraft;

public class GuiCreateGroup extends GuiFrame
{
	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);
		final GuiInput<String> nameInput = new GuiInput<String>(new StringInput(), Dim2D.build().pos(100, 100).area(100, 30).flush(), UtilCore.translate("gui.insertname"));
		this.content().set("input", nameInput);

		final PermissionButton permission = new PermissionButton(Dim2D.build().pos(100, 130).area(100, 20).flush());
		this.content().set("permission", permission);

		this.content().set("create", new MinecraftButton(Dim2D.build().pos(100, 160).area(100, 20).flush(), UtilCore.translate("gui.create"))
		{
			@Override
			public void onMouseInput(MouseInputPool pool, InputProvider input)
			{
				super.onMouseInput(pool, input);
				if (input.isHovered(this) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESSED) && !StringUtils.isEmpty(nameInput.getData()))
				{
					GroupCore.locate().getDefaultPool().create(nameInput.getData(), Minecraft.getMinecraft().thePlayer, new GroupPermsDefault(GroupCore.locate().getPlayers().get(Minecraft.getMinecraft().thePlayer), permission.type));
					Minecraft.getMinecraft().displayGuiScreen(null);
				}
			}
		});
	}

	private static class PermissionButton extends MinecraftButton
	{
		PermissionType type = PermissionType.OPEN;

		@Override
		public void onMouseInput(MouseInputPool pool, InputProvider input)
		{
			super.onMouseInput(pool, input);
			if (input.isHovered(this) && pool.has(MouseButton.LEFT) && pool.has(ButtonState.PRESSED))
			{
				this.type = PermissionType.values()[(this.type.ordinal() + 1) % PermissionType.values().length];
				this.text = UtilCore.translate("gui.permissiontype") + " " + this.type.getName();
			}
		}

		public PermissionButton(Rect dim)
		{
			super(dim, UtilCore.translate("gui.permissiontype") + " " + PermissionType.OPEN.getName());
		}

	}
}
