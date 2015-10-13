package com.gildedgames.util.ui.data.rect;


public class BuildWithRectHolder
{

	protected RectBuilder builder;

	protected RectHolder buildWith;

	BuildWithRectHolder(RectBuilder builder, RectHolder buildWith)
	{
		this.builder = builder;
		this.buildWith = buildWith;
	}
	
	public BuildWithRectHolder rotation()
	{
		this.builder.rotation = this.buildWith.dim().rotation();
		
		return this;
	}

	public BuildWithRectHolder degrees()
	{
		this.builder.rotation = this.builder.rotation.buildWith(this.buildWith.dim().rotation()).degrees().flush();

		return this;
	}

	public BuildWithRectHolder origin()
	{
		this.builder.rotation = this.builder.rotation.clone().origin(this.buildWith.dim().origin()).flush();

		return this;
	}

	public BuildWithRectHolder originX()
	{
		this.builder.rotation = this.builder.rotation.clone().originX(this.buildWith.dim().origin().x()).flush();

		return this;
	}

	public BuildWithRectHolder originY()
	{
		this.builder.rotation = this.builder.rotation.clone().originY(this.buildWith.dim().origin().y()).flush();

		return this;
	}

	public BuildWithRectHolder rotateCW()
	{
		this.builder.rotation = this.builder.rotation.buildWith(this.buildWith.dim().rotation()).addDegrees().flush();

		return this;
	}

	public BuildWithRectHolder rotateCCW()
	{
		this.builder.rotation = this.builder.rotation.buildWith(this.buildWith.dim().rotation()).subtractDegrees().flush();

		return this;
	}

	public BuildWithRectHolder scale()
	{
		this.builder.scale = this.buildWith.dim().scale();

		return this;
	}

	public BuildWithRectHolder height()
	{
		this.builder.height = this.buildWith.dim().height();

		return this;
	}

	public BuildWithRectHolder width()
	{
		this.builder.width = this.buildWith.dim().width();

		return this;
	}

	public BuildWithRectHolder pos()
	{
		this.builder.pos = this.buildWith.dim().pos();

		return this;
	}

	public BuildWithRectHolder center()
	{
		this.builder.centerX(this.buildWith.dim().isCenteredX()).centerY(this.buildWith.dim().isCenteredY());

		return this;
	}

	public BuildWithRectHolder centerX()
	{
		this.builder.centeredX = this.buildWith.dim().isCenteredX();

		return this;
	}

	public BuildWithRectHolder centerY()
	{
		this.builder.centeredY = this.buildWith.dim().isCenteredY();

		return this;
	}

	public BuildWithRectHolder area()
	{
		this.builder.width(this.buildWith.dim().width()).height(this.buildWith.dim().height());

		return this;
	}

	public BuildWithRectHolder y()
	{
		this.builder.pos(this.builder.pos.clone().y(this.buildWith.dim().y()).flush());

		return this;
	}

	public BuildWithRectHolder x()
	{
		this.builder.pos(this.builder.pos.clone().x(this.buildWith.dim().x()).flush());

		return this;
	}

	public BuildWithRectHolder addScale()
	{
		this.builder.scale += this.buildWith.dim().scale();

		return this;
	}

	public BuildWithRectHolder addWidth()
	{
		this.builder.width(this.builder.width + this.buildWith.dim().width());

		return this;
	}

	public BuildWithRectHolder addHeight()
	{
		this.builder.area(this.builder.width, this.builder.height + this.buildWith.dim().height());

		return this;
	}

	public BuildWithRectHolder addArea()
	{
		this.builder.addWidth(this.buildWith.dim().width()).addHeight(this.buildWith.dim().height());

		return this;
	}

	public BuildWithRectHolder addX()
	{
		this.builder.pos(this.builder.pos.clone().addX(this.buildWith.dim().x()).flush());

		return this;
	}

	public BuildWithRectHolder addY()
	{
		this.builder.pos(this.builder.pos.clone().addY(this.buildWith.dim().y()).flush());

		return this;
	}

	public BuildWithRectHolder addPos()
	{
		this.builder.addX(this.buildWith.dim().x()).addY(this.buildWith.dim().y());

		return this;
	}

	public RectBuilder build()
	{
		return this.builder;
	}

	public Rect flush()
	{
		return this.builder.flush();
	}

}