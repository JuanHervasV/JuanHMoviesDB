package com.data.recipesyape.common.utils

interface GenericItemListener<T> {
    fun onItemClickListener(item: T, type: Int, position: Int)
}