package com.gildedgames.util.core.gui.util.wrappers;

import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.InputProvider;

public class MinecraftTextBackground extends GuiFrame
{
	
	private int backColor = -267386864, borderColor = 1347420415;
	
	public MinecraftTextBackground(Rect rect)
	{
		super(rect);
	}
	
	public MinecraftTextBackground(Rect rect, int backColor, int borderColor)
	{
		this(rect);
		
		this.backColor = backColor;
		this.borderColor = borderColor;
	}
	
	public int getBackColor()
	{
		return this.backColor;
	}
	
	public void setBackColor(int backColor)
	{
		this.backColor = backColor;
	}
	
	public int getBorderColor()
	{
		return this.borderColor;
	}
	
	public void setBorderColor(int borderColor)
	{
		this.borderColor = borderColor;
	}

	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		super.draw(graphics, input);
		
		GL11.glPushMatrix();
		this.drawTextBackground(this.dim().x() + 3F, this.dim().y() + 3F, this.dim().width() - 6, this.dim().height() - 7);
		GL11.glPopMatrix();
	}
	
	private void drawTextBackground(float cornerX, float cornerY, float width, float height)
	{
		final int l1 = this.backColor;
		this.drawGradientRect(cornerX - 3, cornerY - 4, cornerX + width + 3, cornerY - 3, l1, l1);
		this.drawGradientRect(cornerX - 3, cornerY + height + 3, cornerX + width + 3, cornerY + height + 4, l1, l1);
		this.drawGradientRect(cornerX - 3, cornerY - 3, cornerX + width + 3, cornerY + height + 3, l1, l1);
		this.drawGradientRect(cornerX - 4, cornerY - 3, cornerX - 3, cornerY + height + 3, l1, l1);
		this.drawGradientRect(cornerX + width + 3, cornerY - 3, cornerX + width + 4, cornerY + height + 3, l1, l1);
		final int i2 = this.borderColor;
		final int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
		this.drawGradientRect(cornerX - 3, cornerY - 3 + 1, cornerX - 3 + 1, cornerY + height + 3 - 1, i2, j2);
		this.drawGradientRect(cornerX + width + 2, cornerY - 3 + 1, cornerX + width + 3, cornerY + height + 3 - 1, i2, j2);
		this.drawGradientRect(cornerX - 3, cornerY - 3, cornerX + width + 3, cornerY - 3 + 1, i2, i2);
		this.drawGradientRect(cornerX - 3, cornerY + height + 2, cornerX + width + 3, cornerY + height + 3, j2, j2);
	}
	
	public void drawGradientRect(float minX, float minY, float maxX, float maxY, int par5, int par6)
	{
		int zLevel = 0;
		
		GL11.glPushMatrix();
		final float f = (par5 >> 24 & 255) / 255.0F;
		final float f1 = (par5 >> 16 & 255) / 255.0F;
		final float f2 = (par5 >> 8 & 255) / 255.0F;
		final float f3 = (par5 & 255) / 255.0F;
		final float f4 = (par6 >> 24 & 255) / 255.0F;
		final float f5 = (par6 >> 16 & 255) / 255.0F;
		final float f6 = (par6 >> 8 & 255) / 255.0F;
		final float f7 = (par6 & 255) / 255.0F;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		//GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		final Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(f1, f2, f3, f);
		tessellator.addVertex(maxX, minY, zLevel);
		tessellator.addVertex(minX, minY, zLevel);
		tessellator.setColorRGBA_F(f5, f6, f7, f4);
		tessellator.addVertex(minX, maxY, zLevel);
		tessellator.addVertex(maxX, maxY, zLevel);
		Tessellator.instance.draw();
		GL11.glShadeModel(GL11.GL_FLAT);
		//GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
	}
	
}
