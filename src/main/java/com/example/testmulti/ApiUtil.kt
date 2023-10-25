package com.example.testmulti


import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import APIService
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import java.util.regex.Pattern
import com.example.testmulti.TokenResponse
import com.example.testmulti.Credentials
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.withTimeout
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import java.util.concurrent.TimeUnit


object ApiUtil {
    fun imageApi(ip: String, token: String?, img: ImageView){
        Log.i(ip,ip)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        // Create OkHttpClient with the interceptor
        val client = OkHttpClient.Builder()
            .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))

            .addInterceptor(logging)
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    var response = chain.proceed(chain.request())
                    var tryCount = 0
                    while (!response.isSuccessful && tryCount < 3) {
                        tryCount++
                        response = chain.proceed(chain.request())
                    }
                    return response
                }
            })
            .readTimeout(300,TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
            .build()

        // Create Retrofit with Gson Converter
        val retrofit = Retrofit.Builder()
            .baseUrl("http://$ip:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)
        val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
            throwable.printStackTrace()
        }
        try {
            CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                withTimeout(10000L){
                    val imageResponse = service.cameraToken("Bearer $token")
                    Log.i("monkeyyyyyyyy", "test")
                    if (imageResponse.isSuccessful){
                        val inputStream = imageResponse.body()?.byteStream()
                        val bitmap =  BitmapFactory.decodeStream(inputStream)
                        withContext(Dispatchers.Main){
                            img.setImageBitmap(bitmap)
                        }
                    }
                }

            }
        }catch (e: CancellationException){
            Log.e("API_ERROR", "Shit went south")
        }

    }

    fun api(path: String, ip: String, username: String? = null, password: String? = null, token: String? = null) {
        // Initialize logging interceptor

        Log.i(ip,ip)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        // Create OkHttpClient with the interceptor
        val client = OkHttpClient.Builder()
            .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))

            .addInterceptor(logging)
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    var response = chain.proceed(chain.request())
                    var tryCount = 0
                    while (!response.isSuccessful && tryCount < 3) {
                        tryCount++
                        response = chain.proceed(chain.request())
                    }
                    return response
                }
            })
            .readTimeout(300,TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
            .build()

        // Create Retrofit with Gson Converter
        val retrofit = Retrofit.Builder()
            .baseUrl("http://$ip:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)



        // Make API request using Coroutines
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = when {
                    username != null && password != null -> {
                        service.listRepos(path, Credentials(Global.username, Global.password))
                    }
                    token != null -> {
                        service.listReposWithToken(path, "Bearer $token")
                    }
                    else -> throw IllegalArgumentException("Either username/password or token must be provided!")
                }
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if ( username != null && password != null){
                        Global.jwt = responseBody?.getAsJsonPrimitive("token")?.asString

                        Log.i("API_RESPONSE", "Response: ${Global.jwt}")
                    }else {
                        Log.i("API_RESPONSE", "Response: $responseBody")
                    }


                }
                withContext(Dispatchers.Main) {
                    // ... [rest of the response handling code remains unchanged]
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("API_ERROR", "Exception: ${e.message}", e)
                }
            }
        }
    }

    fun isValidIPAddress(ip: String): Boolean {
        val ipPattern: Pattern = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"
        )
        return ipPattern.matcher(ip).matches()
    }
}