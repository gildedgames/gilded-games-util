package com.gildedgames.util.ai.csp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
		Collection<IConstraint<VAR>> constraints = problem.constraints();
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
		return backtrack(new HashMap<VAR, Object>(problem.variables().size()), problem, domains, graph);
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

	private static <VAR> Map<VAR, Object> backtrack(Map<VAR, Object> assignment, IConstraintProblem<VAR> problem, Map<VAR, List<Object>> domains, Multigraph<VAR, IConstraint<VAR>> graph)
	{
		Collection<VAR> vars = problem.variables();
		if (assignment.size() == vars.size())
		{
			//Assignment is complete
			return assignment;
		}
		List<VAR> unassigned = new ArrayList<VAR>(vars.size() - assignment.size());
		for (VAR var : vars)
		{
			if (!assignment.containsKey(var))
			{
				unassigned.add(var);
			}
		}

		//Choose next var to assign
		VAR var = problem.selectNextVar(unassigned, domains);
		List<IConstraint<VAR>> toCheck = new ArrayList<IConstraint<VAR>>();
		Set<VAR> keySet = assignment.keySet();

		//Find the constraints this var is in
		for (IConstraint<VAR> constraint : problem.constraints())
		{
			boolean hasAllVars = true;
			for (VAR inScope : constraint.scope())
			{
				if (!inScope.equals(var) && !keySet.contains(inScope))
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

		for (Object value : problem.sortDomain(domains.get(var)))
		{
			boolean consistent = true;
			//Make sure the assignment is consistent
			for (IConstraint<VAR> constraint : toCheck)
			{
				Object[] params = new Object[constraint.scope().size()];
				for (int i = 0; i < constraint.scope().size(); i++)
				{
					VAR param = constraint.scope().get(i);
					params[i] = param.equals(var) ? value : assignment.get(param);
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
			assignment.put(var, value);
			Map<VAR, List<Object>> newDomains = new HashMap<VAR, List<Object>>(domains);

			boolean success = true;
			for (IConstraint<VAR> neighbour : graph.edgesOf(var))
			{
				List<VAR> scope = neighbour.scope();
				int otherIndex = scope.get(0).equals(var) ? 1 : 0;
				VAR other = scope.get(otherIndex);
				if (assignment.containsKey(other))
				{
					continue;
				}
				List<Object> newDomain = new ArrayList<Object>(newDomains.get(other));
				Object[] params = new Object[2];
				params[otherIndex == 0 ? 1 : 0] = value;
				newDomains.put(other, newDomain);
				if (!reduceDomain(neighbour, newDomain, params, otherIndex))
				{
					success = false;
					break;
				}
			}

			if (!success)
			{
				assignment.remove(var);
				continue;
			}

			Map<VAR, Object> result = backtrack(assignment, problem, newDomains, graph);
			if (result != null)
			{
				return result;
			}
			assignment.remove(var);
		}
		return null;
	}
}
