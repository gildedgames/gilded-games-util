package com.gildedgames.util.ai.csp;

import java.util.List;

public interface IConstraint<VAR>
{
	List<VAR> scope();

	boolean constraint(Object... values);
}
