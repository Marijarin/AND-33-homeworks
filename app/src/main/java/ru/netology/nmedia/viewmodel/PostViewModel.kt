package ru.netology.nmedia.viewmodel



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

private val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    video = null,
    likeCount = 0,
    shareCount = 0,
    impressionCount = 0,
    likedByMe = false
)

class PostViewModel : ViewModel() {
    // упрощённый вариант
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

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
    fun addImpressionByClick(id: Long) = repository.addImpressionByClick(id)
    fun removeById(id: Long) = repository.removeById(id)
}