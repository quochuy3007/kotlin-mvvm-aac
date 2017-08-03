package fvn.core.ui

import android.app.Activity
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import fvn.core.BR
import fvn.core.unsafeLazy
import fvn.core.viewmodel.BaseViewModel
import javax.inject.Inject

/**
 * Created by nguyen.thanh.cong on 02/08/2017.
 */
abstract class BaseActivity<VDB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(),
        IView<VDB, VM>, LifecycleRegistryOwner, HasSupportFragmentInjector {
    /***
     * Class of this file, for example : BaseViewModel::class.java
     */
    protected abstract val vmToken: Class<VM>
    private val lifecycleRegistry = LifecycleRegistry(this)
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    fun init(savedInstanceState: Bundle?) {
        if (!binding.setVariable(fvn.core.BR.viewModel, viewModel)) {
            throw IllegalArgumentException("You should add 'viewModel' variable")
        }
        binding.setVariable(BR.callback,callback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    override val activity: Activity
        get() = this
    override val viewModel: VM  by unsafeLazy { ViewModelProviders.of(this, viewModelFactory).get(vmToken) }
    override val binding: VDB by unsafeLazy { DataBindingUtil.setContentView<VDB>(this, layoutId) }
}
