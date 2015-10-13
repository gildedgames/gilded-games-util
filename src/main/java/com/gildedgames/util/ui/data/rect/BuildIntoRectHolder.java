package com.gildedgames.util.ui.data.rect;


public class BuildIntoRectHolder extends RectBuilder
{

	protected RectHolder holder;

	public BuildIntoRectHolder(RectHolder holder)
	{
		super(holder);

		this.holder = holder;
	}

	@Override
	public Rect flush()
	{
		Rect commit = super.flush();

		this.holder.dim().set(commit);

		return commit;
	}

}