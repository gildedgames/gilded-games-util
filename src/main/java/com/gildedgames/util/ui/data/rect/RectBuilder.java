package com.gildedgames.util.ui.data.rect;

import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.Rotation2D;
import com.gildedgames.util.ui.util.rect.RectCollection;

public class RectBuilder
{

	protected Pos2D pos = Pos2D.flush();

	protected float width, height;

	protected boolean centeredX, centeredY;

	protected float scale = 1.0F;

	protected Rotation2D rotation = Rotation2D.flush();

	public RectBuilder()
	{

	}

	public RectBuilder(RectHolder holder)
	{
		this(holder.dim());
	}

	public RectBuilder(Rect rect)
	{
		this.pos = rect.pos();

		this.width = rect.width();
		this.height = rect.height();

		this.scale = rect.scale();

		this.centeredX = rect.isCenteredX();
		this.centeredY = rect.isCenteredY();

		this.rotation = rect.rotation();
	}

	public BuildWithRectHolder buildWith(RectHolder holder)
	{
		return new BuildWithRectHolder(this, holder);
	}

	public BuildWithRectHolder buildWith(Rect dim)
	{
		return this.buildWith(RectCollection.flush(dim));
	}

	public RectBuilder rotation(Rotation2D rotation)
	{
		this.rotation = rotation;

		return this;
	}

	public RectBuilder degrees(float degrees)
	{
		this.rotation = this.rotation.clone().degrees(degrees).flush();

		return this;
	}

	public RectBuilder origin(Pos2D origin)
	{
		this.rotation = this.rotation.clone().origin(origin).flush();

		return this;
	}

	public RectBuilder origin(float x, float y)
	{
		this.rotation = this.rotation.clone().origin(x, y).flush();

		return this;
	}

	public RectBuilder originX(float x)
	{
		this.rotation = this.rotation.clone().originX(x).flush();

		return this;
	}

	public RectBuilder originY(float y)
	{
		this.rotation = this.rotation.clone().originY(y).flush();

		return this;
	}

	public RectBuilder addDegrees(float degrees)
	{
		this.rotation = this.rotation.clone().addDegrees(degrees).flush();

		return this;
	}

	public RectBuilder subtractDegrees(float degrees)
	{
		this.rotation = this.rotation.clone().subtractDegrees(degrees).flush();

		return this;
	}

	public RectBuilder resetPos()
	{
		this.pos = Pos2D.flush();

		return this;
	}

	public RectBuilder scale(float scale)
	{
		this.scale = scale;

		return this;
	}

	public RectBuilder height(float height)
	{
		this.height = height;

		return this;
	}

	public RectBuilder width(float width)
	{
		this.width = width;

		return this;
	}

	public RectBuilder pos(Pos2D position)
	{
		this.pos = position;

		return this;
	}

	public RectBuilder pos(float x, float y)
	{
		this.pos = Pos2D.flush(x, y);

		return this;
	}

	public RectBuilder center(boolean centeredX, boolean centeredY)
	{
		return this.centerX(centeredX).centerY(centeredY);
	}

	public RectBuilder centerX(boolean centeredX)
	{
		this.centeredX = centeredX;

		return this;
	}

	public RectBuilder centerY(boolean centeredY)
	{
		this.centeredY = centeredY;

		return this;
	}

	public RectBuilder area(float width, float height)
	{
		return this.width(width).height(height);
	}

	public RectBuilder y(float y)
	{
		return this.pos(this.pos.clone().y(y).flush());
	}

	public RectBuilder x(float x)
	{
		return this.pos(this.pos.clone().x(x).flush());
	}

	public RectBuilder center(boolean centered)
	{
		return this.center(centered, centered);
	}

	public RectBuilder addScale(float scale)
	{
		this.scale += scale;

		return this;
	}

	public RectBuilder addWidth(float width)
	{
		return this.width(this.width + width);
	}

	public RectBuilder addHeight(float height)
	{
		return this.area(this.width, this.height + height);
	}

	public RectBuilder addArea(float width, float height)
	{
		return this.addWidth(width).addHeight(height);
	}

	public RectBuilder addX(float x)
	{
		return this.pos(this.pos.clone().addX(x).flush());
	}

	public RectBuilder addY(float y)
	{
		return this.pos(this.pos.clone().addY(y).flush());
	}

	public RectBuilder addPos(Pos2D pos)
	{
		return this.addX(pos.x()).addY(pos.y());
	}

	/**
	 * Finalise the state and return it. Some implementations
	 * automatically set it to its holder.
	 * @return
	 */
	public Rect flush()
	{
		return new Dim2D(this);
	}

}
