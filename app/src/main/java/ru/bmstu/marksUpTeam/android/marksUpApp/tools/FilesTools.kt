package ru.bmstu.marksUpTeam.android.marksUpApp.tools

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

fun getFileForRequest(fileUri: Uri, contentResolver: ContentResolver): MultipartBody.Part? {
    val inputStream = contentResolver.openInputStream(fileUri)
    if (inputStream != null) {
        val fileName = getFileName(fileUri, contentResolver) ?: "file"
        Log.d("files",fileName)
        val requestBody =
            inputStream.readBytes()
                .toRequestBody("application/octet-stream".toMediaTypeOrNull())
        val file = MultipartBody.Part.createFormData("file", fileName, requestBody)
        Log.d("files", fileName)
        return file
    }
    return null
}

fun getFileName(uri: Uri, contentResolver: ContentResolver): String? {
    val returnCursor = contentResolver.query(uri, null, null, null, null)
    returnCursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        it.moveToFirst()
        return it.getString(nameIndex)
    }
    return null
}
fun getFileUriByName(context: Context, fileName: String): Uri?{
    val downloadsUri = MediaStore.Files.getContentUri("external")
    val projection = arrayOf(MediaStore.MediaColumns._ID)
    val selection = "${MediaStore.MediaColumns.DISPLAY_NAME} = ?"
    val selectionArgs = arrayOf(fileName)

    context.contentResolver.query(
        downloadsUri,
        projection,
        selection,
        selectionArgs,
        null
    )?.use { cursor ->
        if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
            return ContentUris.withAppendedId(downloadsUri, id)
        }
    }

    return null
}
