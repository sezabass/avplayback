package com.looke.avplayback.core.application

import android.app.Application
import com.looke.avplayback.core.di.ApplicationComponent
import com.looke.avplayback.core.di.ApplicationComponentProvider
import com.looke.avplayback.core.di.DaggerApplicationComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class CustomApplication @Inject constructor() : Application(),
    ApplicationComponentProvider, HasAndroidInjector {

    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector() = androidInjector

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
            .applicationBind(this)
            .build()

        applicationComponent.inject(this)
    }

    override fun provideApplicationComponent(): ApplicationComponent = applicationComponent
}
