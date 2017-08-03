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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fvn.core.BR
import fvn.core.unsafeLazy
import fvn.core.util.AutoClearedValue
import fvn.core.viewmodel.BaseViewModel
import javax.inject.Inject

/**
 * Created by nguyen.thanh.cong on 02/08/2017.
 */
abstract class BaseFragment<VDB : ViewDataBinding, VM : BaseViewModel> : Fragment(), IView<VDB, VM>, LifecycleRegistryOwner {
    protected abstract val vmToken: Class<VM>
    private val lifecycleRegistry = LifecycleRegistry(this)
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    val acvBinding: AutoClearedValue<VDB> by unsafeLazy { AutoClearedValue(this, DataBindingUtil.inflate<VDB>(layoutInflater, layoutId, null, false)) }
    override val binding: VDB
        get() = acvBinding.value!!
    override val viewModel: VM  by unsafeLazy { ViewModelProviders.of(this, viewModelFactory).get(vmToken) }

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!binding.setVariable(BR.viewModel, viewModel)) {
            throw IllegalArgumentException("You should add 'viewModel' variable")
        }
        binding.setVariable(BR.callback, callback)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override val activity: Activity
        get() = getActivity()
}
