package com.gildedgames.util.ai.csp;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Contains the minimum-remaining-values
 * and least-constraining-value heuristics
 * from Artificial Intelligence: A modern
 * approach p 272, 273
 * @author Emile
 *
 */
public abstract class AbstractConstraintProblem<VAR> implements IConstraintProblem<VAR>
{

	@Override
	public VAR selectNextVar(Collection<VAR> unassigned, Map<VAR, List<Object>> domains, VAR lastAssigned)
	{
		int bestSize = Integer.MAX_VALUE;
		VAR bestVar = null;
		for (VAR var : unassigned)
		{
			if (domains.get(var).size() < bestSize)
			{
				bestVar = var;
			}
		}
		return bestVar;
	}

}
