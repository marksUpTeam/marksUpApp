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
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.processJWT
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.authorization.AccountNotFoundScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.authorization.Authorization
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.ErrorScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.LoadingScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.authorization.AuthorizationScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.authorization.AuthorizationViewModel
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.theme.MarksUpTheme

class AuthorizationActivity: ComponentActivity() {
    private val authorizationViewModel: AuthorizationViewModel by lazy { AuthorizationViewModel(BuildConfig.API_URL, this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println(BuildConfig.API_URL)
        setContent {
            AuthorizationScreen(authorizationViewModel, this)
        }
    }
}



