package ru.netology.nmedia.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.NewPostSharedPrefs
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl

private val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    video = "https://www.youtube.com/watch?v=4L7iXMZk7v0",
    likeCount = 0,
    shareCount = 0,
    impressionCount = 0,
    likedByMe = false
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    // упрощённый вариант
    private val repository: PostRepository = PostRepositoryImpl(
        AppDb.getInstance(application).postDao()
    )
    private val prefsImpl: NewPostSharedPrefs = NewPostSharedPrefs(application)
    val data = repository.getAll()
    val edited = MutableLiveData(empty)
    val draftData = prefsImpl.getDraft()

    fun edit(post: Post) {
        edited.value = post
    }

   fun changeContentAndSave(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value?.let {
            repository.save(it.copy(content = text))
        }
        edited.value = empty
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun addShareByClick(id: Long) = repository.addShareByClick(id)
    fun removeById(id: Long) = repository.removeById(id)

    fun writeDraft(draft:String){
        prefsImpl.syncDraft(draft)
    }

}