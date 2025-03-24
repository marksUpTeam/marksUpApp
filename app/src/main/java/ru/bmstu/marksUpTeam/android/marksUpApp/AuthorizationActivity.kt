package ru.bmstu.marksUpTeam.android.marksUpApp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.vk.id.AccessToken
import com.vk.id.onetap.common.OneTapOAuth
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.authorization.AuthorizationRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.authorization.testProfileCall
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getEncryptedSharedPreferences
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.AccountNotFoundScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.Authorization
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.ErrorScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.LoadingScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.theme.MarksUpTheme

class AuthorizationActivity: ComponentActivity() {
    private val authorizationRepository by lazy { AuthorizationRepository(BuildConfig.API_URL, this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println(BuildConfig.API_URL)
        setContent {
            MarksUpTheme(){
            val coroutineScope = rememberCoroutineScope()
            var didFail by rememberSaveable { mutableStateOf(false) }
            var loading by rememberSaveable { mutableStateOf(false) }
            var error by rememberSaveable { mutableStateOf(false) }
            var profileExists by rememberSaveable { mutableStateOf(0) }
            var errorMsg by rememberSaveable { mutableStateOf("") }
            if (profileExists == 1) {
                val prefs = getEncryptedSharedPreferences(this)
                prefs.edit().putBoolean("is_logged_in", true).apply()
                finish()
            }
            when {
                profileExists == 2 -> {
                    AccountNotFoundScreen(onPress = {profileExists = 0;}, tint = MaterialTheme.colorScheme.secondary,
                        containerColor = MaterialTheme.colorScheme.onBackground,
                        backgroundColor = MaterialTheme.colorScheme.background)
                }
                error -> {
                    ErrorScreen(onRefresh = {
                        error = false
                    }, modifier = Modifier.fillMaxSize(), errorMessage = errorMsg, backgroundColor = MaterialTheme.colorScheme.background, tint = MaterialTheme.colorScheme.secondary)
                }
                loading -> {
                    LoadingScreen(modifier = Modifier.fillMaxSize(), backgroundColor = MaterialTheme.colorScheme.background, tint = MaterialTheme.colorScheme.secondary)
                }
               else -> Authorization(
                    onAuth = { oauth, token -> run {testProfileCall(
                        coroutineScope,
                        authorizationRepository,
                        setResult = {if (it) {
                            profileExists = 1
                        } else {
                            profileExists = 2
                        } },
                        setError = {errorMsgThis, stat -> run {error = stat; errorMsg = errorMsgThis}},
                        setLoading = {loading = it},
                        context = this,
                        jwt = token.idToken ?: "") }; processJWT(oauth, token, this) },
                    didFail = didFail,
                    onFail = { _, authFail -> run { didFail = true; println(authFail) } }, backgroundColor = MaterialTheme.colorScheme.background, iconTint = MaterialTheme.colorScheme.primary,)
            }
        }
        }
    }
}

fun processJWT(oauth: OneTapOAuth?, token: AccessToken, context: Context) {
    val jwtString = token.idToken
    val prefs = getEncryptedSharedPreferences(context)
    println(jwtString)
    prefs.edit().putString("jwt", jwtString).apply()
}

