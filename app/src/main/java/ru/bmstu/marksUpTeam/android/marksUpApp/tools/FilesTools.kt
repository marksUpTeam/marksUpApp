package ru.bmstu.marksUpTeam.android.marksUpApp.tools

import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream

fun getFileForRequest(fileUri: Uri, contentResolver: ContentResolver): MultipartBody.Part? {
    val inputStream = contentResolver.openInputStream(fileUri)
    if (inputStream != null) {
        val fileName = getFileName(fileUri, contentResolver) ?: "file"
        Log.d("files", fileName)
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

fun getFileUriByName(context: Context, fileName: String): Uri? {
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

fun saveFileToMediaStore(
    context: Context,
    fileName: String,
    inputStream: InputStream
):Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.Downloads.DISPLAY_NAME, fileName)
        put(MediaStore.Downloads.IS_PENDING, 1)
    }

    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

    if (uri != null) {
        resolver.openOutputStream(uri)?.use { outputStream ->
            inputStream.copyTo(outputStream)
        }

        contentValues.clear()
        contentValues.put(MediaStore.Downloads.IS_PENDING, 0)
        resolver.update(uri, contentValues, null, null)
    } else {
        Log.e("SaveFile", "Failed to insert file into MediaStore")
    }
    return uri
}

fun openFile(context: Context, uri: Uri) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, context.contentResolver.getType(uri))
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "Нет приложения для открытия файла", Toast.LENGTH_SHORT).show()
    }
}
