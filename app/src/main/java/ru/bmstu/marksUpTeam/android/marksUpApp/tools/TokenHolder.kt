package ru.bmstu.marksUpTeam.android.marksUpApp.tools

import android.content.Context

class TokenHolder(var jwt: String = "") {
    fun updateToken(context: Context) {
        jwt = getJwt(context) ?: ""
    }
    fun clearToken(){
        jwt = ""
    }
}

