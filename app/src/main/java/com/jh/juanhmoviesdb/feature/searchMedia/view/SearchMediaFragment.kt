package com.jh.juanhmoviesdb.feature.searchMedia.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.jh.juanhmoviesdb.R
import com.jh.juanhmoviesdb.common.network.ConnectionUtil
import com.jh.juanhmoviesdb.databinding.FragmentSearchMediaBinding
import com.jh.juanhmoviesdb.feature.searchMedia.domain.adapter.SearchMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMediaFragment : Fragment() {
    private lateinit var binding: FragmentSearchMediaBinding
    private val viewModel: SearchMediaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchMediaBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchRecyclerView.layoutManager = GridLayoutManager(context, 2)
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.searchRecyclerView.adapter = SearchMoviesAdapter()
        binding.searchRecyclerView.itemAnimator = null
        binding.edtTitle.requestFocus()
        openKeyboard()
        binding.btnInternet.setOnClickListener {
            if (ConnectionUtil().isConnected(context)) {
                binding.searchRecyclerView.visibility = View.VISIBLE
                binding.txtInternetTitle.visibility = View.INVISIBLE
                binding.btnInternet.visibility = View.INVISIBLE
                viewModel.searchMovies(binding.edtTitle.text.toString())
            }
        }

        binding.edtTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (ConnectionUtil().isConnected(context)) {
                    binding.txtInternetTitle.visibility = View.INVISIBLE
                    binding.btnInternet.visibility = View.INVISIBLE
                    viewModel.searchMovies("" + p0)
                } else {
                    binding.searchRecyclerView.visibility = View.GONE
                    binding.txtInternetTitle.visibility = View.VISIBLE
                    binding.btnInternet.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun initObservers() {
        viewModel.searchMovies.observe(viewLifecycleOwner, {
            if (ConnectionUtil().isConnected(context)) {
                if (it.isEmpty()) {
                    binding.txtInternetTitle.text = getString(R.string.no_movie)
                    binding.txtInternetTitle.visibility = View.VISIBLE
                    binding.searchRecyclerView.visibility = View.GONE
                } else {
                    binding.txtInternetTitle.visibility = View.GONE
                    binding.searchRecyclerView.visibility = View.VISIBLE
                    (binding.searchRecyclerView.adapter as SearchMoviesAdapter).setPopularMoviesData(
                        it.toMutableList()
                    )
                }
            }
        })
    }

    private fun openKeyboard() {
        val showKeyboard =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        showKeyboard.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }
}