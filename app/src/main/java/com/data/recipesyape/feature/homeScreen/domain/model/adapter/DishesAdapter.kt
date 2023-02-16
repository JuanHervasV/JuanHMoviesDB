package com.data.recipesyape.feature.homeScreen.domain.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.data.recipesyape.common.constant.BASE_IMAGE_URL
import com.data.recipesyape.common.utils.GenericItemListener
import com.data.database.model.RecipesDataModel
import com.data.recipesyape.databinding.RecipeItemBinding

class DishesAdapter(
    val listRecipes: MutableList<RecipesDataModel>,
    val genericItemListener: GenericItemListener<RecipesDataModel>
) : RecyclerView.Adapter<DishesAdapter.CustomAdapter>(),
    GenericItemListener<RecipesDataModel> {
    private var recipeSelected = RecipesDataModel()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter {
        return CustomAdapter(
            RecipeItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: CustomAdapter, position: Int) {
        holder.bind(listRecipes[position])
    }

    inner class CustomAdapter(val view: RecipeItemBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(recipes: RecipesDataModel) {
            val recipeData = listRecipes[position]
            view.txttitle.text = recipeData.name
            view.morigin.text = recipeData.origin
            Glide.with(view.imgimage.context)
                .load(BASE_IMAGE_URL + recipeData.images)
                .into(view.imgimage)

            view.root.setOnClickListener {
                genericItemListener.onItemClickListener(recipes, 0, adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return listRecipes.size
    }

    override fun onItemClickListener(item: RecipesDataModel, typeOperation: Int, position: Int) {
        recipeSelected = item
    }
}