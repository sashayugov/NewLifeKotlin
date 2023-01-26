package com.example.newlifekotlin.model

data class SearchMovieDTO(
    val docs: Array<SearchMovieCard>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchMovieDTO

        if (!docs.contentEquals(other.docs)) return false

        return true
    }

    override fun hashCode(): Int {
        return docs.contentHashCode()
    }
}

data class SearchMovieCard(
    var id: String,
    var name: String,
    var description: String,
    var year: String,
    var rating: RatingDTO,
    var poster: ImageDTO?,
    var movieNote: String
) {
    data class RatingDTO(var imdb: String)
    data class ImageDTO(var url: String)
}
