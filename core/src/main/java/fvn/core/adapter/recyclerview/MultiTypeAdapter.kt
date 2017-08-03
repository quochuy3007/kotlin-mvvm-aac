/*
 * Copyright (C) 2016 MarkZhai (http://zhaiyifan.cn).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fvn.div2.core.adapter.recyclerview

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v4.util.ArrayMap
import android.view.ViewGroup
import java.util.*

/**
 * Super simple multi-type adapter using data-binding.

 * @author markzhai on 16/8/23
 */
class MultiTypeAdapter @JvmOverloads constructor(context: Context, viewTypeToLayoutMap: Map<Int,
        Int>? = null) : BaseViewAdapter<Any?>(context) {
    protected var mCollectionViewType: ArrayList<Int>
    private val mItemTypeToLayoutMap = ArrayMap<Int, Int>()

    init {
        mCollection = ArrayList<Any?>()
        mCollectionViewType = ArrayList<Int>()
        if (viewTypeToLayoutMap != null && !viewTypeToLayoutMap.isEmpty()) {
            mItemTypeToLayoutMap.putAll(viewTypeToLayoutMap)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<*> {
        return BindingViewHolder(DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, getLayoutRes
        (viewType), parent, false))
    }

    fun addViewTypeToLayoutMap(viewType: Int?, layoutRes: Int?) {
        mItemTypeToLayoutMap.put(viewType, layoutRes)
    }

    override fun getItemViewType(position: Int): Int {
        return mCollectionViewType[position]
    }

    operator fun set(viewModels: List<*>?, viewType: Int) {
        mCollection!!.clear()
        mCollectionViewType.clear()
        if (viewModels == null) {
            add(null, viewType)
        } else {
            addAll(viewModels, viewType)
        }
    }

    operator fun set(viewModels: List<Any?>, viewTyper: MultiViewTyper) {
        mCollection.clear();
        mCollectionViewType.clear()
        addAll(viewModels, viewTyper)
    }

    fun add(viewModel: Any?, viewType: Int) {
        mCollection.add(viewModel)
        mCollectionViewType.add(viewType)
        notifyItemInserted(mCollection!!.size - 1)
    }

    fun add(position: Int, viewModel: Any, viewType: Int) {
        mCollection.add(position, viewModel)
        mCollectionViewType.add(position, viewType)
        notifyItemInserted(position)
    }

    fun addAll(viewModels: List<*>?, viewType: Int) {
        if (viewModels == null) return
        mCollection.addAll(viewModels)
        for (i in viewModels.indices) {
            mCollectionViewType.add(viewType)
        }
        notifyDataSetChanged()
    }

    fun addAll(position: Int, viewModels: List<*>?, viewType: Int) {
        if (viewModels == null) return
        mCollection.addAll(position, viewModels)
        for (i in viewModels.indices) {
            mCollectionViewType.add(position + i, viewType)
        }
        notifyItemRangeChanged(position, viewModels.size - position)
    }

    fun addAll(viewModels: List<Any?>, multiViewTyper: MultiViewTyper) {
        if (viewModels == null) return
        mCollection.addAll(viewModels)
        for (i in viewModels.indices) {
            mCollectionViewType.add(multiViewTyper.getViewType(viewModels[i]))
        }
        notifyDataSetChanged()
    }

    override fun remove(position: Int) {
        mCollectionViewType.removeAt(position)
        super.remove(position)
    }

    override fun clear() {
        mCollectionViewType.clear()
        super.clear()
    }

    fun removeListIndex(position: Int) {
        val listSize = mCollection!!.size
        while (mCollection!!.size != position) {
            mCollection!!.removeAt(position)
            mCollectionViewType.removeAt(position)
        }
        notifyItemRangeChanged(position, listSize)
    }

    @LayoutRes
    protected fun getLayoutRes(viewType: Int): Int {
        mItemTypeToLayoutMap[viewType]?.let { return it }
        return 0
    }

    interface MultiViewTyper {
        fun getViewType(item: Any?): Int
    }
}
