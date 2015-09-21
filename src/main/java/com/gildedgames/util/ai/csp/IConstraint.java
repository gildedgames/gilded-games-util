package com.gildedgames.util.ai.csp;

import java.util.List;

public interface IConstraint<VAR>
{
	/**
	 * Returns all variables that affect this constraint.
	 */
	List<VAR> scope();

	/**
	 * Returns true if the constraint is satisfied using
	 * the values given. The input array is in the same
	 * order as scope().
	 */
	boolean constraint(Object... values);
}
