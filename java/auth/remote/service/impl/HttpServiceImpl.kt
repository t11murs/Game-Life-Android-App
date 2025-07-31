package auth.remote.service.impl

import android.content.Context
import auth.remote.model.request.LoginRequest
import auth.remote.model.request.RegistrationRequest
import auth.remote.service.HttpService
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders

class  HttpServiceImpl(
    private val client: HttpClient,
    private val context: Context
) : HttpService {

    // ---------------- AUTH --------------------
    override suspend fun login(request: LoginRequest) = client.post("$BASE_URL_PUBLIC/auth/login") {
        addContentTypeHeader()
        setBody(request)
    }

    override suspend fun register(request: RegistrationRequest) =
        client.post("$BASE_URL_PUBLIC/auth/registration") {
            addContentTypeHeader()
            setBody(request)
        }

    // ---------------- USER --------------------

    override suspend fun getSelfProfile() = client.get("$BASE_URL/user/me") { addJwt() }

    override suspend fun register(userId: Long) =
        client.get("$BASE_URL/user?userId=$userId") { addJwt() }

    override suspend fun addCoins(coinsValue: Int) = client.get("$BASE_URL/user/addCoins/$coinsValue") { addJwt() }

    override suspend fun getCoins() = client.get("$BASE_URL/user/getCoins") { addJwt() }

    companion object {
        private const val BASE_URL = "http://192.168.1.80:8080/api/v1"
//        private const val BASE_URL = "http://10.209.206.17:8080/api/v1"
        private const val BASE_URL_PUBLIC = "$BASE_URL/public"
    }

    private fun HttpRequestBuilder.addContentTypeHeader() {
        headers {
            append(HttpHeaders.ContentType, "application/json")
        }
    }

    private fun HttpRequestBuilder.addJwt() {
        addContentTypeHeader()
        val jwt = context.getSharedPreferences("JWT", Context.MODE_PRIVATE).getString("JWT", null) ?: return
        headers {
            append(HttpHeaders.Authorization, "Bearer $jwt")
        }
    }
}