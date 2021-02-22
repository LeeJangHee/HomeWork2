package com.work.homework2.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.work.homework2.R
import com.work.homework2.data.BeerModelsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class BeerListFragment : Fragment() {

    private val beerViewModel: BeerViewModel by viewModels()
    lateinit var adapter: BeerAdapter
    lateinit var mainActivity: MainActivity
    lateinit var recyclerView: RecyclerView
    private var recyclerCount = 1
    private var isNext = false // 다음 페이지 유무
    private val limitCount = 13

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = activity as MainActivity
        beerViewModel.setBeerData(recyclerCount)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.beer_view, container, false)
        recyclerView = view.findViewById(R.id.beer_view)
        adapter = BeerAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = view.findViewById<ProgressBar>(R.id.progress_circular)
        progressBar.visibility = View.VISIBLE

        beerViewModel.getBeerData().observe(requireActivity(), Observer {
            if (it.isNotEmpty())
                setHasNextPage(true)

            adapter.setBeerData(it)
            progressBar.visibility = View.GONE
        })


        adapter.listener = object : OnBeerItemClickListener {
            override fun onItemClick(holder: BeerAdapter.ViewHolder?, view: View?, id: Int) {
                mainActivity.onChangeFragment(1, id)
            }
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager

                if (hasNextPage()) {
                    val lastVisibleItem = (layoutManager as LinearLayoutManager)
                        .findLastCompletelyVisibleItemPosition()

                    if (layoutManager.itemCount <= lastVisibleItem + 2) {
                        if (limitCount > recyclerCount) {
                            beerViewModel.setBeerData(++recyclerCount)
                        } else {
                            recyclerCount = 1
                            beerViewModel.setBeerData(recyclerCount)
                        }
                        setHasNextPage(false)

                    }
                }

            }
        })


    }

    private fun hasNextPage(): Boolean {
        return isNext
    }

    private fun setHasNextPage(b: Boolean) {
        isNext = b
    }
}


