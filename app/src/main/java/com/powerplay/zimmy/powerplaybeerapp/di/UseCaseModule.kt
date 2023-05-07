package com.powerplay.zimmy.powerplaybeerapp.di

import com.powerplay.zimmy.powerplaybeerapp.repository.DataRepository
import com.powerplay.zimmy.powerplaybeerapp.usecase.DataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {

    @Provides
    fun providesDataUseCase(dataRepository: DataRepository): DataUseCase {
        return DataUseCase(dataRepository)
    }
}