package id.rrdevfundamental.data.model

import java.io.Serializable

data class User(
    val id: Int,
    val username: String,
    val name: String,
    val avatar: String,
    val company: String,
    val location: String,
    val repository: Int,
    val follower: Int,
    val following: Int
): Serializable