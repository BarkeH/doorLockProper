

import com.example.testmulti.TokenResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import com.example.testmulti.Credentials
import com.google.gson.JsonObject

interface APIService {
    // ...
    @Headers("Content-Type: application/json")
    @POST("/api/{train}")
    suspend fun listRepos(@Path("train") train: String?, @Body credentials: Credentials): Response<JsonObject>

    @POST("/api/{train}")
    suspend fun listReposWithToken(
        @Path("train") train: String?,
        @Header("Authorization") token: String
    ): Response<JsonObject>

    @POST("/api/camera")
    suspend fun cameraToken(

        @Header("Authorization") token: String
    ): Response<ResponseBody>
    // ...
}

