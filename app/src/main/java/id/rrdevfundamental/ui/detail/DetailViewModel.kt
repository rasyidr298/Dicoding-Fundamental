package id.rrdevfundamental.ui.detail

import androidx.lifecycle.ViewModel
import id.rrdevfundamental.data.repository.UserRepository
import id.rrdevfundamental.data.network.response.DetailResponse
import id.rrdevfundamental.utils.toUserEntity

class DetailViewModel(
    private val repository: UserRepository
) : ViewModel() {

    fun getFollowers(username: String) = repository.getFollowersUser(username)

    fun getFollowing(username: String) = repository.getFollowingUser(username)

    fun getDetail(username: String) = repository.getDetail(username)

    fun insertDb(detailUser: DetailResponse) = repository.insertUserDb(detailUser)

    fun isSaved(login: String) = repository.checkUserDb(login)

    fun deleteUserDb(detailUser: DetailResponse) = repository.deleteUserDb(detailUser.toUserEntity())
}