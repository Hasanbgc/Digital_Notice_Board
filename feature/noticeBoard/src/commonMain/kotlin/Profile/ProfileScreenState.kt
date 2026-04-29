package Profile

import com.hasan.dnb.domain.UserSession

data class ProfileScreenState(
    val userSession: UserSession = user,
    val showLogOutDialog: Boolean = false
)

val user = UserSession(
    uid = "",
    displayName = "",
    photoUrl = "",
    contactNumber = "",
)