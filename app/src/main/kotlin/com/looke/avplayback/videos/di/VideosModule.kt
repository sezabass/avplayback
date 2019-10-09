package com.looke.avplayback.videos.di

import com.looke.avplayback.videos.presentation.VideosActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class VideosModule {
    @VideosScope
    @ContributesAndroidInjector(modules = [VideosActivityModule::class])
    abstract fun contributesVideosActivity(): VideosActivity
}
