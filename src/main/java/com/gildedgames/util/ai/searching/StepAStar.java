package com.gildedgames.util.ai.searching;

import java.util.HashSet;
import java.util.PriorityQueue;

public class StepAStar<T extends Node>
{
	private final ISearchProblem<T> problem;

	private final PriorityQueue<T> queue = new PriorityQueue<>();

	private final HashSet<T> visitedStates = new HashSet<>();

	private final double hWeight;

	private boolean terminated;

	private T currentState;

	public StepAStar(ISearchProblem<T> problem, double hWeight)
	{
		this.queue.add(problem.start());
		this.problem = problem;
		this.hWeight = hWeight;
	}

	public void step()
	{
		if (this.terminated)
		{
			return;
		}
		if (this.queue.isEmpty())
		{
			this.currentState = null;
			this.terminated = true;
			return;
		}
		this.currentState = this.queue.poll();
		if (this.problem.isGoal(this.currentState))
		{
			this.terminated = true;
			return;
		}

		if (this.problem.shouldTerminate(this.currentState))
		{
			this.terminated = true;
			//this.currentState = null;
			return;
		}

		if (this.problem.contains(this.visitedStates, this.currentState))
		{
			return;
		}

		this.visitedStates.add(this.currentState);

		for (T state : this.problem.successors(this.currentState))
		{
			state.setG(this.problem.costBetween(this.currentState, state) + this.currentState.getG());
			state.setH(this.hWeight * this.problem.heuristic(state));

			this.queue.add(state);
		}
	}

	public boolean isTerminated()
	{
		return this.terminated;
	}

	public T currentState()
	{
		return this.currentState;
	}
}
