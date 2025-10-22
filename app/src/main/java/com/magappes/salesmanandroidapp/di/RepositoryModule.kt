package com.magappes.salesmanandroidapp.di

import com.magappes.salesmanandroidapp.data.repository.SalesmenRepository
import com.magappes.salesmanandroidapp.data.repository.SalesmenRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideSalesmenRepository(): SalesmenRepository = SalesmenRepositoryImpl()
}