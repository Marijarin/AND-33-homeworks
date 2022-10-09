package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val video: String?,
    val likeCount: Int,
    val shareCount: Int,
    val impressionCount: Int,
    val likedByMe: Boolean,
    val isClicked: Boolean
)
