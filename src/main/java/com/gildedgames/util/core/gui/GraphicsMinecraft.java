package com.gildedgames.util.core.gui;

import com.gildedgames.util.ui.data.Dimensions2D;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.data.IResource;
import com.gildedgames.util.ui.data.Position2D;
import com.gildedgames.util.ui.graphics.IGraphics;
import com.gildedgames.util.ui.graphics.Sprite;
import com.gildedgames.util.ui.graphics.Text;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GraphicsMinecraft implements IGraphics
{
	
	protected final Minecraft minecraft;
	
	protected Gui gui = new Gui();
	
	protected final FontRenderer fontRenderer;

	public GraphicsMinecraft(Minecraft minecraft)
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
		
		GlStateManager.translate(dim.getX() - dim.getX() * data.getScale(), dim.getY() - dim.getY() * data.getScale(), 0);
		GlStateManager.scale(data.getScale(), data.getScale(), data.getScale());
		
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
	public void drawLine(DrawingData drawingData, Position2D startPos, Position2D endPos)
	{
		
	}

	@Override
	public void drawRectangle(Dimensions2D dim, DrawingData data)
	{
		this.draw(dim, data, new DrawRectangle(this, dim, data));
	}

	@Override
	public void drawGradientRectangle(DrawingData startColor, DrawingData endColor, Dimensions2D dim)
	{
		
	}
	
	private static interface DrawInner
	{
		
		void draw();
		
	}
	
	private static class DrawSprite implements DrawInner
	{
		
		private final GraphicsMinecraft graphics;
		
		private final Sprite sprite;
		
		private final Dimensions2D dim;
		
		public DrawSprite(GraphicsMinecraft graphics, Sprite sprite, Dimensions2D dim)
		{
			this.graphics = graphics;
			this.sprite = sprite;
			this.dim = dim;
		}

		@Override
		public void draw()
		{
			this.graphics.minecraft.renderEngine.bindTexture(this.graphics.convert(this.sprite.getResource()));
			Gui.drawModalRectWithCustomSizedTexture((int) this.dim.getX(), (int) this.dim.getY(), (int) this.sprite.getMinU(), (int) this.sprite.getMinV(), (int) this.sprite.getMaxU(), (int) this.sprite.getMaxV(), (int) this.sprite.getTextureWidth(), (int) this.sprite.getTextureHeight());
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
			float xOffset = this.dim.isCenteredHorizontally() ? this.fontRenderer.getStringWidth(text.getData()) / 2 : 0.0F;
			this.fontRenderer.drawStringWithShadow(this.text.getData(), this.dim.getX() - xOffset, this.dim.getY(), this.data.getColorHex());
		}
		
	}

	private static class DrawRectangle implements DrawInner
	{
		
		private final GraphicsMinecraft graphics;
		
		private final Dimensions2D dim;
		
		private final DrawingData data;
		
		public DrawRectangle(GraphicsMinecraft graphics, Dimensions2D dim, DrawingData data)
		{
			this.graphics = graphics;
			this.dim = dim;
			this.data = data;
		}

		@Override
		public void draw()
		{
			Gui.drawRect((int) this.dim.getX(), (int) this.dim.getY(), (int) (this.dim.getX() + this.dim.getWidth()), (int) (this.dim.getY() + this.dim.getHeight()), this.data.getColorHex());
		}
		
	}
	
}
