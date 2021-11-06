package id.rrdevfundamental

import id.rrdevfundamental.data.network.ApiService
import id.rrdevfundamental.data.network.BasicInterceptor
import id.rrdevfundamental.data.repository.UserRepository
import id.rrdevfundamental.ui.detail.DetailViewModel
import id.rrdevfundamental.ui.home.HomeViewModel
import id.rrdevfundamental.utils.ACCESS_TOKEN
import id.rrdevfundamental.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    single {
        val okHttp = OkHttpClient.Builder()
            .addInterceptor(BasicInterceptor(ACCESS_TOKEN))
            .addInterceptor(logger)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttp)
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}

val repositoryModule = module {
    single { UserRepository(get()) }
}