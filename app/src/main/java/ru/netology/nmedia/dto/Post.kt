package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likeCount: String,
    val shareCount: String,
    val impressionCount: String,
    val likedByMe: Boolean
)
