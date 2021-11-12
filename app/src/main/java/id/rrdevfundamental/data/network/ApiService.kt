package id.rrdevfundamental.data.network

import id.rrdevfundamental.data.network.response.DetailResponse
import id.rrdevfundamental.data.network.response.SearchResponse
import id.rrdevfundamental.data.network.response.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getSearchUser(
        @Query("q") keyword: String
    ): Observable<SearchResponse>

    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String
    ): Observable<DetailResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String
    ): Observable<List<User>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ): Observable<List<User>>
}