package auth.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
class CoinsRequest (
    val coinsValue: Int
)