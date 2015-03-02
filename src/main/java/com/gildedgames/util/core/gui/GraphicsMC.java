package com.gildedgames.util.core.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.data.IResource;
import com.gildedgames.util.ui.data.Position2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.graphics.Sprite;
import com.gildedgames.util.ui.graphics.Text;

public class GraphicsMC implements IGraphics
{
	
	protected Minecraft minecraft;
	
	protected Gui gui = new Gui();
	
	protected FontRenderer fontRenderer;
	
	protected float zLevel;

	public GraphicsMC(Minecraft minecraft)
	{
		this.minecraft = minecraft;
		this.fontRenderer = this.minecraft.fontRendererObj;
	}

	private ResourceLocation convert(IResource resource)
	{
		return new ResourceLocation(resource.getDomain(), resource.getPath());
	}
	
	private void draw(Dimensions2D dim, DrawingData data, DrawInner inner)
	{
		GlStateManager.pushMatrix();

		GlStateManager.translate(dim.getX() - dim.getX() * dim.getScale(), dim.getY() - dim.getY() * dim.getScale(), 0);
		GlStateManager.scale(dim.getScale(), dim.getScale(), dim.getScale());
		
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GlStateManager.color(data.getRed(), data.getGreen(), data.getBlue(), data.getAlpha());

		inner.draw();
		
		GlStateManager.popMatrix();
	}
	
	@Override
	public void drawSprite(Sprite sprite, Dimensions2D dim, DrawingData data)
	{
		this.draw(dim, data, new DrawSprite(this, sprite, dim));
	}

	@Override
	public void drawText(Text text, Dimensions2D dim, DrawingData data)
	{
		this.draw(dim, data, new DrawText(this.fontRenderer, text, dim, data));
	}
	
	@Override
	public void drawLine(Position2D startPos, Position2D endPos, DrawingData drawingData)
	{
		
	}

	@Override
	public void drawRectangle(Dimensions2D dim, DrawingData data)
	{
		this.draw(dim, data, new DrawRectangle(this, dim, data));
	}

	@Override
	public void drawGradientRectangle(Dimensions2D dim, DrawingData startColor, DrawingData endColor)
	{
		this.draw(dim, startColor, new DrawGradientRectangle(this, dim, startColor, endColor));
	}
	
    public void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor)
    {
        float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
        GlStateManager.func_179090_x();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.startDrawingQuads();
        worldrenderer.func_178960_a(f1, f2, f3, f);
        worldrenderer.addVertex((double)right, (double)top, (double)this.zLevel);
        worldrenderer.addVertex((double)left, (double)top, (double)this.zLevel);
        worldrenderer.func_178960_a(f5, f6, f7, f4);
        worldrenderer.addVertex((double)left, (double)bottom, (double)this.zLevel);
        worldrenderer.addVertex((double)right, (double)bottom, (double)this.zLevel);
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.func_179098_w();
    }
	
	private static interface DrawInner
	{
		void draw();
	}
	
	private static class DrawSprite implements DrawInner
	{
		
		private final GraphicsMC graphics;
		
		private final Sprite sprite;
		
		private final Dimensions2D dim;
		
		public DrawSprite(GraphicsMC graphics, Sprite sprite, Dimensions2D dim)
		{
			this.graphics = graphics;
			this.sprite = sprite;
			this.dim = dim;
		}

		@Override
		public void draw()
		{
			this.graphics.minecraft.renderEngine.bindTexture(this.graphics.convert(this.sprite.getResource()));
			this.graphics.gui.drawModalRectWithCustomSizedTexture((int) this.dim.getX(), (int) this.dim.getY(), (int) this.sprite.getMinU(), (int) this.sprite.getMinV(), (int) (this.sprite.getMaxU() - this.sprite.getMinU()), (int) (this.sprite.getMaxV() - this.sprite.getMinV()), (int) this.sprite.getTextureWidth(), (int) this.sprite.getTextureHeight());
		}
		
	}
	
	private static class DrawText implements DrawInner
	{
		
		private final FontRenderer fontRenderer;
		
		private final Text text;
		
		private final Dimensions2D dim;
		
		private final DrawingData data;
		
		public DrawText(FontRenderer fontRenderer, Text text, Dimensions2D dim, DrawingData data)
		{
			this.fontRenderer = fontRenderer;
			this.text = text;
			this.dim = dim;
			this.data = data;
		}

		@Override
		public void draw()
		{
			this.fontRenderer.func_175063_a(this.text.getData(), this.dim.getX(), this.dim.getY(), this.data.getColor().getRGB());
		}
		
	}

	private static class DrawRectangle implements DrawInner
	{
		
		private final GraphicsMC graphics;
		
		private final Dimensions2D dim;
		
		private final DrawingData data;
		
		public DrawRectangle(GraphicsMC graphics, Dimensions2D dim, DrawingData data)
		{
			this.graphics = graphics;
			this.dim = dim;
			this.data = data;
		}

		@Override
		public void draw()
		{
			this.graphics.gui.drawRect((int)this.dim.getX(), (int)this.dim.getY(), (int)(this.dim.getX() + this.dim.getWidth()), (int)(this.dim.getY() + this.dim.getHeight()), this.data.getColor().getRGB());
		}
		
	}
	
	private static class DrawGradientRectangle implements DrawInner
	{
		
		private final GraphicsMC graphics;
		
		private final Dimensions2D dim;
		
		private final DrawingData startColor, endColor;
		
		public DrawGradientRectangle(GraphicsMC graphics, Dimensions2D dim, DrawingData startColor, DrawingData endColor)
		{
			this.graphics = graphics;
			this.dim = dim;
			this.startColor = startColor;
			this.endColor = endColor;
		}

		@Override
		public void draw()
		{
			this.graphics.drawGradientRect((int)this.dim.getX(), (int)this.dim.getY(), (int)(this.dim.getX() + this.dim.getWidth()), (int)(this.dim.getY() + this.dim.getHeight()), this.startColor.getColor().getRGB(), this.endColor.getColor().getRGB());
		}
		
	}
	
}
