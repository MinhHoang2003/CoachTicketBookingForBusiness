package com.example.coachticketbookingforbusiness.utils

import android.content.Context
import com.example.coachticketbookingforbusiness.model.LocalPosition
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single

class FileManager(val context: Context) : IFileManager {

    companion object {
        var INSTANCE: IFileManager? = null

        fun getInstance(context: Context): IFileManager = INSTANCE ?: synchronized(this) {
            INSTANCE ?: FileManager(context).also { INSTANCE = it }
        }
    }


    override fun readPositionLocal(): Single<LocalPosition> = Single.create { emitter ->
        try {
            val content = context.assets.open("position.json").bufferedReader().use {
                it.readText()
            }
            val data = Gson().fromJson(content, LocalPosition::class.java)
            emitter.onSuccess(data)
        } catch (e: Exception) {
            emitter.onError(Throwable(e.message))
        }
    }

}