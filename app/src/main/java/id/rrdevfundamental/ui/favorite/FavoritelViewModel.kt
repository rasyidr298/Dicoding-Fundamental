package id.rrdevfundamental.ui.favorite

import androidx.lifecycle.ViewModel
import id.rrdevfundamental.data.db.entitiy.UserEntity
import id.rrdevfundamental.data.network.response.DetailResponse
import id.rrdevfundamental.data.repository.UserRepository

class FavoritelViewModel(
    private val repository: UserRepository
) : ViewModel() {

    fun getUserAllDb() = repository.getUserAllDb()
    fun deleteUserDb(detailUser: UserEntity) = repository.deleteUserDb(detailUser)

}