package com.example.suitevent.di

import com.example.suitevent.guest.GuestRepository
import com.example.suitevent.guest.IGuestRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
abstract class GuestModule {

    @Binds
    @ViewModelScoped
    abstract fun bindsGuestRepo(guestRepo: GuestRepository): IGuestRepository
}