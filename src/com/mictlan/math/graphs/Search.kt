package com.mictlan.math.graphs

abstract class Search<T: IGraph<T>>(protected val start: T, protected val goal: T) {
    enum class SearchResultStatus{
        FOUND,
        NOT_FOUND,
        MAX_ITERATION_REACHED
    }

    abstract fun search(maxSearchIteration: Int = 1000): SearchResultStatus

    fun  recoverSolution( end: T): Collection<T>
    {
        val collection = mutableListOf<T>()
        var current: T? = end
        while(current != null){
            collection.add(current)
            current = current.parent
        }
        return collection
    }
}