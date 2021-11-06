package id.rrdevfundamental.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.rrdevfundamental.data.network.ApiService
import id.rrdevfundamental.data.response.DetailResponse
import id.rrdevfundamental.data.response.SearchResponse
import id.rrdevfundamental.data.response.Status
import id.rrdevfundamental.data.response.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class UserRepository(private val api: ApiService) {

    private val compositeDisposable = CompositeDisposable()

    fun getUserSearch(query: String): LiveData<Status<SearchResponse>> {
        val liveData = MutableLiveData<Status<SearchResponse>>()
        liveData.postValue(Status.start("Start", null))

        compositeDisposable.add(
            api.getSearchUser(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        it?.let {
                            liveData.postValue(Status.success(it))
                            return@subscribeBy
                        }
                        liveData.postValue(Status.error("Error", null))
                    },
                    onError = {
                        liveData.postValue(Status.error("Error : ${it.message}", null))
                    }
                )
        )

        return liveData
    }

    fun getDetail(username: String): LiveData<Status<DetailResponse>> {
        val liveData = MutableLiveData<Status<DetailResponse>>()
        liveData.postValue(Status.start("Start", null))

        compositeDisposable.add(
            api.getUser(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        it?.let {
                            liveData.postValue(Status.success(it))
                            return@subscribeBy
                        }
                        liveData.postValue(Status.error("Error", null))
                    },
                    onError = {
                        liveData.postValue(Status.error("Error : ${it.message}", null))
                    }
                )
        )

        return liveData
    }

    fun getFollowersUser(username: String): LiveData<Status<List<User>>> {
        val liveData = MutableLiveData<Status<List<User>>>()
        liveData.postValue(Status.start("Start", null))

        compositeDisposable.add(
            api.getUserFollowers(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        it?.let {
                            liveData.postValue(Status.success(it))
                            return@subscribeBy
                        }
                        liveData.postValue(Status.error("Error", null))
                    },
                    onError = {
                        liveData.postValue(Status.error("Error : ${it.message}", null))
                    }
                )
        )

        return liveData
    }

    fun getFollowingUser(username: String): LiveData<Status<List<User>>> {
        val liveData = MutableLiveData<Status<List<User>>>()
        liveData.postValue(Status.start("Start", null))

        compositeDisposable.add(
            api.getUserFollowing(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        it?.let {
                            liveData.postValue(Status.success(it))
                            return@subscribeBy
                        }
                        liveData.postValue(Status.error("Error", null))
                    },
                    onError = {
                        liveData.postValue(Status.error("Error : ${it.message}", null))
                    }
                )
        )

        return liveData
    }

    fun disposeComposite() {
        compositeDisposable.dispose()
    }
}