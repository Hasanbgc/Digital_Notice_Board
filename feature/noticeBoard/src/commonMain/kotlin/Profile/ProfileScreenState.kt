package Profile

data class ProfileScreenState(
    val user: User = User(),
    val showLogOutDialog: Boolean = false
)

data class User(
    val name: String = "",
    val imageUrl: String = "",
)