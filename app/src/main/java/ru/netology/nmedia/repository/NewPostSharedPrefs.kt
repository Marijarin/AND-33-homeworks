package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NewPostSharedPrefs(context: Context) {
    private val prefs = context.getSharedPreferences("drafts", Context.MODE_PRIVATE)
    private val key = "draft"
    private val draftData = MutableLiveData (" ")

    init{
        prefs.getString(key, null)?.let{
            draftData.value = it
        }
    }

     fun syncDraft(draft: String) {
         draftData.value = draft
         with(prefs.edit()){
            putString(key,draft)
            apply()
        }
    }

    fun getDraft() : LiveData <String> = draftData



}