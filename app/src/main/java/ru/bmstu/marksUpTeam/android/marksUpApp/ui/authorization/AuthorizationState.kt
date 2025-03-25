package ru.bmstu.marksUpTeam.android.marksUpApp.ui.authorization

sealed class AuthorizationState {
    data object Loading : AuthorizationState()
    sealed class Error() : AuthorizationState() {
        data class DefaultError(val message: String) : Error()
        data object VKIDFailed: Error()
        data object AccountNotFound : Error()
    }
    sealed class Content: AuthorizationState() {
        data class Authorized(val jwt: String) : Content()
        data object Idle: Content()
    }
}