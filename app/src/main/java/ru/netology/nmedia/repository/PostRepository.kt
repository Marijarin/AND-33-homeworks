package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun save (post: Post)
    fun likeById(id: Long)
    fun addLikeToLiked(post: Post): Int
    fun addShareByClick(id: Long)
    fun addImpressionByClick(id: Long)
    fun removeById(id:Long)
}