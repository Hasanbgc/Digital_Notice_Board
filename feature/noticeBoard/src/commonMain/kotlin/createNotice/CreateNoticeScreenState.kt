package createNotice

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CarRepair
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.CrisisAlert
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.ElectricalServices
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Plumbing
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector

data class CreateNoticeScreenState(
    val currentStep: NoticeCreationStep = NoticeCreationStep.QUICK_PICK_CATEGORY,
    val quickPickCategory: List<Category> = categories,
    val selectedCategory: Category? = null,
    val searchQuery: String = ""
)

data class Category(
    val id:Int,
    val title: String,
    val icon: ImageVector,
    val description:String,
    val parentCategory: ParentCategory? = null
    )
data class ParentCategory(
    val id:Int,
    val label: String,
    val icon: ImageVector,
)

enum class NoticeCreationStep{
    QUICK_PICK_CATEGORY,
    PICK_CATEGORY,
    ADD_NOTICE_BODY,
    PREVIEW
}


val servicesParent = ParentCategory(
    id = 1,
    label = "Services",
    icon = Icons.Default.Build
)

val marketplaceParent = ParentCategory(
    id = 2,
    label = "Marketplace",
    icon = Icons.Default.Store
)

val communityParent = ParentCategory(
    id = 3,
    label = "Community",
    icon = Icons.Default.Groups
)

val emergencyParent = ParentCategory(
    id = 4,
    label = "Emergency",
    icon = Icons.Default.Warning
)


val categories = listOf(

    //common
    Category(
        id = 1,
        title = "Emergency Alert",
        icon = Icons.Default.CrisisAlert,
        description = "Urgent alerts and important emergency notices",
        parentCategory = emergencyParent
    ),
    Category(
        id = 2,
        title = "Health & Medical",
        icon = Icons.Default.LocalHospital,
        description = "Medical help, blood requests, and health services",
        parentCategory = emergencyParent
    ),
    Category(
        id = 3,
        title = "Education & Coaching",
        icon = Icons.Default.School,
        description = "Tuition, coaching centers, and learning services",
        parentCategory = communityParent
    ),
    Category(
        id = 4,
        title = "Job Circular",
        icon = Icons.Default.Work,
        description = "Local job postings and hiring announcements",
        parentCategory = communityParent
    ),

    // 🛍 Marketplace
    Category(
        id = 5,
        title = "Buy & Sell",
        icon = Icons.Default.ShoppingCart,
        description = "Buy or sell new and used products locally",
        parentCategory = marketplaceParent
    ),
    Category(
        id = 6,
        title = "Men’s Fashion",
        icon = Icons.Default.Checkroom,
        description = "Clothing, accessories, and fashion items for men",
        parentCategory = marketplaceParent
    ),
    Category(
        id = 7,
        title = "Electronics",
        icon = Icons.Default.Devices,
        description = "Mobile phones, gadgets, and electronic items",
        parentCategory = marketplaceParent
    ),
    Category(
        id = 8,
        title = "Property Rent",
        icon = Icons.Default.Apartment,
        description = "Houses, flats, and commercial space for rent",
        parentCategory = marketplaceParent
    ),

    // 👥 Community
    Category(
        id = 9,
        title = "Local Events",
        icon = Icons.Default.Event,
        description = "Community programs, fairs, and local events",
        parentCategory = communityParent
    ),
    Category(
        id = 10,
        title = "Lost & Found",
        icon = Icons.Default.Search,
        description = "Report lost or found items in your area",
        parentCategory = communityParent
    ),
    // 🛠 Services
    Category(
        id = 11,
        title = "Car Repair",
        icon = Icons.Default.CarRepair,
        description = "Car servicing and mechanical repair solutions",
        parentCategory = servicesParent
    ),
    Category(
        id = 12,
        title = "Home Cleaning",
        icon = Icons.Default.CleaningServices,
        description = "Professional home and office cleaning services",
        parentCategory = servicesParent
    ),
    Category(
        id = 13,
        title = "Electrician",
        icon = Icons.Default.ElectricalServices,
        description = "Electrical repair, wiring, and maintenance services",
        parentCategory = servicesParent
    ),
    Category(
        id = 14,
        title = "Plumber",
        icon = Icons.Default.Plumbing,
        description = "Pipe fitting, leakage fixing, and plumbing services",
        parentCategory = servicesParent
    ),

    Category(
        id = 15,
        title = "Security Notice",
        icon = Icons.Default.Security,
        description = "Safety warnings and security-related announcements",
        parentCategory = emergencyParent
    )
)


