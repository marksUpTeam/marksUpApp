@file:Suppress("FunctionName")

package ru.bmstu.marksUpTeam.android.marksUpApp.ui.registration


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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import org.koin.androidx.compose.koinViewModel
import ru.bmstu.marksUpTeam.android.marksUpApp.R
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Person
import ru.bmstu.marksUpTeam.android.marksUpApp.data.basePersonTeacher
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.ProfileDomain
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.baseTeacherProfileDomain

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = koinViewModel(),
    navController: NavController,
    context: Context = LocalContext.current,
){
    val state = viewModel.stateFlow.collectAsState()

}

@Composable
private fun RegistrationContent(
    state: RegistrationState,
    onRefresh: () -> Unit,

) {

}


@OptIn(ExperimentalCoilApi::class)
val previewHandler = AsyncImagePreviewHandler {
    ColorImage(Color.Red.toArgb())
}

@OptIn(ExperimentalCoilApi::class)
@Preview
@Composable
private fun RegistrationContentFinished(
    modifier: Modifier = Modifier,
    profile: ProfileDomain = baseTeacherProfileDomain,
    person: Person = basePersonTeacher,
    onClick: (ProfileDomain) -> Unit = {}
){
    Column(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
            AsyncImage(
                model = person.imgUrl,
                contentDescription = "",
                modifier = Modifier.shadow(elevation = 4.dp, shape = CircleShape).height(80.dp).width(80.dp),
            )
        }
        Row(modifier = Modifier.fillMaxWidth().height(20.dp)) {}
        Text(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            text = "${stringResource(R.string.registrationAlmostFinished)}, ${person.name}!",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.readyToSendRegistrationRequest),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
        )
        Row(modifier = Modifier.fillMaxWidth().height(20.dp)) {}
        Button(
            onClick = {onClick(profile)},
            colors = ButtonColors(
                contentColor = MaterialTheme.colorScheme.secondary,
                containerColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = MaterialTheme.colorScheme.onBackground,
                disabledContentColor = MaterialTheme.colorScheme.secondary,
            )
        ) {
            Text(text = stringResource(R.string.continueAuth))
        }
    }
}

@Preview
@Composable
fun InvitationNotFoundErrorScreen(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {},
){
    Column(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon (
            painter = painterResource(R.drawable.cancel),
            contentDescription = "",
            modifier = Modifier.padding(10.dp).size(80.dp),
            tint = Color.Red
        )
        Text (
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            text = stringResource(R.string.notInvitedError),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
        )
        Button (
            onClick = onRefresh,
            colors = ButtonColors(
                contentColor = MaterialTheme.colorScheme.secondary,
                containerColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = MaterialTheme.colorScheme.onBackground,
                disabledContentColor = MaterialTheme.colorScheme.secondary,
            )
        ){
            Text (
                text = stringResource(R.string.ok),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
            )
        }
    }
}

@Preview
@Composable
fun AccountAlreadyExistsErrorScreen(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {},
){
    Column(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon (
            painter = painterResource(R.drawable.no_acc),
            contentDescription = "",
            modifier = Modifier.padding(10.dp).size(80.dp),
            tint = Color.Red
        )
        Text (
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            text = stringResource(R.string.accountAlreadyExists),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
        )
        Button (
            onClick = onRefresh,
            colors = ButtonColors(
                contentColor = MaterialTheme.colorScheme.secondary,
                containerColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = MaterialTheme.colorScheme.onBackground,
                disabledContentColor = MaterialTheme.colorScheme.secondary,
            )
        ){
            Text (
                text = stringResource(R.string.ok),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
            )
        }
    }
}


