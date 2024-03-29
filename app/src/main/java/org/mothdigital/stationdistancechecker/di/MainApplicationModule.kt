package org.mothdigital.stationdistancechecker.di

import androidx.room.Room
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.mothdigital.stationdistancechecker.BuildConfig
import org.mothdigital.stationdistancechecker.api.HeaderInterceptor
import org.mothdigital.stationdistancechecker.database.AppDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val mainApplicationModule = module {
    singleOf(::HeaderInterceptor)

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HeaderInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, BuildConfig.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().fetchTimeDao() }
    single { get<AppDatabase>().stationDao() }
    single { get<AppDatabase>().stationKeywordDao() }
}