package com.looke.avplayback.videos.di

import androidx.lifecycle.ViewModel
import com.looke.avplayback.core.viewmodel.ViewModelKey
import com.looke.avplayback.videos.data.repository.VideosRepository
import com.looke.avplayback.videos.data.repository.VideosRepositoryImpl
import com.looke.avplayback.videos.data.service.VideosService
import com.looke.avplayback.videos.presentation.VideosActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import retrofit2.create


@Module
internal abstract class VideosActivityModule {

    @Binds
    @IntoMap
    @VideosScope
    @ViewModelKey(VideosActivityViewModel::class)
    internal abstract fun bindViewModel(viewModel: VideosActivityViewModel): ViewModel

    @Binds
    @VideosScope
    internal abstract fun bindRepository(repository: VideosRepositoryImpl): VideosRepository

    @Module
    companion object {
        @Provides
        @VideosScope
        @JvmStatic
        internal fun providesService(retrofit: Retrofit): VideosService = retrofit.create()
    }
}
