package com.example.newlifekotlin.model

data class MovieDTO(
    val results: Array<MovieCard>
    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieDTO

        if (!results.contentEquals(other.results)) return false

        return true
    }

    override fun hashCode(): Int {
        return results.contentHashCode()
    }
}
