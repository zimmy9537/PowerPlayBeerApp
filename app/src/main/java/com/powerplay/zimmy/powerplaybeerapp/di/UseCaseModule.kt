package com.powerplay.zimmy.powerplaybeerapp.di

import com.powerplay.zimmy.powerplaybeerapp.repository.BeerRepository
import com.powerplay.zimmy.powerplaybeerapp.usecase.DataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object UseCaseModule {

    @Provides
    fun providesDataUseCase(beerRepository: BeerRepository): DataUseCase {
        return DataUseCase(beerRepository)
    }
}