package com.powerplay.zimmy.powerplaybeerapp.di

import com.powerplay.zimmy.powerplaybeerapp.network.BeerApiService
import com.powerplay.zimmy.powerplaybeerapp.repository.BeerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object DataRepositoryModule {

    @Provides
    fun provideDataRepository(beerApiService: BeerApiService): BeerRepository {
        return BeerRepository(beerApiService)
    }
}