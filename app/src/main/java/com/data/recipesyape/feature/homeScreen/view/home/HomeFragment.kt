package com.data.recipesyape.feature.homeScreen.view.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.denzcoskun.imageslider.models.SlideModel
import com.data.recipesyape.databinding.FragmentHomeBinding
import com.data.recipesyape.feature.homeScreen.domain.model.adapter.DishesAdapter
import dagger.hilt.android.AndroidEntryPoint
import androidx.recyclerview.widget.GridLayoutManager
import com.data.recipesyape.common.network.ConnectionUtil
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.data.database.model.RecipesDataModel
import com.data.recipesyape.common.constant.BLANK
import com.data.recipesyape.common.utils.GenericItemListener
import com.data.recipesyape.common.utils.showInternetDialog
import com.data.recipesyape.common.utils.toRecipeDataModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), GenericItemListener<RecipesDataModel> {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private var recipeSelected = RecipesDataModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (this::binding.isInitialized.not()) {
            binding = FragmentHomeBinding.inflate(inflater)
            binding.lifecycleOwner = this
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        with(binding) {
            recipesRecyclerView.layoutManager = GridLayoutManager(context, 1)
            btnSearch.setOnClickListener {
                edtTitle.visibility = View.VISIBLE
                btnSearch.visibility = View.GONE
                txtRecipesHome.visibility = View.INVISIBLE
            }

            if (!edtTitle.hasFocus()) {
                edtTitle.visibility = View.GONE
                btnSearch.visibility = View.VISIBLE
                txtRecipesHome.visibility = View.VISIBLE
            }

            edtTitle.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    with(viewModel) {
                        searchRecipes(p0.toString())
                    }
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()

    }

    private fun initObservers() {
        with(viewModel) {
            lifecycleScope.launch {
                recipeList.flowWithLifecycle(lifecycle = lifecycle).collectLatest {
                    binding.recipesRecyclerView.adapter =
                        DishesAdapter(toRecipeDataModel(it).toMutableList(), this@HomeFragment)
                    addRecipesToDataBase(it)
                }
            }

            lifecycleScope.launch {
                if (readRecipesDataBaseRoom().isEmpty()) {
                    callRecipeList()
                } else {
                    binding.recipesRecyclerView.adapter =
                        DishesAdapter(readRecipesDataBaseRoom().toMutableList(), this@HomeFragment)
                }
            }

            lifecycleScope.launch {
                recipeLocalList.flowWithLifecycle(lifecycle = lifecycle).collectLatest {
                    binding.recipesRecyclerView.adapter =
                        DishesAdapter(it.toMutableList(), this@HomeFragment)
                }
            }
        }
    }

    override fun onItemClickListener(item: RecipesDataModel, type: Int, position: Int) {
        recipeSelected = item
        findNavController().navigate(
            HomeFragmentDirections.gotoRecipeDetails(
                RecipesDataModel(
                    id = item.id,
                    name = item.name,
                    origin = item.origin,
                    latitude = item.latitude,
                    longitude = item.longitude,
                    ingredients = item.ingredients,
                    preparation = item.preparation,
                    images = item.images
                )
            )
        )
        with(binding) {
            edtTitle.setText(BLANK)
        }
    }
}