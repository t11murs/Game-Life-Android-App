package auth.remote.service

import auth.remote.model.request.LoginRequest
import auth.remote.model.request.RegistrationRequest
import io.ktor.client.statement.HttpResponse

interface HttpService {

    suspend fun login(request: LoginRequest): HttpResponse
    suspend fun register(request: RegistrationRequest): HttpResponse
    suspend fun getSelfProfile(): HttpResponse
    suspend fun register(userId: Long): HttpResponse

    suspend fun addCoins(coinsValue: Int): HttpResponse
    suspend fun getCoins(): HttpResponse
//    suspend fun getPublicationsPage(page: Int, size: Int): HttpResponse
//    suspend fun getPublicationById(publicationId: Long): HttpResponse
//    suspend fun likePublication(publicationId: Long, isLiked: Boolean): HttpResponse
//    suspend fun createPublication(request: PublicationRequest): HttpResponse
}