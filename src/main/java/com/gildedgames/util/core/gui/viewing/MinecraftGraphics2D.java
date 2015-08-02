package com.gildedgames.util.core.gui.viewing;

import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ZERO;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.gildedgames.util.core.UtilEvents;
import com.gildedgames.util.ui.data.AssetLocation;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.graphics.Sprite;
import com.gildedgames.util.ui.graphics.Sprite.UV;
import com.gildedgames.util.ui.graphics.UVBehavior.UVDimPair;

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

	private void draw(Dim2D dim, DrawingData data, DrawInner inner)
	{
		GlStateManager.pushMatrix();

		float currentX = dim.x();
		float currentY = dim.y();

		float partialTicks = UtilEvents.getPartialTicks();

		float x = currentX;//currentX + (currentX - prevX) * partialTicks;
		float y = currentY;//currentY + (currentY - prevY) * partialTicks;

		/** TO-DO: Figure out why the prevPos and currentPos are the same wtf?! :D **/

		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		GlStateManager.translate(x, y, 0);

		GlStateManager.scale(dim.scale(), dim.scale(), 0);

        GlStateManager.enableBlend();

		inner.draw();

		GlStateManager.popMatrix();
	}

	@Override
	public void drawSprite(Sprite sprite, Dim2D dim, DrawingData data)
	{
		this.draw(dim, data, new DrawSprite(this, sprite, data, dim));
	}

	@Override
	public void drawText(String text, Dim2D dim, DrawingData data)
	{
		this.draw(dim, data, new DrawText(this.fontRenderer, text, dim, data));
	}

	@Override
	public void drawLine(Pos2D startPos, Pos2D endPos, DrawingData drawingData)
	{

	}

	@Override
	public void drawRectangle(Dim2D dim, DrawingData data)
	{
		this.draw(dim, data, new DrawRectangle(this, dim, data));
	}

	@Override
	public void drawGradientRectangle(Dim2D dim, DrawingData startColor, DrawingData endColor)
	{
		this.draw(dim, startColor, new DrawGradientRectangle(this, dim, startColor, endColor));
	}

	protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor)
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

	private static interface DrawInner
	{
		void draw();
	}

	private static class DrawSprite implements DrawInner
	{

		private final MinecraftGraphics2D graphics;

		private final Sprite sprite;

		private final Dim2D dim;

		private final DrawingData data;

		public DrawSprite(MinecraftGraphics2D graphics, Sprite sprite, DrawingData data, Dim2D dim)
		{
			this.graphics = graphics;
			this.sprite = sprite;
			this.dim = dim;
			this.data = data;
		}

		@Override
		public void draw()
		{
			this.graphics.minecraft.renderEngine.bindTexture(this.graphics.convert(this.sprite.getAsset()));
			
			if (this.sprite.getBehavior().shouldRecalculateUVs(this.sprite, this.dim.toHolder()))
			{
				this.sprite.getBehavior().recalculateUVs(this.sprite, this.dim.toHolder());
			}
			
			List<UVDimPair> uvDimPairs = this.sprite.getBehavior().getDrawnUVsFor(this.sprite, this.dim.toHolder());
			
			for (UVDimPair uvDimPair : uvDimPairs)
			{
				UV uv = uvDimPair.getUV();
				Dim2D dim = uvDimPair.getDim();
				
				Gui.drawModalRectWithCustomSizedTexture(dim.x(), dim.y(), uv.minU(), uv.minV(), uv.width(), uv.height(), this.sprite.getAssetWidth(), this.sprite.getAssetHeight());
			}
		}

	}

	private static class DrawText implements DrawInner
	{

		private final FontRenderer fontRenderer;

		private final String text;

		private final Dim2D dim;

		private final DrawingData data;

		public DrawText(FontRenderer fontRenderer, String text, Dim2D dim, DrawingData data)
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

		private final Dim2D dim;

		private final DrawingData data;

		public DrawRectangle(MinecraftGraphics2D graphics, Dim2D dim, DrawingData data)
		{
			this.graphics = graphics;
			this.dim = dim;
			this.data = data;
		}

		@Override
		public void draw()
		{
			Gui.drawRect(0, 0, this.dim.width(), this.dim.height(), this.data.getColor().getRGB());
		}

	}

	private static class DrawGradientRectangle implements DrawInner
	{

		private final MinecraftGraphics2D graphics;

		private final Dim2D dim;

		private final DrawingData startColor, endColor;

		public DrawGradientRectangle(MinecraftGraphics2D graphics, Dim2D dim, DrawingData startColor, DrawingData endColor)
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
