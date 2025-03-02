package ru.bmstu.marksUpTeam.android.marksUpApp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.vk.id.AccessToken
import com.vk.id.onetap.common.OneTapOAuth
import kotlinx.coroutines.CoroutineScope
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.BackendRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.testProfileCall
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.getEncryptedSharedPreferences
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.AccountNotFoundScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.Authorization
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.ErrorScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.LoadingScreen

class AuthorizationActivity: ComponentActivity() {
    private val backendRepository by lazy { BackendRepository(BuildConfig.API_URL) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val coroutineScope = rememberCoroutineScope()
            var didFail by rememberSaveable { mutableStateOf(false) }
            var loading by rememberSaveable { mutableStateOf(false) }
            var error by rememberSaveable { mutableStateOf(false) }
            var profileExists by rememberSaveable { mutableStateOf(0) }
            if (profileExists == 1) {
                finish()
            }
            when {
                profileExists == 2 -> {
                    AccountNotFoundScreen(onPress = {profileExists = 0; })
                }
                error -> {
                    ErrorScreen(onRefresh = {
                        error = false
                    })
                }
                loading -> {
                    LoadingScreen()
                }
               else -> Authorization(
                    onAuth = { oauth, token -> run { processJWT(oauth, token, this); testProfileCall(
                        coroutineScope,
                        backendRepository,
                        setResult = {if (it) {
                            profileExists = 1
                        } else {
                            profileExists = 2
                        } },
                        setError = {_, stat -> {error = stat}},
                        setLoading = {loading = it},
                        context = this,
                        jwt = token.idToken ?: "") } },
                    didFail = didFail,
                    onFail = { _, _ -> run { didFail = true } })
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

