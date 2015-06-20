package com.gildedgames.util.core.gui.viewing;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
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
import com.gildedgames.util.ui.graphics.Text;

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
		
		float currentX = dim.getX();
		float currentY = dim.getY();
		
		float prevX = dim.getPrevX();
		float prevY = dim.getPrevY();
		
		float partialTicks = UtilEvents.getPartialTicks();

		float x = currentX;//currentX + (currentX - prevX) * partialTicks;
		float y = currentY;//currentY + (currentY - prevY) * partialTicks;

		/** TO-DO: Figure out why the prevPos and currentPos are the same wtf?! :D **/
		
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		
		GlStateManager.translate(x, y, 0);

		GlStateManager.scale(dim.getScale(), dim.getScale(), dim.getScale());
		
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GlStateManager.color(data.getRed(), data.getGreen(), data.getBlue(), data.getAlpha());

		inner.draw();
		
		GlStateManager.disableBlend();
		
		worldrenderer.setTranslation(0, 0, 0);
		
		GlStateManager.popMatrix();
	}
	
	@Override
	public void drawSprite(Sprite sprite, Dim2D dim, DrawingData data)
	{
		this.draw(dim, data, new DrawSprite(this, sprite, dim));
	}

	@Override
	public void drawText(Text text, Dim2D dim, DrawingData data)
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
    	float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.startDrawingQuads();
        worldrenderer.setColorRGBA_F(f1, f2, f3, f);
        worldrenderer.addVertex((double)right, (double)top, (double)this.zLevel);
        worldrenderer.addVertex((double)left, (double)top, (double)this.zLevel);
        worldrenderer.setColorRGBA_F(f5, f6, f7, f4);
        worldrenderer.addVertex((double)left, (double)bottom, (double)this.zLevel);
        worldrenderer.addVertex((double)right, (double)bottom, (double)this.zLevel);
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
		
		public DrawSprite(MinecraftGraphics2D graphics, Sprite sprite, Dim2D dim)
		{
			this.graphics = graphics;
			this.sprite = sprite;
			this.dim = dim;
		}

		@Override
		public void draw()
		{
			this.graphics.minecraft.renderEngine.bindTexture(this.graphics.convert(this.sprite.getAsset()));
			this.graphics.gui.drawModalRectWithCustomSizedTexture(0, 0, (int) this.sprite.getMinU(), (int) this.sprite.getMinV(), (int) (this.sprite.getMaxU() - this.sprite.getMinU()), (int) (this.sprite.getMaxV() - this.sprite.getMinV()), (int) this.sprite.getTextureWidth(), (int) this.sprite.getTextureHeight());
		}
		
	}
	
	private static class DrawText implements DrawInner
	{
		
		private final FontRenderer fontRenderer;
		
		private final Text text;
		
		private final Dim2D dim;
		
		private final DrawingData data;
		
		public DrawText(FontRenderer fontRenderer, Text text, Dim2D dim, DrawingData data)
		{
			this.fontRenderer = fontRenderer;
			this.text = text;
			this.dim = dim;
			this.data = data;
		}

		@Override
		public void draw()
		{
			this.fontRenderer.drawStringWithShadow(this.text.getData(), 0, 0, this.data.getColor().getRGB());
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
			this.graphics.gui.drawRect(0, 0, this.dim.getWidth(), this.dim.getHeight(), this.data.getColor().getRGB());
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
			this.graphics.drawGradientRect(0, 0, this.dim.getWidth(), this.dim.getHeight(), this.startColor.getColor().getRGB(), this.endColor.getColor().getRGB());
		}
		
	}
	
}
