package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson

class NewPostSharedPrefs(context: Context) {
    private val gson = Gson()
    private val prefs = context.getSharedPreferences("drafts", Context.MODE_PRIVATE)
    private val key = "draft"
    private var draft = ""
    private val draftData = MutableLiveData(draft)

    init{
        prefs.getString(key, null)?.let {
            draft = gson.fromJson(it, String::class.java)
            draftData.value = draft
        }?: kotlin.run {
            draft = " "
        }
    }

     fun syncDraft(dr: String) {
        draft = dr
        with(prefs.edit()){
            putString(key,gson.toJson(draft))
            apply()
        }
    }

    fun getDraft(): LiveData <String> = draftData


}