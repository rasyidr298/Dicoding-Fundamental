package id.rrdevfundamental.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.rrdevfundamental.data.network.ApiService
import id.rrdevfundamental.data.db.dao.UserDao
import id.rrdevfundamental.data.db.entitiy.UserEntity
import id.rrdevfundamental.data.network.response.DetailResponse
import id.rrdevfundamental.data.network.response.SearchResponse
import id.rrdevfundamental.data.network.response.Status
import id.rrdevfundamental.data.network.response.User
import id.rrdevfundamental.utils.toUserDetail
import id.rrdevfundamental.utils.toUserEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class UserRepository(
    private val api: ApiService,
    private val dao: UserDao
    ) {

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

    fun insertUserDb(detailUser: DetailResponse) {
        compositeDisposable.add(Observable.fromCallable{
            dao.insertUser(detailUser.toUserEntity())
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }

    fun getUserAllDb(): LiveData<List<UserEntity>> {
        val liveData = MutableLiveData<List<UserEntity>>()

        compositeDisposable.add(Observable.fromCallable{
            dao.getUsers()
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                liveData.postValue(it)
            })

        return liveData
    }

    fun checkUserDb(login: String): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()

        compositeDisposable.add(Observable.fromCallable {
            dao.getUserById(login)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    liveData.postValue(true)
                },
                onError = {
                    liveData.postValue(false)
                }
            )
        )
        return liveData
    }

    fun deleteUserDb(user: UserEntity)  {

        compositeDisposable.add(Observable.fromCallable {
            dao.deleteUser(user.id)
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())

        getUserAllDb()
    }

    fun disposeComposite() {
        compositeDisposable.dispose()
    }
}