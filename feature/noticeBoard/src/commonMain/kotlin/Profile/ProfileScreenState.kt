package Profile

data class ProfileScreenState(
    val user: User,

)

data class User(
    val name: String,
    val imageUrl: String,
)