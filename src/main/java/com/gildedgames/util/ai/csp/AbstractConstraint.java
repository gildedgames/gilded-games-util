package com.gildedgames.util.ai.csp;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractConstraint<VAR> implements IConstraint<VAR>
{
	private final List<VAR> vars;

	public AbstractConstraint(VAR... vars)
	{
		this.vars = Arrays.asList(vars);
	}

	@Override
	public List<VAR> scope()
	{
		return this.vars;
	}
}
