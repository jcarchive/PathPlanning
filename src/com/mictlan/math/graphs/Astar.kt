package com.mictlan.math.graphs

class Astar<T: IGraph<T>>(start: T, goal: T): Search<T>(start, goal){

    override fun search(maxSearchIteration: Int): Search.SearchResultStatus {
        start.pathCost = 0.0
        goal.pathCost = Double.MIN_VALUE
        val open: MutableCollection<T> = mutableListOf(start)
        val visited: MutableMap<Int, T> = hashMapOf()

        var control = maxSearchIteration
        while(control-- > 0 ){
            if(open.isEmpty())return Search.SearchResultStatus.NOT_FOUND
            val current = open.minBy{ graph -> graph.weight }!!

            if(current.index == goal.index){ return Search.SearchResultStatus.FOUND }
            for (neighbor in current.neighbors) {
                val h = neighbor.calculateHeuristic(current, goal)
                val g = neighbor.calculatePathcost(current, goal)
                if(!visited.containsKey(neighbor.index)){
                    neighbor.parent = current
                    neighbor.heuristic = h
                    neighbor.pathCost = g
                    open.add(neighbor)
                }
            }
            open.removeIf { graph -> graph.index == current.index}
            visited[current.index] = current
        }
        return Search.SearchResultStatus.MAX_ITERATION_REACHED
    }

}