package ru.bmstu.marksUpTeam.android.marksUpApp.ui.registration





sealed class RegistrationState {
    data object Loading : RegistrationState()
    data class Error(val message: String) : RegistrationState()
    sealed class Content : RegistrationState() {
        data object CodeEnter : Content()
        data object DataEnter : Content()
    }
}