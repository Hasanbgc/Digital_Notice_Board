package home

fun generateDummyNotices(page: Int, pageSize: Int): List<Poster.Normal> {
    val start = (page - 1) * pageSize

    return List(150) { index ->
        val id = start + index
            Poster.Normal(
                id = id,
                title = "University Admission Test Result Published",
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
            )
    }
}

/*
if (id % 50 == 0) {
    Poster.Emergency(
        id = id,
        title = "Emergency #$id",
        description = "This is emergency alert number $id",
        type = Type.HIGH,
        location = "Dhaka",
        distance = "2km",
        time = "5 min ago",
        topic = Topic.FIRE,
        isExpanded = false,
        date = "5-4-2026",
        imageUrl = ""
    )
}
else {*/
