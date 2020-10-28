package com.mictlan.math.graphs

class Dijkstra<T: IGraph<T>>(start: T, goal: T): Search<T>(start, goal){
    override fun search(maxSearchIteration: Int): SearchResultStatus {
        start.stepCost = 0.0;

        val open: MutableCollection<T> = mutableListOf(start)
        val visited: MutableMap<Int, T> = hashMapOf()

        var control = maxSearchIteration
        while(control-- > 0 ){
            if(open.isEmpty()) break
            val current = open.first()

            for (neighbor in current.neighbors) {
                val tentativeStepCost = neighbor.calculateStepCost(current)
                val tentativeCost =  tentativeStepCost + if (neighbor.parent == null )  0.0 else neighbor.pathCost
                if(tentativeCost < neighbor.pathCost){
                    neighbor.stepCost = tentativeStepCost
                    neighbor.parent = current
                }

                if(!visited.containsKey(neighbor.index)) open.add(neighbor)
            }

            open.removeIf { graph -> graph.index == current.index}
            visited[current.index] = current
        }
        if(goal.parent != null) return SearchResultStatus.FOUND
        return Search.SearchResultStatus.MAX_ITERATION_REACHED
    }
}