package ru.netology.nmedia.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.dto.Post

class PostDaoImpl(private val db: SQLiteDatabase) : PostDao {
    companion object {
        val DDL = """
        CREATE TABLE ${PostColumns.TABLE} (
            ${PostColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${PostColumns.COLUMN_AUTHOR} TEXT NOT NULL,
            ${PostColumns.COLUMN_CONTENT} TEXT NOT NULL,
            ${PostColumns.COLUMN_PUBLISHED} TEXT NOT NULL,
            ${PostColumns.COLUMN_VIDEO} TEXT,
            ${PostColumns.COLUMN_LIKECOUNT} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_SHARECOUNT} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_IMPRESSIONCOUNT} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_LIKED_BY_ME} BOOLEAN NOT NULL DEFAULT 0
        );
        """.trimIndent()
    }

    object PostColumns {
        const val TABLE = "posts"
        const val COLUMN_ID = "id"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_PUBLISHED = "published"
        const val COLUMN_VIDEO = "video"
        const val COLUMN_LIKECOUNT = "likeCount"
        const val COLUMN_SHARECOUNT = "shareCount"
        const val COLUMN_IMPRESSIONCOUNT = "impressionCount"
        const val COLUMN_LIKED_BY_ME = "likedByMe"
        val ALL_COLUMNS = arrayOf(
            COLUMN_ID,
            COLUMN_AUTHOR,
            COLUMN_CONTENT,
            COLUMN_PUBLISHED,
            COLUMN_VIDEO,
            COLUMN_LIKECOUNT,
            COLUMN_SHARECOUNT,
            COLUMN_IMPRESSIONCOUNT,
            COLUMN_LIKED_BY_ME
        )
    }

    override fun getAll(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            null,
            null,
            null,
            null,
            "${PostColumns.COLUMN_ID} DESC"
        )?.use {
            while (it.moveToNext())
                posts.add(map(it))
        }
        return posts
    }
    override fun save(post: Post): Post {
        val values = ContentValues().apply {
            // TODO: remove hardcoded values
            put(PostColumns.COLUMN_AUTHOR, "Me")
            put(PostColumns.COLUMN_CONTENT, post.content)
            put(PostColumns.COLUMN_PUBLISHED, "now")
            put(PostColumns.COLUMN_VIDEO, "https://www.youtube.com/watch?v=4L7iXMZk7v0")
        }
        val id = if (post.id != 0L) {
            db.update(
                PostColumns.TABLE,
                values,
                "${PostColumns.COLUMN_ID} = ?",
                arrayOf(post.id.toString()),
            )
            post.id
        } else {
            db.insert(PostColumns.TABLE, null, values)
        }
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
        ).use {
            it.moveToNext()
            return map(it)
        }
    }

    override fun likeById(id: Long) {
        db.execSQL(
            """
           UPDATE posts SET
               likeCount = likeCount + CASE WHEN likedByMe THEN -1 ELSE 1 END,
               likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }

    override fun removeById(id: Long) {
        db.delete(
            PostColumns.TABLE,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    override fun addShareByClick(id: Long) {
        db.execSQL(
            """
           UPDATE posts SET
           shareCount = shareCount + 1
           WHERE id = ?;
           
         """.trimIndent(), arrayOf(id)
           )
    }

    override fun addImpressionByClick(id: Long) {
        TODO("Not yet implemented")
    }

    private fun map(cursor: Cursor): Post {
        with(cursor) {
            return Post(
                id = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_ID)),
                author = getString(getColumnIndexOrThrow(PostColumns.COLUMN_AUTHOR)),
                content = getString(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT)),
                published = getString(getColumnIndexOrThrow(PostColumns.COLUMN_PUBLISHED)),
                video = getString(getColumnIndexOrThrow(PostColumns.COLUMN_VIDEO)),
                likedByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKED_BY_ME)) != 0,
                likeCount = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKECOUNT)),
                shareCount = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_SHARECOUNT)),
                impressionCount = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_IMPRESSIONCOUNT))
                )
        }
    }
}