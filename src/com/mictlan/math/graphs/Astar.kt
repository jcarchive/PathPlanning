package com.mictlan.math.graphs

class Astar<T: IGraph<T>>(start: T, goal: T): Search<T>(start, goal){

    override fun search(maxSearchIteration: Int): Search.SearchResultStatus {
        start.heuristic = 0.0
        start.stepCost = 0.0

        goal.heuristic = Double.MIN_VALUE

        val open: MutableCollection<T> = mutableListOf(start)
        val visited: MutableMap<Int, T> = hashMapOf()

        var control = maxSearchIteration
        while(control-- > 0 ){
            if(open.isEmpty())return Search.SearchResultStatus.NOT_FOUND
            val current = open.minBy{ graph -> graph.weight}!!

            if(current.index == goal.index){ return Search.SearchResultStatus.FOUND }
            for (neighbor in current.neighbors) {
                val h = neighbor.calculateHeuristic(goal)
                val g = neighbor.calculateStepCost(current)
                if(!visited.containsKey(neighbor.index)) {
                    neighbor.parent = current
                    neighbor.heuristic = h
                    neighbor.stepCost = g
                    open.add(neighbor)
                }else{
                    val tentativeCost = h + g + current.pathCost
                    if(tentativeCost < neighbor.pathCost){
                        neighbor.parent = current
                        neighbor.heuristic = h
                        neighbor.stepCost = g
                    }
                }
            }
            open.removeIf { graph -> graph.index == current.index}
            visited[current.index] = current
        }
        return Search.SearchResultStatus.MAX_ITERATION_REACHED
    }

}