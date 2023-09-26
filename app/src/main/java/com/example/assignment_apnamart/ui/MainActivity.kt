package com.example.assignment_apnamart.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.assignment_apnamart.AppPreferences
import com.example.assignment_apnamart.R
import com.example.assignment_apnamart.databinding.ActivityMainBinding
import com.example.assignment_apnamart.viewModel.MainViewModel


class MainActivity : AppCompatActivity(), View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private var repoAdapter: RepositoryAdapter? = null
    lateinit var preferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = AppPreferences(this)
        binding.lifecycleOwner = this
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        supportActionBar?.hide()
        observeViewModel()
        initUI()
    }

    private fun observeViewModel() {
        with(mainViewModel) {
            getRepositories()?.observe(this@MainActivity) { list ->
                tempRepoList = list
                repoAdapter?.submitList(list)
            }
            error.observe(this@MainActivity) { value ->
                if (value == false)
                    showHideErrorLayout(value)
                else
                    if (tempRepoList.isNullOrEmpty())
                        showHideErrorLayout(value)
            }
            isLoading.observe(this@MainActivity) { value ->
                with(binding) {
                    if (value) {
                        shimmerViewContainer.startShimmerAnimation()
                    } else {
                        shimmerViewContainer.stopShimmerAnimation()
                    }
                    shimmerViewContainer.isVisible = value
                    swipeRefresh.isRefreshing = value
                }
            }
        }
    }

    private fun initUI() {
        repoAdapter = RepositoryAdapter(this)
        with(binding) {
            rvRepository.apply {
                adapter = repoAdapter
                layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            }
            retryBtn.setOnClickListener(this@MainActivity)
            swipeRefresh.setOnRefreshListener(this@MainActivity)
        }
    }

    private fun showHideErrorLayout(error: Boolean) {
        binding.noDataPlaceHolder.isVisible = error
        binding.rvRepository.isVisible = !error
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.retryBtn -> {
                showHideErrorLayout(false)
                mainViewModel.getRepositories(true)
            }
        }
    }

    override fun onRefresh() {
        binding.swipeRefresh.isRefreshing = true
        mainViewModel.getRepositories(true)
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerViewContainer.startShimmerAnimation()
    }
}