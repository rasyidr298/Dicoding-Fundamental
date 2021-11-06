package id.rrdevfundamental.ui.home

import androidx.lifecycle.ViewModel
import id.rrdevfundamental.data.repository.UserRepository

class HomeViewModel(
    private val repository: UserRepository
) : ViewModel() {

    fun getUserSearch(keyword: String) = repository.getUserSearch(keyword)
}
