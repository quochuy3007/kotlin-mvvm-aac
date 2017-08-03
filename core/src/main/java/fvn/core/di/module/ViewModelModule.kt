package fvn.div2.core.di.module

import android.arch.lifecycle.ViewModelProvider

import dagger.Binds
import dagger.Module
import fvn.div2.core.viewmodel.ViewModelFactory

@Module
internal abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
