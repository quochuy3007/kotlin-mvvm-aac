package fvn.core

/**
 * Created by nguyen.thanh.cong on 02/08/2017.
 */
/**
 * A thread unsafe lazy function.
 * This function 'must' be called only on single thread.
 */
fun <T> unsafeLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)