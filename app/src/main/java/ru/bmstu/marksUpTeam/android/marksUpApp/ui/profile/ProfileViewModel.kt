package ru.bmstu.marksUpTeam.android.marksUpApp.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Parent
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Profile
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student
import ru.bmstu.marksUpTeam.android.marksUpApp.data.domain.PersonType
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.profile.ProfileMapper
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.profile.ProfileRepository


class ProfileViewModel(api: String, jwt: String, context: Context): ViewModel() {
    private val profileRepository = ProfileRepository(api = api, context = context, jwt)
    private val _stateFlow: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    fun updateFlow(){
        viewModelScope.launch {
            runCatching {
                _stateFlow.value = ProfileState.Loading
                val profileResponse = profileRepository.getProfile()
                if (!profileResponse.isSuccessful) {
                    _stateFlow.value = ProfileState.Error(profileResponse.errorBody()!!.string())
                }
                else {
                    val profile = profileResponse.body() ?: throw Exception("Bad response")
                    val profileDomain = ProfileMapper().map(profile)
                    when (profileDomain.personType) {
                        is PersonType.TeacherType -> _stateFlow.value = ProfileState.ContentTeacher(profileDomain)
                        is PersonType.StudentType -> _stateFlow.value = ProfileState.ContentStudent(profileDomain)
                        is PersonType.ParentType -> _stateFlow.value = ProfileState.ContentParent(profileDomain)
                    }
                }
            }.onFailure {error -> _stateFlow.value = ProfileState.Error(error.message.orEmpty())}
        }
    }

    fun pushProfileChanges(profile: Profile, infoDisplay: (String) -> Unit){
        viewModelScope.launch {
            runCatching {
                val stringResponse = profileRepository.modifyProfile(profile)
                if (stringResponse.isSuccessful){
                    infoDisplay(stringResponse.body() ?: throw Exception("Bad response"))
                }
                else {
                    infoDisplay(stringResponse.errorBody()!!.string())
                }
            }.onFailure {error -> infoDisplay(error.message.orEmpty())}
            updateFlow()
        }
    }

    fun pushCurrentStudentChange(student: Student, infoDisplay: (String) -> Unit = {}){
        val newProfile = formNewProfile(student) ?: return
        pushProfileChanges(newProfile, infoDisplay)
    }

    init {
        updateFlow()
    }

    private fun formNewProfile(student: Student): Profile? {

        if (stateFlow.value is ProfileState.ContentParent){
            val profile = ProfileMapper().demap((stateFlow.value as ProfileState.ContentParent).profile)
            val studentList = profile.parent?.children ?: return null
            if (studentList.contains(student) == false){
                return null
            }
            val newParent = Parent(
                id = profile.parent.id,
                person = profile.parent.person,
                children = profile.parent.children,
                currentChild = student,
            )
            return Profile(
                id = profile.id,
                username = profile.username,
                parent = newParent,
            )
        }
        return null
    }

}