package home

sealed class Poster {
    data class Emergency(
        val id: Int,
        val title: String,
        val description: String,
        val date: String,
        val distance: String,
        val time: String,
        val imageUrl: String,
        val location: String,
        val type: Type,
        val topic: Topic,
        var isExpanded: Boolean = false
    ) : Poster()

    data class Normal(
        val id: Int,
        val noticeId: String = "",
        val title: String,
        val description: String,
        val date: String,
        val distance: String,
        val time: String,
        val imageUrlList: List<String>,
        val location: String,
        val type: Type,
        val category: String = "",
        val profile: Profile,
        val attachments: List<String>,
        val mediaItems: List<domain.model.AttachmentItem> = emptyList(),
        val isFavorite: Boolean,
        val shareCount: Int,
        val commentCount: Int,
        val likeCount: Int,
        val isSaved: Boolean = false,
        val viewCount: Int,
        var isExpanded: Boolean = false,
        var liked: Like = Like.IDLE
    ) : Poster()

}


data class Profile(
    val name: String,
    val imageUrl: String,
    val institution: String,
    val designation: String
)

data class Comment(
    val id: Int,
    val authorName: String,
    val authorImageUrl: String,
    val text: String,
    val time: String
)

enum class Type{
    URGENT,
    NORMAL,
    HIGH,
    MEDIUM
}
enum class Topic {
    FIRE,
    FLOOD,
    LOAD_SHEDDING,
    ROAD_CONSTRUCTION,
    EARTHQUAKE,
    GAS_LEAK,
    ACCIDENT,
    STORM,
    CYCLONE,
    WATER_SUPPLY_DISRUPTION,
    ELECTRICITY_OUTAGE,
    TRAFFIC_JAM,
    POLICE_ALERT,
    MISSING_PERSON,
    HEALTH_ALERT,
    WEATHER_WARNING
}

enum class Like{
    IDLE,
    LIKED,
    UNLIKED
}