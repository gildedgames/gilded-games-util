package com.gildedgames.util.ai.csp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.Multigraph;

/**
 * Constrain satisfaction solver based on Artificial Intelligence,
 * A Modern Approach by Stuart Russell and Peter Norvig, p 271.
 * Uses Backtracking search with forward checking. 
 * @author Emile
 *
 */
public class CSPSolver
{
	public static <VAR> Map<VAR, Object> solve(IConstraintProblem<VAR> problem)
	{
		//Initialize
		Collection<? extends IConstraint<VAR>> constraints = problem.constraints();
		Map<VAR, List<Object>> domains = new HashMap<VAR, List<Object>>(problem.variables().size());
		Multigraph<VAR, IConstraint<VAR>> graph = new Multigraph<VAR, IConstraint<VAR>>(new EdgeFactory<VAR, IConstraint<VAR>>()
		{
			@Override
			public IConstraint<VAR> createEdge(VAR sourceVertex, VAR targetVertex)
			{
				return null;
			}
		});

		for (VAR var : problem.variables())
		{
			graph.addVertex(var);
		}

		Set<IConstraint<VAR>> addedConstraints = new HashSet<IConstraint<VAR>>();
		for (VAR var : problem.variables())
		{
			List<Object> domain = new ArrayList<Object>(problem.domain(var));
			domains.put(var, domain);
			for (IConstraint<VAR> constraint : constraints)
			{
				Collection<VAR> scope = constraint.scope();
				if (!scope.contains(var))
				{
					continue;
				}
				//Remove impossible values
				if (scope.size() == 1 && !reduceDomain(constraint, domain, new Object[1], 0))
				{
					return null;
				}
				if (scope.size() == 2 && !addedConstraints.contains(constraint))
				{
					addedConstraints.add(constraint);
					VAR other = null;
					for (VAR it : scope)
					{
						if (!it.equals(var))
						{
							other = it;
						}
					}
					graph.addEdge(var, other, constraint);
				}
			}
		}
		return backtrack(problem, domains, graph, null);
	}

	private static boolean reduceDomain(IConstraint<?> constraint, List<Object> domain, Object[] params, int index)
	{
		Iterator<Object> iterator = domain.iterator();
		while (iterator.hasNext())
		{
			Object next = iterator.next();
			params[index] = next;
			if (!constraint.constraint(next))
			{
				iterator.remove();
			}
		}
		return !domain.isEmpty();
	}

	private static <VAR> Map<VAR, Object> backtrack(IConstraintProblem<VAR> problem, Map<VAR, List<Object>> domains, Multigraph<VAR, IConstraint<VAR>> graph, VAR lastAssigned)
	{
		Collection<? extends VAR> vars = problem.variables();

		List<VAR> assigned = new ArrayList<VAR>();
		List<VAR> unassigned = new ArrayList<VAR>();
		for (VAR var : vars)
		{
			int domainSize = domains.get(var).size();
			if (domainSize == 0)
			{
				return null;
			}
			else if (domainSize == 1)
			{
				assigned.add(var);
			}
			else
			{
				unassigned.add(var);
			}
		}

		if (unassigned.isEmpty())
		{
			Map<VAR, Object> result = new HashMap<VAR, Object>(vars.size());
			for (VAR var : vars)
			{
				result.put(var, domains.get(var).get(0));
			}
			return result;
		}

		//Choose next var to assign
		VAR var = null;
		if (lastAssigned == null)
		{
			var = problem.firstVar(domains);
		}
		else
		{
			var = problem.selectNextVar(unassigned, domains, lastAssigned);
		}
		List<IConstraint<VAR>> toCheck = new ArrayList<IConstraint<VAR>>();

		//Find the constraints that have this var in its scope
		for (IConstraint<VAR> constraint : problem.constraints())
		{
			boolean hasAllVars = true;
			for (VAR inScope : constraint.scope())
			{
				if (!inScope.equals(var) && !assigned.contains(inScope))
				{
					hasAllVars = false;
					break;
				}
			}
			if (hasAllVars)
			{
				toCheck.add(constraint);
			}
		}

		for (Object value : problem.sortValues(domains.get(var)))
		{
			boolean consistent = true;
			//Make sure the assignment is consistent
			for (IConstraint<VAR> constraint : toCheck)
			{
				Object[] params = new Object[constraint.scope().size()];
				for (int i = 0; i < constraint.scope().size(); i++)
				{
					VAR param = constraint.scope().get(i);
					params[i] = param.equals(var) ? value : domains.get(param).get(0);
				}
				if (!constraint.constraint(params))
				{
					consistent = false;
					break;
				}
			}
			if (!consistent)
			{
				continue;
			}
			Map<VAR, List<Object>> newDomains = new HashMap<VAR, List<Object>>(domains.size());
			for (Entry<VAR, List<Object>> entry : domains.entrySet())
			{
				newDomains.put(entry.getKey(), new ArrayList<Object>(entry.getValue()));
			}

			//Remove everything from the domain except the chosen value.
			Iterator<Object> iter = newDomains.get(var).iterator();
			while (iter.hasNext())
			{
				if (iter.next() != value)
				{
					iter.remove();
				}
			}

			//Performs AC1 (forward checking) to reduce the domains.
			boolean success = true;
			for (IConstraint<VAR> neighbour : graph.edgesOf(var))
			{
				List<VAR> scope = neighbour.scope();
				//We only use arcs, so we can assume that there are only two vars
				int otherIndex = scope.get(0).equals(var) ? 1 : 0;
				VAR other = scope.get(otherIndex);
				//If the other one already contains this, 
				if (assigned.contains(other))
				{
					continue;
				}
				List<Object> newDomain = newDomains.get(other);
				Object[] params = new Object[2];
				params[otherIndex == 0 ? 1 : 0] = value;
				if (!reduceDomain(neighbour, newDomain, params, otherIndex))
				{
					success = false;
					break;
				}
			}

			if (!success)
			{
				continue;
			}

			//Assign the value and possibly do explicit propagation
			if (!problem.allowedAssign(var, value, newDomains))
			{
				continue;
			}

			Map<VAR, Object> result = backtrack(problem, newDomains, graph, var);
			if (result != null)
			{
				return result;
			}
		}
		return null;
	}
}
