package com.powerplay.zimmy.powerplaybeerapp.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.powerplay.zimmy.powerplaybeerapp.R
import com.powerplay.zimmy.powerplaybeerapp.databinding.FragmentBeerListBinding
import com.powerplay.zimmy.powerplaybeerapp.network.ResultData
import com.powerplay.zimmy.powerplaybeerapp.ui.main.adapter.BeerListAdapter
import com.powerplay.zimmy.powerplaybeerapp.util.BeerUtils
import com.powerplay.zimmy.powerplaybeerapp.util.PaginationScrollListener
import com.powerplay.zimmy.powerplaybeerapp.viewmodel.BeerListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeerListFragment : Fragment(), BeerListAdapter.BeerActionListener {
    private lateinit var binding: FragmentBeerListBinding

    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private val viewModel: BeerListViewModel by viewModels()

    private val adapter: BeerListAdapter by lazy {
        BeerListAdapter(context, this)
    }

    private val recyclerViewScrollListener: PaginationScrollListener by lazy {
        object : PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                viewModel.fetchData()
            }

            override fun isLastPage(): Boolean {
                return viewModel.isLastPage.get()
            }

            override fun isLoading(): Boolean {
                return viewModel.isLoading.get()
            }
        }
    }

    private fun setObservers() {
        with(viewModel) {
            beerModelLiveData.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is ResultData.Loading -> {
                        if (viewModel.pageCount == 1) {
                            binding.progressPb.isVisible = true
                            binding.beerRecyclerView.isVisible = false
                            binding.noDataTv.isVisible = false
                        }
                    }

                    is ResultData.Success -> {
                        binding.progressPb.isVisible = false
                        binding.beerRecyclerView.isVisible = true
                        binding.noDataTv.isVisible = false
                        incrementPageCount()
                    }

                    is ResultData.Failed -> {
                        if (viewModel.pageCount == 1) {
                            binding.progressPb.isVisible = false
                            binding.beerRecyclerView.isVisible = false
                            binding.noDataTv.isVisible = true
                        }
                    }
                }
            }
            beerListNotifier.observe(viewLifecycleOwner) {
                adapter.submitList(viewModel.beerList.map {
                    it.copy()
                })
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeerListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUserInterFace()
        checkNetwork()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun checkNetwork() {
        if (!BeerUtils.isNetworkAvailable(context)) {
            Toast.makeText(context, "Internet Not Available", Toast.LENGTH_LONG).show()
        }
    }

    private fun initUserInterFace() {
        setUI()
        setObservers()
        viewModel.fetchData()
    }

    private fun setUI() {
        with(binding) {
            beerRecyclerView.layoutManager = layoutManager
            beerRecyclerView.adapter = adapter
            beerRecyclerView.addOnScrollListener(recyclerViewScrollListener)
            refreshFb.setOnClickListener {
                viewModel.resetLists()
                viewModel.fetchData()
                checkNetwork()
            }
        }
    }

    override fun openBottomSheetDialog(description: String) {
        createDialog(description)
    }

    private fun createDialog(description: String) {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.layout_beer_bottomsheet, null, false)
        val textView = view.findViewById<TextView>(R.id.descriptionTv)
        val closeView = view.findViewById<View>(R.id.closeView)
        closeView.setOnClickListener {
            dialog.hide()
        }
        textView.text = description
        dialog.setContentView(view)
        dialog.show()
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }
}