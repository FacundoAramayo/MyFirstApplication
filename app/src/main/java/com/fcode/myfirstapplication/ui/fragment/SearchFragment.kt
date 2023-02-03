package com.fcode.myfirstapplication.ui.fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.fcode.myfirstapplication.R
import com.fcode.myfirstapplication.domain.Article
import com.fcode.myfirstapplication.ui.activity.DetailActivity
import com.fcode.myfirstapplication.databinding.FragmentSearchBinding
import com.fcode.myfirstapplication.presentation.SearchViewModel
import com.fcode.myfirstapplication.ui.widget.ArticleAdapter
import com.fcode.myfirstapplication.ui.widget.DatePickerDialogCustom
import com.fcode.myfirstapplication.ui.widget.SortByDialog
import com.fcode.myfirstapplication.utils.KeyboardUtils

class SearchFragment: Fragment(), DatePickerDialog.OnDateSetListener, SortByDialog.OnSortByListener {

    lateinit var viewBinding: FragmentSearchBinding
    private lateinit var articleAdapter: ArticleAdapter
    private val viewModel: SearchViewModel by activityViewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentSearchBinding.inflate(inflater, container, false)
        showResults(false)
        setupList()

        viewModel.getObservableSearchResults().observe(viewLifecycleOwner, Observer {
            showLoading(false)
            if (it.isEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.generic_error), Toast.LENGTH_LONG).show()
            } else {
                articleAdapter.articles = it
            }
        })

        viewBinding.apply {
            search.searchView.setIconifiedByDefault(false)
            search.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    showResults(true)
                    performSearch()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }

            })

            search.fromDate.setOnClickListener {
                val datePickerDialogCustom = DatePickerDialogCustom(this@SearchFragment)
                datePickerDialogCustom.show(
                    requireActivity().supportFragmentManager,
                    DatePickerDialogCustom.TAG
                )
            }

            search.sortBy.setOnClickListener {
                SortByDialog(requireActivity(), this@SearchFragment)
                    .show(
                        requireActivity().supportFragmentManager,
                        SortByDialog.TAG
                    )
            }
        }

        return viewBinding.root
    }

    private fun performSearch() {
        showLoading(true)
        viewBinding.search.apply {
            viewModel.loadNewsWithFilters(
                searchView.query.toString(),
                fromDateContent.text.toString(),
                sortByContent.text.toString()
            )
        }
        KeyboardUtils.hideSoftKeyboard(viewBinding.search.root)
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            viewBinding.progressBar.visibility = View.VISIBLE
            viewBinding.recyclerViewList.visibility = View.GONE
            viewBinding.results.visibility = View.GONE
        } else {
            viewBinding.progressBar.visibility = View.GONE
            viewBinding.recyclerViewList.visibility = View.VISIBLE
            viewBinding.results.visibility = View.VISIBLE
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        viewBinding.search.fromDateContent.text = "$year-$month-$dayOfMonth"
        performSearch()
    }

    override fun onSortBySet(selection: String) {
        viewBinding.search.sortByContent.text = selection
        performSearch()
    }

    private fun setupList() {
        articleAdapter = ArticleAdapter {
            article ->  onNewsItemClick(article)
        }

        viewBinding.recyclerViewList.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(activity)

            val dividerItemDecoration = DividerItemDecoration(
                this.context,
                VERTICAL
            )
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun showResults(show: Boolean) {
        if (show) {
            viewBinding.apply {
                body.visibility = View.VISIBLE
                imageHeader.visibility = View.GONE
            }
        } else {
            viewBinding.apply {
                body.visibility = View.GONE
                imageHeader.visibility = View.VISIBLE
            }
        }
    }

    private fun onNewsItemClick(article: Article) {
        val intent = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(DetailActivity.URL_PARAM, article.url)
        }
        if (WebContentFragment.isAvailable()) {
            WebContentFragment.setWebContent(article.url)
        } else {
            requireActivity().startActivity(intent)
        }
    }

}