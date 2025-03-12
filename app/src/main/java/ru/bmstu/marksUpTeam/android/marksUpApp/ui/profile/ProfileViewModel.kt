package ru.bmstu.marksUpTeam.android.marksUpApp.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Profile
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.profile.ProfileRepository


class ProfileViewModel(api: String, jwt: String, context: Context): ViewModel() {
    private val profileRepository = ProfileRepository(api = api, context = context, jwt)
    private val _stateFlow: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    fun updateFlow(){
        viewModelScope.launch {
            try {
                _stateFlow.value = ProfileState.Loading
                val profileResponse = profileRepository.getProfile()
                if (!profileResponse.isSuccessful) {
                    _stateFlow.value = ProfileState.Error(profileResponse.errorBody()!!.string())
                }
                else {
                    val profile = profileResponse.body() ?: throw Exception("Bad response")
                    when {
                        profile.teacher != null -> _stateFlow.value = ProfileState.ContentTeacher(profile)
                        profile.student != null -> _stateFlow.value = ProfileState.ContentParent(profile)
                        profile.parent != null -> _stateFlow.value = ProfileState.ContentParent(profile)
                        else -> throw Exception("Malformed profile")
                    }
                }
            } catch (e: Exception){
                _stateFlow.value = ProfileState.Error(e.message ?: "")
            }
        }
    }

    fun pushProfileChanges(profile: Profile, infoDisplay: (String) -> Unit){
        viewModelScope.launch {
            try {
                val stringResponse = profileRepository.modifyProfile(profile)
                if (stringResponse.isSuccessful){
                    infoDisplay(stringResponse.body() ?: throw Exception("Bad response"))
                }
                else {
                    infoDisplay(stringResponse.errorBody()!!.string())
                }
            } catch (e: Exception){
                infoDisplay(e.message ?: "")
            }
        }
    }

    init {
        updateFlow()
    }

}