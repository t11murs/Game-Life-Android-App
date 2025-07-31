package auth.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
open class UserResponse(
    val id: Long = -1,
    val email: String,
    val username: String,
    val coins: Int,
)