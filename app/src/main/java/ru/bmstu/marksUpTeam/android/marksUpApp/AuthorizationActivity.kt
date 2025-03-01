package ru.bmstu.marksUpTeam.android.marksUpApp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.vk.id.AccessToken
import com.vk.id.onetap.common.OneTapOAuth
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getEncryptedSharedPreferences
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.Authorization

class AuthorizationActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var didFail by rememberSaveable { mutableStateOf(false) }
            Authorization(onAuth = {oauth, token -> run {saveJWT(oauth, token, this)}}, didFail = didFail, onFail = {_, _ -> run {didFail = true}})
        }
    }
}

fun saveJWT(oauth: OneTapOAuth?, token: AccessToken, context: Context){
    val jwtString = token.idToken
    val prefs = getEncryptedSharedPreferences(context)
    prefs.edit().putString("jwt", jwtString).apply()
    prefs.edit().putBoolean("is_logged_in", true).apply()
}

