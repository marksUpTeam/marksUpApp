@file:Suppress("FunctionName")

package ru.bmstu.marksUpTeam.android.marksUpApp.ui.authorization

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vk.id.AccessToken
import com.vk.id.VKIDAuthFail
import com.vk.id.auth.AuthCodeData
import com.vk.id.onetap.common.OneTapOAuth
import com.vk.id.onetap.compose.onetap.OneTap
import ru.bmstu.marksUpTeam.android.marksUpApp.R
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.ErrorScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.LoadingScreen
import ru.bmstu.marksUpTeam.android.marksUpApp.ui.theme.MarksUpTheme

val sigmarFont = FontFamily(Font(R.font.sigmar))



@Composable
fun AuthorizationScreen(
    viewModel: AuthorizationViewModel,
    context: Context,
) {
    val state = viewModel.stateFlow.collectAsState()
    AuthorizationContent(
        state = state.value,
        onRefresh = {viewModel.retry()},
        onSuccessfulAuth = {viewModel.profileCall(it)},
        onFailedAuth = {viewModel.vkIdFail()},
        onAuthorizationFinish = {viewModel.finishAuthorization(it, context)},
    )
}

@Composable
private fun AuthorizationContent(
    state: AuthorizationState,
    onRefresh: () -> Unit,
    onSuccessfulAuth: (String) -> Unit,
    onFailedAuth: () -> Unit,
    onAuthorizationFinish: (String) -> Unit,
) {
    MarksUpTheme {
        when (state) {
            is AuthorizationState.Content -> {
                when(state) {
                    is AuthorizationState.Content.AccountNotFound -> {
                        AccountNotFoundScreen(
                            onPress = { onRefresh() },
                            tint = MaterialTheme.colorScheme.secondary,
                            containerColor = MaterialTheme.colorScheme.onBackground,
                            backgroundColor = MaterialTheme.colorScheme.background,
                        )
                    }
                    is AuthorizationState.Content.Authorized -> {
                        AccountFound(
                            onAuthorizationFinish = { onAuthorizationFinish(state.jwt) },
                            backgroundColor = MaterialTheme.colorScheme.background,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                    is AuthorizationState.Content.Idle -> {
                        Authorization(
                            onAuth = {_, token -> run {onSuccessfulAuth(token.idToken ?: "")} },
                            didFail = false,
                            onFail = {_, _ -> run {onFailedAuth()}},
                        )
                    }
                    is AuthorizationState.Content.VKIDFailed -> {
                        Authorization(
                            onAuth = {_, token -> run {onSuccessfulAuth(token.idToken ?: "")} },
                            didFail = true,
                            onFail = {_, _ -> run {onFailedAuth()}},
                        )
                    }
                }

            }
            is AuthorizationState.Error -> {
                ErrorScreen(
                    onRefresh = onRefresh,
                    modifier = Modifier.fillMaxSize(),
                    backgroundColor = MaterialTheme.colorScheme.background,
                    errorMessage = state.message,
                )
            }
            is AuthorizationState.Loading -> {
                LoadingScreen(
                    modifier = Modifier.fillMaxSize(),
                    backgroundColor = MaterialTheme.colorScheme.background,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun AccountFound(
    modifier: Modifier = Modifier,
    onAuthorizationFinish: () -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    tint: Color = MaterialTheme.colorScheme.primary,
){
    Column(modifier = modifier.fillMaxSize().background(backgroundColor), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Icon(
                painter = painterResource(R.drawable.check_circle),
                contentDescription = "",
                tint = tint,
                modifier = Modifier.size(96.dp)
            )
        Row(
            modifier = Modifier.fillMaxWidth().height(10.dp),
        ) {}
        Text(
            text = stringResource(R.string.accFound),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
        )
        Row(
            modifier = Modifier.fillMaxWidth().height(10.dp),
        ) {}
        Button(
            onClick = onAuthorizationFinish,
        ) {
            Text(text = stringResource(R.string.continueAuth))
        }

    }
}


@Preview
@Composable
fun vkAuthBlock(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    onAuth: (OneTapOAuth?, AccessToken) -> Unit = { _, _ -> },
    onAuthCode: (AuthCodeData, Boolean) -> Unit = {_, _ -> },
    onFail: (OneTapOAuth?, VKIDAuthFail) -> Unit = { _, _ -> },
    tint: Color = colorResource(R.color.grey),
) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        OneTap(onAuth = onAuth,
            onAuthCode = onAuthCode,
            onFail = onFail,)
        Text(text = context.getString(R.string.privacyPolicy), modifier = Modifier.fillMaxWidth().padding(10.dp), textAlign = TextAlign.Center, color = tint)
    }
}


@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Authorization(
    modifier: Modifier = Modifier,
    onAuth: (OneTapOAuth?, AccessToken) -> Unit = { _, _ -> },
    tint: Color = colorResource(id = R.color.white),
    iconTint: Color = colorResource(id = R.color.purple_200),
    backgroundColor: Color = colorResource(id = R.color.lighter_black),
    context: Context = LocalContext.current,
    onAuthCode: (AuthCodeData, Boolean) -> Unit = { _, _ -> },
    onFail: (OneTapOAuth?, VKIDAuthFail) -> Unit = { _, _ -> },
    didFail: Boolean = false
){
    Column(
        modifier = modifier.fillMaxSize().background(backgroundColor), horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.app_icon), contentDescription = null, modifier = Modifier.size(60.dp), tint = iconTint)
            Text(text = context.getString(R.string.app_name), modifier = Modifier.padding(5.dp), fontSize = 32.sp, color = iconTint, fontFamily = sigmarFont)
        }
        Column(modifier = Modifier.fillMaxSize().padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            vkAuthBlock(context = context, onAuth = onAuth, onAuthCode = onAuthCode, onFail = onFail)
            if (didFail) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Icon(painter = painterResource(R.drawable.error), contentDescription = null, modifier = Modifier.size(24.dp), tint = colorResource(R.color.red))
                    Text(text = context.getString(R.string.somethingWentWrong), color = colorResource(id = R.color.red), modifier = Modifier.padding(10.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun AccountNotFoundScreen(
    modifier: Modifier = Modifier,
    onPress: () -> Unit = {},
    tint: Color = colorResource(id = R.color.white),
    iconTint: Color = colorResource(id = R.color.red),
    containerColor: Color = colorResource(id = R.color.black),
    backgroundColor: Color = colorResource(id = R.color.lighter_black),
    context: Context = LocalContext.current,
){
    Column(modifier = modifier.fillMaxSize().background(backgroundColor), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Icon(painter = painterResource(R.drawable.no_acc), contentDescription = null, modifier = Modifier.size(60.dp), tint = iconTint)
        Text(text = context.getString(R.string.accNotFound), color = tint, textAlign = TextAlign.Center, modifier = Modifier.padding(10.dp), fontSize = 18.sp)
        Button(onClick = onPress, modifier = Modifier.padding(10.dp), colors = ButtonColors(
            containerColor = containerColor,
            disabledContainerColor = containerColor,
            contentColor = tint,
            disabledContentColor = tint,)){
                Text(text = context.getString(R.string.try_again), color = tint)
        }
    }
}