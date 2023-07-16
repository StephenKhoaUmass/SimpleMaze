package search;

import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;

/**
 * An implementation of a Searcher that performs an iterative search,
 * storing the list of next states in a Stack. This results in a
 * depth-first search.
 * 
 */
public class StackBasedDepthFirstSearcher<T> extends Searcher<T> {
	private final List<T> states;
	private final List<T> predecessors;

	public StackBasedDepthFirstSearcher(SearchProblem<T> searchProblem) {
		super(searchProblem);
		states = new ArrayList<T>();
		predecessors = new ArrayList<T>();
	}

	@Override
	public List<T> solve() {
		if (solution != null) {
			return solution;
		}
        Stack<T> stackDFS = new Stack<T>();
		final List<T> solution = new ArrayList<T>();
		T initStateBFS = searchProblem.getInitialState();
		stackDFS.push(initStateBFS);
		states.add(initStateBFS);
		predecessors.add(initStateBFS);
		T cur = null;

		while (!stackDFS.isEmpty()) {
			cur = stackDFS.pop();
			visitedStates.add(cur);
			if (searchProblem.isGoalState(cur)) {
				break;
			}
			
			for (T successor : searchProblem.getSuccessors(cur)) {
				if (!visitedStates.contains(successor)) {
					stackDFS.push(successor);
					states.add(successor);
					predecessors.add(successor);
					predecessors.set(states.indexOf(successor), cur);
				}
			}
		}

		T goal = cur;

		if (goal != null) {
			
			solution.add(goal);
			while (!goal.equals(searchProblem.getInitialState())) {
				final T predecessor = predecessors.get(states.indexOf(goal));
				solution.add(predecessor);
				goal = predecessor;
			}

			Collections.reverse(solution);
		}

		if (solution.size() > 0) {
			if (!isValid(solution)) {
				throw new RuntimeException(
						"searcher should never find an invalid solution!");
			}
		}

		return solution;
	}
}
