package ru.bmstu.marksUpTeam.android.marksUpApp.tools

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.vk.id.AccessToken


fun getEncryptedSharedPreferences(context: Context): EncryptedSharedPreferences {
    val masterKey = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
    return EncryptedSharedPreferences.create(context, "user_data", masterKey, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM) as EncryptedSharedPreferences
}

fun getJwt(context: Context): String?{
    val prefs = getEncryptedSharedPreferences(context)
    val jwt = prefs.getString("jwt", null)
    return jwt
}

fun processJWT(token: String, context: Context) {
    val jwtString = token
    val prefs = getEncryptedSharedPreferences(context)
    prefs.edit().putString("jwt", jwtString).apply()
}

fun invalidateSession(context: Context){}