package ru.bmstu.marksUpTeam.android.marksUpApp.tools

import android.content.Context
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.compose.koinInject
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.bmstu.marksUpTeam.android.marksUpApp.R
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.AuthorizationInterceptor
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


private fun getUnsafeClientBuilder(context: Context): OkHttpClient.Builder {
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

    return OkHttpClient.Builder().hostnameVerifier(unsafeHostnameVerifier).sslSocketFactory(sslContext.socketFactory, trustManager)
}


private fun getUnsafeClient(context: Context): OkHttpClient {
   return getUnsafeClientBuilder(context).build()
}

private fun getUnsafeInterceptedClient(context: Context, authInterceptor: AuthorizationInterceptor): OkHttpClient {
    return getUnsafeClientBuilder(context).addInterceptor(authInterceptor).build()
}

private fun getBasicRetrofitBuilder(api: String): Retrofit.Builder {
    return Retrofit.Builder().baseUrl("https://$api").addConverterFactory(Json {ignoreUnknownKeys = true}
        .asConverterFactory("application/json; charset=UTF-8".toMediaType()))
}

fun getBasicInterceptedRetrofit(context: Context, api: String, authInterceptor: AuthorizationInterceptor): Retrofit {
    return getBasicRetrofitBuilder(api).client(getUnsafeInterceptedClient(context, authInterceptor = authInterceptor)).build()
}

fun getBasicRetrofit(context: Context, api: String): Retrofit {
    return getBasicRetrofitBuilder(api).client(getUnsafeClient(context)).build()
}