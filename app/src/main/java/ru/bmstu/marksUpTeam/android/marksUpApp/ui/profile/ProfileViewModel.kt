package ru.bmstu.marksUpTeam.android.marksUpApp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Parent
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Profile
import ru.bmstu.marksUpTeam.android.marksUpApp.data.Student
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.PersonType
import ru.bmstu.marksUpTeam.android.marksUpApp.domain.ProfileDomain
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.profile.ProfileMapper
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.profile.ProfileRepository


class ProfileViewModel(private val profileRepository: ProfileRepository): ViewModel() {
    private val _stateFlow: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    fun updateFlow(){
        viewModelScope.launch {
            runCatching {
                _stateFlow.value = ProfileState.Loading
                val profileResponse = profileRepository.getProfileDomain()
                if (profileResponse.isFailure) {
                    _stateFlow.value = ProfileState.Error(profileResponse.exceptionOrNull()?.message ?: "Something went wrong")
                }
                else {
                    val profileDomain = profileResponse.getOrNull() ?: throw Exception("Bad response")
                    when (profileDomain.personType) {
                        is PersonType.TeacherType -> _stateFlow.value = ProfileState.ContentTeacher(profileDomain)
                        is PersonType.StudentType -> _stateFlow.value = ProfileState.ContentStudent(profileDomain)
                        is PersonType.ParentType -> _stateFlow.value = ProfileState.ContentParent(profileDomain)
                    }
                }
            }.onFailure {error -> _stateFlow.value = ProfileState.Error(error.message.orEmpty())}
        }
    }

    fun pushProfileChanges(profileDomain: ProfileDomain, infoDisplay: (String) -> Unit){
        viewModelScope.launch {
            runCatching {
                val stringResponse = profileRepository.modifyProfileDomain(profileDomain)
                if (stringResponse.isSuccess){
                    infoDisplay(stringResponse.getOrNull() ?: throw Exception("Bad response"))
                }
                else {
                    infoDisplay(stringResponse.exceptionOrNull()?.message ?: "Something went wrong")
                }
            }.onFailure {error -> infoDisplay(error.message.orEmpty())}
            updateFlow()
        }
    }

    fun pushCurrentStudentChange(student: Student, infoDisplay: (String) -> Unit = {}){
        val newProfile = formNewParentProfileDomain(student) ?: return
        pushProfileChanges(newProfile, infoDisplay)
    }

    init {
        updateFlow()
    }

    private fun formNewParentProfileDomain(student: Student): ProfileDomain? {
        if (stateFlow.value is ProfileState.ContentParent) {
            val profileDomain = (stateFlow.value as ProfileState.ContentParent).profile
            val personType = ((stateFlow.value as ProfileState.ContentParent).profile.personType as PersonType.ParentType)
            val studentList = personType.parent.children
            if (studentList.contains(student) == false) {
                return null
            }
            else {
                personType.parent.currentChild = student
                return ProfileDomain(
                    id = profileDomain.id,
                    username = profileDomain.username,
                    personType = personType,
                )
            }
        } else {
            return null
        }
    }

    @Deprecated("Data layer models usage")
    private fun formNewParentProfile(student: Student): Profile? {

        if (stateFlow.value is ProfileState.ContentParent){
            val profile = ProfileMapper().toDto((stateFlow.value as ProfileState.ContentParent).profile)
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