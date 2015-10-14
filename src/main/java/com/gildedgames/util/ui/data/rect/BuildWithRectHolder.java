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
		this.builder.rotation = this.buildWith.dim().originalState().rotation();
		
		return this;
	}

	public BuildWithRectHolder degrees()
	{
		this.builder.rotation = this.builder.rotation.buildWith(this.buildWith.dim().originalState().rotation()).degrees().flush();

		return this;
	}

	public BuildWithRectHolder origin()
	{
		this.builder.rotation = this.builder.rotation.clone().origin(this.buildWith.dim().originalState().origin()).flush();

		return this;
	}

	public BuildWithRectHolder originX()
	{
		this.builder.rotation = this.builder.rotation.clone().originX(this.buildWith.dim().originalState().origin().x()).flush();

		return this;
	}

	public BuildWithRectHolder originY()
	{
		this.builder.rotation = this.builder.rotation.clone().originY(this.buildWith.dim().originalState().origin().y()).flush();

		return this;
	}

	public BuildWithRectHolder rotateCW()
	{
		this.builder.rotation = this.builder.rotation.buildWith(this.buildWith.dim().originalState().rotation()).addDegrees().flush();

		return this;
	}

	public BuildWithRectHolder rotateCCW()
	{
		this.builder.rotation = this.builder.rotation.buildWith(this.buildWith.dim().originalState().rotation()).subtractDegrees().flush();

		return this;
	}

	public BuildWithRectHolder scale()
	{
		this.builder.scale = this.buildWith.dim().originalState().scale();

		return this;
	}

	public BuildWithRectHolder height()
	{
		this.builder.height = this.buildWith.dim().originalState().height();

		return this;
	}

	public BuildWithRectHolder width()
	{
		this.builder.width = this.buildWith.dim().originalState().width();

		return this;
	}

	public BuildWithRectHolder pos()
	{
		this.builder.pos = this.buildWith.dim().originalState().pos();

		return this;
	}

	public BuildWithRectHolder center()
	{
		this.builder.centerX(this.buildWith.dim().originalState().isCenteredX()).centerY(this.buildWith.dim().originalState().isCenteredY());

		return this;
	}

	public BuildWithRectHolder centerX()
	{
		this.builder.centeredX = this.buildWith.dim().originalState().isCenteredX();

		return this;
	}

	public BuildWithRectHolder centerY()
	{
		this.builder.centeredY = this.buildWith.dim().originalState().isCenteredY();

		return this;
	}

	public BuildWithRectHolder area()
	{
		this.builder.width(this.buildWith.dim().originalState().width()).height(this.buildWith.dim().originalState().height());

		return this;
	}

	public BuildWithRectHolder y()
	{
		this.builder.pos(this.builder.pos.clone().y(this.buildWith.dim().originalState().y()).flush());

		return this;
	}

	public BuildWithRectHolder x()
	{
		this.builder.pos(this.builder.pos.clone().x(this.buildWith.dim().originalState().x()).flush());

		return this;
	}

	public BuildWithRectHolder addScale()
	{
		this.builder.scale += this.buildWith.dim().originalState().scale();

		return this;
	}

	public BuildWithRectHolder addWidth()
	{
		this.builder.width(this.builder.width + this.buildWith.dim().originalState().width());

		return this;
	}

	public BuildWithRectHolder addHeight()
	{
		this.builder.area(this.builder.width, this.builder.height + this.buildWith.dim().originalState().height());

		return this;
	}

	public BuildWithRectHolder addArea()
	{
		this.builder.addWidth(this.buildWith.dim().originalState().width()).addHeight(this.buildWith.dim().originalState().height());

		return this;
	}

	public BuildWithRectHolder addX()
	{
		this.builder.pos(this.builder.pos.clone().addX(this.buildWith.dim().originalState().x()).flush());

		return this;
	}

	public BuildWithRectHolder addY()
	{
		this.builder.pos(this.builder.pos.clone().addY(this.buildWith.dim().originalState().y()).flush());

		return this;
	}

	public BuildWithRectHolder addPos()
	{
		this.builder.addX(this.buildWith.dim().originalState().x()).addY(this.buildWith.dim().originalState().y());

		return this;
	}

	public RectBuilder rebuild()
	{
		return this.builder;
	}

	public Rect flush()
	{
		return this.builder.flush();
	}

}