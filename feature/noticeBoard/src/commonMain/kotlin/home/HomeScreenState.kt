package home

import androidx.paging.ItemSnapshotList

data class HomeScreenState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val emergencyNotice: List<Poster.Emergency> = emptyList(),

    //internal state
    val isEmergencyPosterExpanded: Boolean = false,
    val emergencyAlertClosed: Boolean = false,
    val searchQuery:String = "",
    val scrollToTop: Boolean = false,
)


/*
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
        "City Power Outage Maintenance Notice",
        description = "Scheduled maintenance will cause a temporary power outage in selected areas. Residents are advised to plan accordingly and keep emergency lights charged.",
        date = "6/11/2025",
        distance = "1.2km away",
        time = "12 min ago",
        imageUrlList = listOf(
            "https://picsum.photos/id/40/400/300",
            "https://picsum.photos/id/50/400/300"
        ),
        location = "Gulshan Area",
        type = Type.NORMAL,
        profile = Profile(
            name = "DESCO Authority",
            imageUrl = "https://picsum.photos/id/40/400/300",
            institution = "Dhaka Electric Supply Company",
            designation = "Public Notice"
        ),
        attachments = listOf("Maintenance_Schedule.pdf"),
        isFavorite = true,
        shareCount = 12,
        commentCount = 18,
        likeCount = 87,
        isSaved = false,
        viewCount = 980,
        isExpanded = false,
        liked = Like.IDLE
    ),

    Poster.Normal(
        5,
        "Free Health Checkup Camp Announcement",
        description = "A free health checkup camp including blood pressure, diabetes, and BMI tests will be organized for local residents. Doctors and nurses will be present throughout the day.",
        date = "6/11/2025",
        distance = "3km away",
        time = "25 min ago",
        imageUrlList = listOf(
            "https://picsum.photos/id/60/400/300"
        ),
        location = "Uttara Sector 7",
        type = Type.URGENT,
        profile = Profile(
            name = "Dr. Ayesha Rahman",
            imageUrl = "https://picsum.photos/id/60/400/300",
            institution = "Uttara General Hospital",
            designation = "Medical Officer"
        ),
        attachments = emptyList(),
        isFavorite = false,
        shareCount = 34,
        commentCount = 29,
        likeCount = 156,
        isSaved = false,
        viewCount = 1840,
        isExpanded = false,
        liked = Like.IDLE
    ),

    Poster.Normal(
        6,
        "Road Repair Work Update",
        description = "Repair work on the main road is currently in progress. Traffic movement may be slow during peak hours. Please follow traffic police instructions.",
        date = "6/11/2025",
        distance = "500m away",
        time = "40 min ago",
        imageUrlList = listOf(
            "https://picsum.photos/id/70/400/300",
            "https://picsum.photos/id/80/400/300"
        ),
        location = "Farmgate Area",
        type = Type.NORMAL,
        profile = Profile(
            name = "Dhaka Traffic Police",
            imageUrl = "https://picsum.photos/id/70/400/300",
            institution = "Dhaka Metropolitan Police",
            designation = "Traffic Division"
        ),
        attachments = listOf("Traffic_Notice.pdf"),
        isFavorite = false,
        shareCount = 9,
        commentCount = 14,
        likeCount = 64,
        isSaved = false,
        viewCount = 720,
        isExpanded = false,
        liked = Like.IDLE
    ),

    Poster.Normal(
        7,
        "Job Fair 2025 Registration Open",
        description = "Registration for Job Fair 2025 is now open. Multiple companies will participate offering opportunities for fresh graduates and experienced professionals.",
        date = "7/11/2025",
        distance = "4.5km away",
        time = "1 hour ago",
        imageUrlList = listOf(
            "https://picsum.photos/id/90/400/300",
            "https://picsum.photos/id/100/400/300",
            "https://picsum.photos/id/110/400/300"
        ),
        location = "Bashundhara Convention Center",
        type = Type.NORMAL,
        profile = Profile(
            name = "Career Development Center",
            imageUrl = "https://picsum.photos/id/90/400/300",
            institution = "Bangladesh Youth Council",
            designation = "Organizer"
        ),
        attachments = listOf("Job_Fair_Details.pdf"),
        isFavorite = true,
        shareCount = 56,
        commentCount = 67,
        likeCount = 298,
        isSaved = false,
        viewCount = 3420,
        isExpanded = false,
        liked = Like.IDLE
    ),

    Poster.Normal(
        8,
        "Community Clean-Up Drive",
        description = "A community clean-up drive will be held this Friday morning. Volunteers are requested to join and help keep the neighborhood clean and green.",
        date = "7/11/2025",
        distance = "1.8km away",
        time = "2 hours ago",
        imageUrlList = listOf(
            "https://picsum.photos/id/120/400/300"
        ),
        location = "Mohammadpur Area",
        type = Type.NORMAL,
        profile = Profile(
            name = "Green Dhaka Initiative",
            imageUrl = "https://picsum.photos/id/120/400/300",
            institution = "Local NGO",
            designation = "Community Organizer"
        ),
        attachments = emptyList(),
        isFavorite = false,
        shareCount = 21,
        commentCount = 33,
        likeCount = 145,
        isSaved = false,
        viewCount = 1675,
        isExpanded = false,
        liked = Like.IDLE
    )
)
*/
