package com.gildedgames.util.ai.searching;

import java.util.Collection;
import java.util.List;

public interface ISearchProblem<T extends Node>
{
	T start();

	List<T> successors(T parentState);

	boolean isGoal(T state);

	double heuristic(T state);

	double costBetween(T parent, T child);

	boolean shouldTerminate(T currentState);

	boolean contains(Collection<T> visitedStates, T currentState);
}
