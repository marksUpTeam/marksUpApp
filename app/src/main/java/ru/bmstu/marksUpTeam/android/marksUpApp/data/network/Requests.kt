package ru.bmstu.marksUpTeam.android.marksUpApp.data.network

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.bmstu.marksUpTeam.android.marksUpApp.data.network.authorization.AuthorizationRepository


fun testProfileCall(
    coroutineScope: CoroutineScope,
    authorizationRepository: AuthorizationRepository,
    setResult: (Boolean) -> Unit,
    setError: (String, Boolean) -> Unit,
    setLoading: (Boolean) -> Unit,
    context: Context,
    jwt: String
) {
    coroutineScope.launch {
        setLoading(true)
        setError("", false)
        try{
            val response = authorizationRepository.testProfile(jwt)
            setResult(response)
        } catch (e: Exception) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
            setError(e.message ?: "Something went wrong", true)
        } finally {
            setLoading(false)
        }
    }

}

