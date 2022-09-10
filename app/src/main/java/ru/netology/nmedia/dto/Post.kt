package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 100,
    var shareCount: Int = 499,
    var impressionCount: Int = 999_999,
    var shared: Boolean = false,
    var seen: Boolean = false,
    var likedByMe: Boolean = false
)
