package com.gildedgames.util.notifications;

import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.gildedgames.util.core.UtilModule;
import com.gildedgames.util.notifications.common.core.INotification;
import com.gildedgames.util.notifications.common.core.NotificationDispatcher;
import com.gildedgames.util.notifications.common.core.NotificationDispatcherClient;
import com.gildedgames.util.notifications.common.core.NotificationQueue;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;

public class NotificationServicesClient extends NotificationServices
{

	private NotificationQueue queue = new NotificationQueue();

	private NotificationDispatcher dispatcher;

	private static final ResourceLocation TEXTURE_NOTIFICATIONS = new ResourceLocation(UtilModule.MOD_ID, "textures/gui/notification/notifications.png");

	public NotificationServicesClient()
	{
		super(Side.CLIENT);
	}

	@Override
	public void queueNotificationForDisplay(INotification notification)
	{
		this.queue.addNotification(notification);
	}

	@Override
	public void onRenderOverlay()
	{
		this.queue.tick();
		INotification toDisplay = this.queue.activeNotification();
		if (toDisplay != null)
		{
			double relTime = 2 * this.queue.getNotiTime() / 3000.0D;

			Minecraft mc = Minecraft.getMinecraft();

			if (relTime > 1.0D)
			{
				relTime = 2.0D - relTime;
			}

			relTime *= 4.0D;
			relTime = 1.0D - relTime;

			if (relTime < 0.0D)
			{
				relTime = 0.0D;
			}

			relTime *= relTime;
			relTime *= relTime;
			int y = -(int) (relTime * 36.0D);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			mc.renderEngine.bindTexture(TEXTURE_NOTIFICATIONS);
			GL11.glDisable(GL11.GL_LIGHTING);
			this.drawTexturedModalRect(0, y, 96, 202, 160, 32);

			int textOffsetX = 45;

			String receiver = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(toDisplay.getReceiver()).getGameProfile().getName();
			String sender = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(toDisplay.getSender()).getGameProfile().getName();

			String senderName = "To: " + receiver + (toDisplay.getSender() != null ? " From: " + sender : "");

			mc.fontRendererObj.drawString(toDisplay.getName(), textOffsetX, y + 7, -256);
			mc.fontRendererObj.drawString(senderName, textOffsetX, y + 18, -1);

			RenderHelper.enableGUIStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glEnable(GL11.GL_COLOR_MATERIAL);
			GL11.glEnable(GL11.GL_LIGHTING);
			//this.itemRender.renderItemAndEffectIntoGUI(this.theGame.fontRendererObj, this.theGame.renderEngine, this.theAchievement.theItemStack, i + 8, j + 8);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		}
	}

	private void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
	{
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
		worldrenderer.pos(x, y + height, 0).tex((textureX) * f, (textureY + height) * f1).endVertex();
		worldrenderer.pos(x + width, y + height, 0).tex((textureX + width) * f, (textureY + height) * f1).endVertex();
		worldrenderer.pos(x + width, y, 0).tex((textureX + width) * f, (textureY) * f1).endVertex();
		worldrenderer.pos(x, y, 0).tex((textureX) * f, (textureY) * f1).endVertex();
		tessellator.draw();
	}

	@Override
	public NotificationDispatcher getDispatcher()
	{
		if (this.dispatcher == null)
		{
			this.dispatcher = new NotificationDispatcherClient();
		}

		return this.dispatcher;
	}

	@Override
	public void onServerTick()
	{
	}

}
