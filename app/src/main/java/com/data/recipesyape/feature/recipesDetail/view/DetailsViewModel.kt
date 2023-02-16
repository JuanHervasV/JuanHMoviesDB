package com.data.recipesyape.feature.recipesDetail.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    val context: Application,
) : AndroidViewModel(context) {
}