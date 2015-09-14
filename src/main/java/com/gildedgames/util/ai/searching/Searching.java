package com.gildedgames.util.ai.searching;

import java.util.HashSet;
import java.util.PriorityQueue;

public class Searching
{

	public static <T extends Node> T aStar(ISearchProblem<T> problem)
	{
		return weightedAStar(problem, 1.0);
	}

	public static <T extends Node> T weightedAStar(ISearchProblem<T> problem, double weight)
	{
		T initialState = problem.start();
		PriorityQueue<T> priorityQueue = new PriorityQueue<T>();
		HashSet<T> visitedStates = new HashSet<T>();

		priorityQueue.add(initialState);

		while (!priorityQueue.isEmpty())
		{
			T state = priorityQueue.poll();
			if (problem.isGoal(state))
			{
				return state;
			}

			if (problem.shouldTerminate(state))
			{
				return null;
			}

			if (problem.contains(visitedStates, state))
			{
				continue;
			}

			visitedStates.add(state);

			for (T newState : problem.successors(state))
			{
				newState.setG(problem.costBetween(state, newState) + state.getG());
				newState.setH(weight * problem.heuristic(newState));

				priorityQueue.add(newState);
			}
		}
		return null;
	}

}
