package fvn.core.di.module

import android.arch.lifecycle.ViewModelProvider

import dagger.Binds
import dagger.Module
import fvn.core.viewmodel.ViewModelFactory

@Module
internal abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
