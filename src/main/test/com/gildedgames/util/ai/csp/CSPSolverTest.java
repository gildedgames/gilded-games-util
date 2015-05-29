package com.gildedgames.util.ai.csp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class CSPSolverTest
{
	@Test
	public void testSolve()
	{
		IConstraintProblem<String> problem = new AbstractConstraintProblem<String>()
		{
			@Override
			public Collection<String> variables()
			{
				return new ArrayList<String>()
				{
					{
						this.add("WA");
						this.add("NT");
						this.add("SA");
						this.add("Q");
						this.add("V");
						this.add("NSW");
						this.add("T");
					}
				};
			}

			@Override
			public Collection<Object> domain(String var)
			{
				return new ArrayList<Object>()
				{
					{
						this.add("red");
						this.add("green");
						this.add("blue");
					}
				};
			}

			@Override
			public Collection<IConstraint<String>> constraints()
			{
				return new ArrayList<IConstraint<String>>()
				{
					{
						this.add(new ConstraintAllDiff<String>("WA", "NT"));
						this.add(new ConstraintAllDiff<String>("WA", "SA"));
						this.add(new ConstraintAllDiff<String>("SA", "NT"));
						this.add(new ConstraintAllDiff<String>("Q", "NT"));
						this.add(new ConstraintAllDiff<String>("SA", "NSW"));
						this.add(new ConstraintAllDiff<String>("SA", "Q"));
						this.add(new ConstraintAllDiff<String>("NSW", "Q"));
						this.add(new ConstraintAllDiff<String>("SA", "V"));
						this.add(new ConstraintAllDiff<String>("NSW", "V"));
					}
				};
			}

			@Override
			public List<Object> sortDomain(List<Object> domain)
			{
				return domain;
			}
		};
		Map<String, Object> result = CSPSolver.solve(problem);
		for (Entry<String, Object> entry : result.entrySet())
		{
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}
}
