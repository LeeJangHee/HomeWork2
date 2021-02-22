package com.work.homework2.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.work.homework2.R

class MainActivity : AppCompatActivity() {
    var currFragment: Int = 0
    lateinit var actionBar: ActionBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        // 상단 바 뒤로가기 버튼 생성
        actionBar = supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(false)

        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, BeerListFragment()).commit()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onChangeFragment(0, 0)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    open fun onChangeFragment(index: Int, id: Int) {
        when (index) {
            0 -> {
                // 기본 리사이클러뷰
                // 뒤로가기 버튼 비활성화
                currFragment = 0
                actionBar.setDisplayHomeAsUpEnabled(false)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, BeerListFragment()).commit()
            }
            1 -> {
                // 상세정보
                // 뒤로가기 버튼 활성화
                currFragment = 1
                actionBar.setDisplayHomeAsUpEnabled(true)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, BeerContextFragment.newInstance(id))
                    .commit()
            }
        }
    }

    override fun onBackPressed() {
        // 뒤로가기 버튼 누르기
        // 상세정보가 열려있으면 리사이클러뷰로 이동 후
        // 엑티비티 종료
        if (currFragment > 0) {
            onChangeFragment(0, 0)
        } else {
            super.onBackPressed()
        }
    }
}