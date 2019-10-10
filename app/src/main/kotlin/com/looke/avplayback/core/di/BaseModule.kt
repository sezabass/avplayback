package com.looke.avplayback.core.di

import androidx.lifecycle.ViewModelProvider
import com.looke.avplayback.core.viewmodel.factory.ViewModelFactory
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class BaseModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Module
    companion object {

        @Named("BASE_URL")
        @Provides
        @JvmStatic
        @Singleton
        internal fun baseUrl() = "https://firebasestorage.googleapis.com/"

        @Provides
        @JvmStatic
        @Singleton
        fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = Level.BODY
            return loggingInterceptor
        }

        @Provides
        @JvmStatic
        @Singleton
        internal fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
            val builder = OkHttpClient.Builder()
            builder.addInterceptor(httpLoggingInterceptor)
            return builder.build()
        }

        @Provides
        @JvmStatic
        @Singleton
        internal fun provideMoshi(): Moshi = Moshi.Builder().build()

        @Provides
        @JvmStatic
        @Singleton
        internal fun provideRetrofit(
            okHttpClient: OkHttpClient,
            @Named("BASE_URL") baseUrl: String,
            moshi: Moshi
        ): Retrofit =
            Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttpClient)
                .build()
    }
}
