package fvn.core.ui

import android.app.Activity
import android.databinding.ViewDataBinding
import fvn.core.viewmodel.BaseViewModel

/**
 * Created by nguyen.thanh.cong on 02/08/2017.
 */
interface IView<TB : ViewDataBinding, TM : BaseViewModel> {
    val viewModel: TM
    val binding: TB
    val callback : ICallBack
    val activity: Activity
    var layoutId: Int

    fun showLoading(isShow: Boolean)
}
