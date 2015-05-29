package com.gildedgames.util.ai.csp;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IConstraintProblem<VAR>
{
	Collection<VAR> variables();

	Collection<Object> domain(VAR var);

	Collection<IConstraint<VAR>> constraints();

	VAR selectNextVar(Collection<VAR> unassigned, Map<VAR, List<Object>> domains);

	List<Object> sortDomain(List<Object> domain);
}
