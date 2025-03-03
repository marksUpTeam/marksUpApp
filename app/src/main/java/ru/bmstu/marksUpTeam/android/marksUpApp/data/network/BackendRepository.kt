package ru.bmstu.marksUpTeam.android.marksUpApp.data.network

import android.content.Context
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.bmstu.marksUpTeam.android.marksUpApp.R
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class BackendRepository(api: String, private val context: Context) {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://$api").client(getUnsafeClient())
        .addConverterFactory(Json {ignoreUnknownKeys = true}
            .asConverterFactory("application/json; charset=UTF-8".toMediaType()))
        .build()

    private val backendApi = retrofit.create(BackendApi::class.java)

    private fun getUnsafeClient(): OkHttpClient {
        val certInputStream: InputStream = context.resources.openRawResource(R.raw.naburnm8)

        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(null, null)
        val cert = CertificateFactory.getInstance("X.509").generateCertificate(certInputStream)
        keyStore.setCertificateEntry("naburnm8MarskUp", cert)

        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)
        val trustManagers = trustManagerFactory.trustManagers
        val trustManager = trustManagers[0] as X509TrustManager

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf(trustManager), null)

        val unsafeHostnameVerifier = HostnameVerifier { _, _ -> true }

        return OkHttpClient.Builder().hostnameVerifier(unsafeHostnameVerifier).sslSocketFactory(sslContext.socketFactory, trustManager).build()
    }

    suspend fun testProfile(jwt: String): Boolean{
        val response = backendApi.testProfile("Bearer $jwt")
        return response.isSuccessful
    }
}