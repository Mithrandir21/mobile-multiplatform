package com.demo.domain.model.albums


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.demo.remote.api.models.RemoteAlbum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
@Entity(tableName = "album")
data class Album(
    @PrimaryKey
    @SerialName("id")
    val id: Int,
    @SerialName("userId")
    val userId: Int,
    @SerialName("title")
    val title: String
)


/**
 * TODO - Explanation
 */
internal fun RemoteAlbum.toAlbum() = Album(id, userId, title)