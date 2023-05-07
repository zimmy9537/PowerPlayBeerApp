package com.powerplay.zimmy.powerplaybeerapp.di

import com.powerplay.zimmy.powerplaybeerapp.network.ApiService
import com.powerplay.zimmy.powerplaybeerapp.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object DataRepositoryModule {

    @Provides
    fun provideDataRepository(apiService: ApiService): DataRepository {
        return DataRepository(apiService)
    }
}