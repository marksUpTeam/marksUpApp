package ru.bmstu.marksUpTeam.android.marksUpApp.data.network.authorization

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@Deprecated("Implemented in AuthorizationViewModel")
fun testProfileCall(
    coroutineScope: CoroutineScope,
    authorizationRepository: AuthorizationRepository,
    setResult: (Boolean) -> Unit,
    setError: (String, Boolean) -> Unit,
    setLoading: (Boolean) -> Unit,
    jwt: String
) {
    coroutineScope.launch {
        setLoading(true)
        setError("", false)
        try{
            val response = authorizationRepository.testProfile(jwt)
            setResult(response)
        } catch (e: Exception) {
            setError(e.message ?: "Something went wrong", true)
        } finally {
            setLoading(false)
        }
    }

}

