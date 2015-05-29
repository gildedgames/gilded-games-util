package com.gildedgames.util.ai.csp;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IConstraintProblem<VAR>
{
	Collection<? extends VAR> variables();

	Collection<Object> domain(VAR var);

	Collection<? extends IConstraint<VAR>> constraints();

	VAR selectNextVar(Collection<VAR> unassigned, Map<VAR, List<Object>> domains);

	List<Object> sortDomain(List<Object> domain);
}
