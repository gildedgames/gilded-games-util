package com.gildedgames.util.ai.csp;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IConstraintProblem<VAR>
{
	Collection<? extends VAR> variables();

	Collection<Object> domain(VAR var);

	Collection<? extends IConstraint<VAR>> constraints();

	VAR firstVar(Map<VAR, List<Object>> domains);

	VAR selectNextVar(Collection<VAR> unassigned, Map<VAR, List<Object>> domains, VAR lastAssigned);

	/**
	 * Sort the remaining values, for example using the least
	 * constraining value heuristic.
	 */
	List<Object> sortValues(List<Object> values);

	/**
	 * Returns true if this is a valid assignment, and reduces
	 * the domains of each variable further using explicit 
	 * constraint propagation.
	 */
	boolean allowedAssign(VAR variable, Object value, Map<VAR, List<Object>> values);
}
