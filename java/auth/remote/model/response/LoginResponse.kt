package auth.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
class LoginResponse(
    val token: String = ""
)
