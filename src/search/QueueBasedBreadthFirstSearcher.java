package search;

import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;

/**
 * An implementation of a Searcher that performs an iterative search,
 * storing the list of next states in a Queue. This results in a
 * breadth-first search.
 * 
 */
public class QueueBasedBreadthFirstSearcher<T> extends Searcher<T> {
	private final List<T> states;
	private final List<T> predecessors;

	public QueueBasedBreadthFirstSearcher(SearchProblem<T> searchProblem) {
		super(searchProblem);
		states = new ArrayList<T>();
		predecessors = new ArrayList<T>();
	}

	@Override
	public List<T> solve() {
		if (solution != null) {
			return solution;
		}
        Queue<T> queueBFS = new LinkedList<T>();
		final List<T> solution = new ArrayList<T>();
		T initStateBFS = searchProblem.getInitialState();
		queueBFS.add(initStateBFS);
		states.add(initStateBFS);
		predecessors.add(initStateBFS);
		T cur = null;

		while (!queueBFS.isEmpty()) {
			cur = queueBFS.remove();
			visitedStates.add(cur);
			if (searchProblem.isGoalState(cur)) {
				break;
			}

			for (T successor : searchProblem.getSuccessors(cur)) {
				if (!visitedStates.contains(successor)) {
					queueBFS.add(successor);
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
				throw new  RuntimeException(
						"searcher should never find an invalid solution!");
			}
		}

        return solution;
	}
}
