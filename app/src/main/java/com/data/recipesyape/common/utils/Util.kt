package com.data.recipesyape.common.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.Button
import androidx.navigation.NavController
import com.data.database.model.RecipesDataModel
import com.data.recipesyape.R
import com.example.juanhervas.dominio.entities.RecipesModel


fun toRecipeDataModel(list: List<RecipesModel>): List<RecipesDataModel> {
    return list.map {
        RecipesDataModel(
            id = it.id,
            name = it.name,
            origin = it.origin,
            latitude = it.latitude,
            longitude = it.longitude,
            ingredients = it.ingredients,
            preparation = it.preparation,
            images = it.images
        )
    }
}

fun showInternetDialog(
    context: Context?,
    isFromDetails: Boolean = false,
    navController: NavController? = null
) {
    val dialog: AlertDialog = AlertDialog.Builder(context)
        .setTitle(R.string.no_internet)
        .setCancelable(false)
        .setNeutralButton(R.string.try_again, null)
        .create()

    dialog.setOnShowListener {
        val button: Button =
            (dialog).getButton(AlertDialog.BUTTON_NEUTRAL)
        button.setOnClickListener {
            if (isFromDetails) {
                navController?.popBackStack()
            }
            dialog.dismiss()
        }
    }
    dialog.show()
}
