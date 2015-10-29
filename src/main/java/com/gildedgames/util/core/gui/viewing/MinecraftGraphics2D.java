package com.gildedgames.util.core.gui.viewing;

import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ZERO;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.gildedgames.util.ui.data.AssetLocation;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.graphics.Sprite;
import com.gildedgames.util.ui.graphics.Sprite.UV;
import com.gildedgames.util.ui.graphics.UVBehavior.UVDimPair;
import com.gildedgames.util.ui.util.rect.RectCollection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ResourceLocation;

public class MinecraftGraphics2D implements Graphics2D
{

	protected Minecraft minecraft;

	protected Gui gui = new Gui();

	protected FontRenderer fontRenderer;

	protected float zLevel;

	public MinecraftGraphics2D(Minecraft minecraft)
	{
		this.minecraft = minecraft;
		this.fontRenderer = this.minecraft.fontRendererObj;
	}

	private ResourceLocation convert(AssetLocation resource)
	{
		return new ResourceLocation(resource.getDomain(), resource.getPath());
	}

	private void draw(Rect dim, DrawingData data, DrawInner inner)
	{
		GlStateManager.pushMatrix();

		double currentX = dim.x();
		double currentY = dim.y();

		double x = currentX;//currentX + (currentX - prevX) * partialTicks;
		double y = currentY;//currentY + (currentY - prevY) * partialTicks;

		/** TO-DO: Figure out why the prevPos and currentPos are the same wtf?! :D **/

		GlStateManager.translate(x, y, 0);

		GlStateManager.scale(dim.scale(), dim.scale(), 0);

		GlStateManager.translate((dim.width() / 2) + dim.originX(), (dim.height() / 2) + dim.originY(), 0);

		GlStateManager.rotate(dim.degrees(), 0.0F, 0.0F, 1.0F);

		GlStateManager.translate(-(dim.width() / 2) - dim.originX(), -(dim.height() / 2) - dim.originY(), 0);

		GlStateManager.color(1, 1, 1, data.getAlpha());

		GlStateManager.enableBlend();

		GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ZERO);
		GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		inner.draw();

		GlStateManager.popMatrix();
	}

	@Override
	public void drawSprite(Sprite sprite, Rect dim, DrawingData data)
	{
		this.draw(dim, data, new DrawSprite(this, sprite, data, dim));
	}

	@Override
	public void drawText(String text, Rect dim, DrawingData data)
	{
		this.draw(dim, data, new DrawText(this.fontRenderer, text, dim, data));
	}

	@Override
	public void drawLine(int startX, int startY, int endX, int endY, DrawingData drawingData)
	{

	}

	@Override
	public void drawRectangle(Rect dim, DrawingData data)
	{
		this.draw(dim, data, new DrawRectangle(this, dim, data));
	}

	@Override
	public void drawGradientRectangle(Rect dim, DrawingData startColor, DrawingData endColor)
	{
		this.draw(dim, startColor, new DrawGradientRectangle(this, dim, startColor, endColor));
	}

	protected void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor)
	{
		float f = (startColor >> 24 & 255) / 255.0F;
		float f1 = (startColor >> 16 & 255) / 255.0F;
		float f2 = (startColor >> 8 & 255) / 255.0F;
		float f3 = (startColor & 255) / 255.0F;
		float f4 = (endColor >> 24 & 255) / 255.0F;
		float f5 = (endColor >> 16 & 255) / 255.0F;
		float f6 = (endColor >> 8 & 255) / 255.0F;
		float f7 = (endColor & 255) / 255.0F;
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		worldrenderer.startDrawingQuads();
		worldrenderer.setColorRGBA_F(f1, f2, f3, f);
		worldrenderer.addVertex(right, top, this.zLevel);
		worldrenderer.addVertex(left, top, this.zLevel);
		worldrenderer.setColorRGBA_F(f5, f6, f7, f4);
		worldrenderer.addVertex(left, bottom, this.zLevel);
		worldrenderer.addVertex(right, bottom, this.zLevel);
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	private void drawModalRectWithCustomSizedTexture(double x, double y, double u, double v, double width, double height, double textureWidth, double textureHeight)
	{
		double f4 = 1.0D / textureWidth;
		double f5 = 1.0D / textureHeight;
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		worldrenderer.startDrawingQuads();
		worldrenderer.addVertexWithUV(x, y + height, 0.0D, u * f4, (v + (float) height) * f5);
		worldrenderer.addVertexWithUV(x + width, y + height, 0.0D, (u + (float) width) * f4, (v + (float) height) * f5);
		worldrenderer.addVertexWithUV(x + width, y, 0.0D, (u + (float) width) * f4, v * f5);
		worldrenderer.addVertexWithUV(x, y, 0.0D, u * f4, v * f5);
		tessellator.draw();
	}

	private void drawRect(double left, double top, double right, double bottom, int color)
	{
		double j1;

		if (left < right)
		{
			j1 = left;
			left = right;
			right = j1;
		}

		if (top < bottom)
		{
			j1 = top;
			top = bottom;
			bottom = j1;
		}

		float f3 = (color >> 24 & 255) / 255.0F;
		float f = (color >> 16 & 255) / 255.0F;
		float f1 = (color >> 8 & 255) / 255.0F;
		float f2 = (color & 255) / 255.0F;
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(f, f1, f2, f3);
		worldrenderer.startDrawingQuads();
		worldrenderer.addVertex(left, bottom, 0.0D);
		worldrenderer.addVertex(right, bottom, 0.0D);
		worldrenderer.addVertex(right, top, 0.0D);
		worldrenderer.addVertex(left, top, 0.0D);
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	private static interface DrawInner
	{
		void draw();
	}

	private static class DrawSprite implements DrawInner
	{

		private final MinecraftGraphics2D graphics;

		private final Sprite sprite;

		private final Rect dim;

		private final DrawingData data;

		private final RectCollection collection;

		public DrawSprite(MinecraftGraphics2D graphics, Sprite sprite, DrawingData data, Rect dim)
		{
			this.graphics = graphics;
			this.sprite = sprite;
			this.dim = dim;
			this.data = data;
			this.collection = RectCollection.flush(this.dim);
		}

		@Override
		public void draw()
		{
			this.graphics.minecraft.renderEngine.bindTexture(this.graphics.convert(this.sprite.getAsset()));

			if (this.sprite.getBehavior().shouldRecalculateUVs(this.sprite, this.collection))
			{
				this.sprite.getBehavior().recalculateUVs(this.sprite, this.collection);
			}

			List<UVDimPair> uvDimPairs = this.sprite.getBehavior().getDrawnUVsFor(this.sprite, this.collection);

			for (UVDimPair uvDimPair : uvDimPairs)
			{
				UV uv = uvDimPair.getUV();
				Rect dim = uvDimPair.getRect();

				this.graphics.drawModalRectWithCustomSizedTexture(dim.x(), dim.y(), uv.minU(), uv.minV(), uv.width(), uv.height(), this.sprite.getAssetWidth(), this.sprite.getAssetHeight());
			}
		}

	}

	private static class DrawText implements DrawInner
	{

		private final FontRenderer fontRenderer;

		private final String text;

		private final Rect dim;

		private final DrawingData data;

		public DrawText(FontRenderer fontRenderer, String text, Rect dim, DrawingData data)
		{
			this.fontRenderer = fontRenderer;
			this.text = text;
			this.dim = dim;
			this.data = data;
		}

		@Override
		public void draw()
		{
			this.fontRenderer.drawStringWithShadow(this.text, 0, 0, this.data.getColor().getRGB());
		}

	}

	private static class DrawRectangle implements DrawInner
	{

		private final MinecraftGraphics2D graphics;

		private final Rect dim;

		private final DrawingData data;

		public DrawRectangle(MinecraftGraphics2D graphics, Rect dim, DrawingData data)
		{
			this.graphics = graphics;
			this.dim = dim;
			this.data = data;
		}

		@Override
		public void draw()
		{
			this.graphics.drawRect(0, 0, this.dim.width(), this.dim.height(), this.data.getColor().getRGB());
		}

	}

	private static class DrawGradientRectangle implements DrawInner
	{

		private final MinecraftGraphics2D graphics;

		private final Rect dim;

		private final DrawingData startColor, endColor;

		public DrawGradientRectangle(MinecraftGraphics2D graphics, Rect dim, DrawingData startColor, DrawingData endColor)
		{
			this.graphics = graphics;
			this.dim = dim;
			this.startColor = startColor;
			this.endColor = endColor;
		}

		@Override
		public void draw()
		{
			this.graphics.drawGradientRect(0, 0, this.dim.width(), this.dim.height(), this.startColor.getColor().getRGB(), this.endColor.getColor().getRGB());
		}

	}

}
