package com.work.homework2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.work.homework2.R

class BeerContextFragment: Fragment() {

    private val beerViewModel: BeerViewModel by viewModels()
    lateinit var imageView: ImageView
    lateinit var nameTextView: TextView
    lateinit var phTextView: TextView
    lateinit var tipsTextView: TextView

    companion object{

        fun newInstance(id: Int): BeerContextFragment {
            val args = Bundle()
            args.putInt("id", id)
            val fragment = BeerContextFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val id = requireArguments().getInt("id")
        // id를 받아와 상세정보 view 생성
        beerViewModel.setBeerContext(id)

        val view = inflater.inflate(R.layout.beer_context, container, false)
        imageView = view.findViewById(R.id.imageView)
        nameTextView = view.findViewById(R.id.context_name)
        phTextView = view.findViewById(R.id.context_ph)
        tipsTextView = view.findViewById(R.id.context_tips)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 상세정보 ViewModel
        beerViewModel.getBeerContext().observe(requireActivity(), Observer {
            nameTextView.text = "Name: ${it[0].name}"
            phTextView.text = "PH: ${it[0].ph}"
            tipsTextView.text = "TIPS: ${it[0].brewers_tips}"
            Glide.with(this).load(it[0].image_url).into(imageView)

        })

    }
}