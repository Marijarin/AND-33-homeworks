package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val video: String?,
    val likeCount: Int = 0,
    val shareCount: Int,
    val impressionCount: Int,
    val likedByMe: Boolean
) {
    fun toDto() = Post(
        id,
        author,
        content,
        published,
        video,
        likeCount,
        shareCount,
        impressionCount,
        likedByMe)

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(
                dto.id,
                dto.author,
                dto.content,
                dto.published,
                dto.video,
                dto.likeCount,
                dto.shareCount,
                dto.impressionCount,
                dto.likedByMe)

    }
}
