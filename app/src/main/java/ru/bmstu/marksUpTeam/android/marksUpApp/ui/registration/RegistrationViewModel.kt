package ru.bmstu.marksUpTeam.android.marksUpApp.ui.registration

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.registration.AccountAlreadyExistsException
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.registration.RegistrationRepository
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.ProfileDomain

class RegistrationViewModel(private val registrationRepository: RegistrationRepository): ViewModel() {
    private val _stateFlow: MutableStateFlow<RegistrationState> = MutableStateFlow(RegistrationState.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    fun getInvite(){
        viewModelScope.launch {
            runCatching {
                _stateFlow.value = RegistrationState.Loading
                val inviteResponse = registrationRepository.getInvite()
                if (inviteResponse.isFailure) {
                    if (inviteResponse.exceptionOrNull() is AccountAlreadyExistsException) {
                        _stateFlow.value = RegistrationState.Error.NotInvitedError
                    }
                    else {
                        val exception = inviteResponse.exceptionOrNull() ?: Exception("Unknown error")
                        _stateFlow.value = RegistrationState.Error.GeneralError(exception.message.toString())
                    }
                }
                else {
                    _stateFlow.value = RegistrationState.Content(inviteResponse.getOrNull() ?: throw IOException("Malformed response"))
                }
            }.onFailure {
                error -> _stateFlow.value = RegistrationState.Error.GeneralError(error.message.toString())
            }
        }
    }

    fun finish(context: Context){
        if (context is Activity){
            context.finish()
        }
    }

    fun registerProfile(profile: ProfileDomain){
        viewModelScope.launch {
            runCatching {
                _stateFlow.value = RegistrationState.Loading
                val registerResponse = registrationRepository.register(profile)
                if (registerResponse.isFailure) {
                    if (registerResponse.exceptionOrNull() is AccountAlreadyExistsException) {
                        _stateFlow.value = RegistrationState.Error.AlreadyExistsError
                    }
                    else {
                        val exception = registerResponse.exceptionOrNull() ?: Exception("Unknown error")
                        _stateFlow.value = RegistrationState.Error.GeneralError(exception.message.toString())
                    }
                }
                else {
                    _stateFlow.value = RegistrationState.Finished(registerResponse.getOrNull() ?: throw IOException("Malformed response"))
                }
            }.onFailure {
                    error -> _stateFlow.value = RegistrationState.Error.GeneralError(error.message.toString())
            }
        }
    }
}