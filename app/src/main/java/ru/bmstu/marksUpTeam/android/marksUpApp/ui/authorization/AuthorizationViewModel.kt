package ru.bmstu.marksUpTeam.android.marksUpApp.ui.authorization

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.authorization.AuthorizationRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.tools.processJWT

class AuthorizationViewModel(private val authorizationRepository: AuthorizationRepository): ViewModel() {
    private val _stateFlow: MutableStateFlow<AuthorizationState> = MutableStateFlow(AuthorizationState.Content.Idle)
    val stateFlow = _stateFlow.asStateFlow()

    fun profileCall(jwt: String){
        if (jwt.isEmpty()) {
            _stateFlow.value = AuthorizationState.Error.DefaultError("")
        }
        viewModelScope.launch {
            runCatching {
                _stateFlow.value = AuthorizationState.Loading
                val response = authorizationRepository.testProfile(jwt)
                if (response) {
                    _stateFlow.value = AuthorizationState.Content.Authorized(jwt)
                } else {
                    _stateFlow.value = AuthorizationState.Error.AccountNotFound(jwt)
                }
            }.onFailure {
                _stateFlow.value = AuthorizationState.Error.DefaultError(it.message.toString())
            }
        }
    }
    fun callRegistration(context: Context, jwt: String){
        processJWT(jwt, context)
        //TODO: call registration activity here
    }
    fun vkIdFail(){
        _stateFlow.value = AuthorizationState.Error.VKIDFailed
    }

    fun finishAuthorization(jwt: String, context: Context){
        processJWT(jwt, context)
    }

    fun retry(){
        _stateFlow.value = AuthorizationState.Content.Idle
    }


}

