package com.example.sampleworkmanager

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {
    val dependency: DummyDependency

    fun inject(application: CustomApplication)
}