package com.work.homework2.ui

import android.view.View

interface OnBeerItemClickListener {
    fun onItemClick(holder: BeerAdapter.ViewHolder?, view: View?, id: Int)
}