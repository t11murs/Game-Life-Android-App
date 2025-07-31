package auth.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
class RegistrationRequest(
    val email: String,
    val password: String,
    val username: String,
)