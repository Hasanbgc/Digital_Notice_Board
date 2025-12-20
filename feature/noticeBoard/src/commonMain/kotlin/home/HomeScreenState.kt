package home

data class HomeScreenState(
    val isLoading: Boolean = false,
    val poster: List<Poster> = posterList,
    val error: String? = null,
    val isEmergencyPosterExpanded: Boolean = false,
)
val posterList = listOf(
    Poster.Emergency(
        0,
        "Flash Flood Warning, please stay away form there",
        "Heavy rainfall causing flood in low-laying areas. lorem ipsum dolor sit amet, consectetur adipiscing elit.  ",
        "5/11/2025",
        "2km away",
        "5 min ago",
        "",
        "Dhanmondi Area, Near Dhaka University",
        Type.HIGH,
        Topic.FIRE
    ),
    Poster.Emergency(
        1,
        "Flash Flood Warning",
        "Heavy rainfall causing flood in low-laying areas. lorem ipsum dolor sit amet, consectetur adipiscing elit.  ",
        "5/11/2025",
        "2km away",
        "5 min ago",
        "",
        "Dhanmondi Area",
        Type.MEDIUM,
        Topic.FIRE
    ),
    Poster.Emergency(
        2,
        "Flash Flood Warning",
        "Heavy rainfall causing flood in low-laying areas. lorem ipsum dolor sit amet, consectetur adipiscing elit.  ",
        "5/11/2025",
        "2km away",
        "5 min ago",
        "",
        "Dhanmondi Area",
        Type.NORMAL,
        Topic.FIRE
    ),
    Poster.Normal(
        3,
        "University Admission Test Result Published",
        description = "Results for the 2024 admission test are now available. Students can check their results using their registration number and celebrate their achievements!," +
                "Results for the 2024 admission test are now available. Students can check their results using their registration number and celebrate their achievements!"+
                "Results for the 2024 admission test are now available. Students can check their results using their registration number and celebrate their achievements!",
        date = "5/11/2025",
        distance = "2km away",
        time = "5 min ago",
        imageUrlList = listOf(
            "https://picsum.photos/id/10/400/300",
            "https://picsum.photos/id/20/400/300",
            "https://picsum.photos/id/30/400/300",
        ),
        location = "Dhanmondi Area",
        type = Type.URGENT,
        profile = Profile(
            name = "John Doe",
            imageUrl = "https://picsum.photos/id/10/400/300",
            institution = "Chittagong University",
            designation = "Student"
        ),
        attachments = listOf("Job_description.pdf", "Application_Form.docx"),
        isFavorite = false,
        shareCount = 23,
        commentCount = 45,
        likeCount = 123,
        isSaved = false,
        viewCount = 1250,
        isExpanded = false,
        liked = Like.IDLE
    ),
    Poster.Normal(
        4,
        "University Admission Test Result Published",
        description = "Results for the 2024 admission test are now available. Students can check their results using their registration number and celebrate their achievements!",
        date = "5/11/2025",
        distance = "2km away",
        time = "5 min ago",
        imageUrlList = listOf(
            "https://picsum.photos/id/10/400/300",
            "https://picsum.photos/id/20/400/300",
            "https://picsum.photos/id/30/400/300",
        ),
        location = "Dhanmondi Area",
        type = Type.URGENT,
        profile = Profile(
            name = "John Doe",
            imageUrl = "https://picsum.photos/id/10/400/300",
            institution = "Chittagong University",
            designation = "Student"
        ),
        attachments = listOf("Job_description.pdf", "Application_Form.docx", "Job_description.pdf"),
        isFavorite = false,
        shareCount = 23,
        commentCount = 45,
        likeCount = 123,
        isSaved = false,
        viewCount = 1250,
        isExpanded = false,
        liked = Like.IDLE
    ),
    Poster.Normal(
        5,
        "University Admission Test Result Published",
        description = "Results for the 2024 admission test are now available. Students can check their results using their registration number and celebrate their achievements!",
        date = "5/11/2025",
        distance = "2km away",
        time = "5 min ago",
        imageUrlList = listOf(
            "https://picsum.photos/id/10/400/300",
            "https://picsum.photos/id/20/400/300",
            "https://picsum.photos/id/30/400/300",
        ),
        location = "Dhanmondi Area",
        type = Type.URGENT,
        profile = Profile(
            name = "John Doe",
            imageUrl = "https://picsum.photos/id/10/400/300",
            institution = "Chittagong University",
            designation = "Student"
        ),
        attachments = listOf("Job_description.pdf", "Application_Form.docx"),
        isFavorite = false,
        shareCount = 23,
        commentCount = 45,
        likeCount = 123,
        isSaved = false,
        viewCount = 1250,
        isExpanded = false,
        liked = Like.IDLE
    )
)
