package com.data.common.utils

private const val SUCCESSFUL_CODE = "0000"

val String?.isSuccessful
    get(): Boolean {
        return this == SUCCESSFUL_CODE
    }