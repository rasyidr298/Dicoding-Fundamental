package id.rrdevfundamental.ui.detail

import androidx.lifecycle.ViewModel
import id.rrdevfundamental.data.repository.UserRepository

class DetailViewModel(
    private val repository: UserRepository
) : ViewModel() {

    fun getFollowers(username: String) = repository.getFollowersUser(username)

    fun getFollowing(username: String) = repository.getFollowingUser(username)

    fun getDetail(username: String) = repository.getDetail(username)
}