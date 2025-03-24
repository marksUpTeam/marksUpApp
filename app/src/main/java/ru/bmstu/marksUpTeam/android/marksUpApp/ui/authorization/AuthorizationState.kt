package ru.bmstu.marksUpTeam.android.marksUpApp.ui.authorization

sealed class AuthorizationState {
    data object Loading : AuthorizationState()
    data class Error(val message: String) : AuthorizationState()
    sealed class Content: AuthorizationState() {
        data class Authorized(val jwt: String) : Content()
        data object AccountNotFound : Content()
        data object Idle: Content()
        data object VKIDFailed: Content()
    }
}