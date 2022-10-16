package ru.netology.nmedia.dao

import ru.netology.nmedia.dto.Post

interface PostDao {
        fun getAll(): List<Post>
        fun save (post: Post): Post
        fun likeById(id: Long)
        fun addShareByClick(id: Long)
        fun addImpressionByClick(id: Long)
        fun removeById(id:Long)

}