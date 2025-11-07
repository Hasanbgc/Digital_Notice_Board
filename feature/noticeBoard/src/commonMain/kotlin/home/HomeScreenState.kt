package home

data class HomeScreenState(
    val isLoading: Boolean = false,
    val poster: List<Poster> = emptyList(),
    val error: String? = null,
    val isEmergencyPosterExpanded: Boolean = false,
)

