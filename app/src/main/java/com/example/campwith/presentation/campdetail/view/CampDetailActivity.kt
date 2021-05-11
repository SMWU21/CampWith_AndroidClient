package com.example.campwith.presentation.campdetail.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.campwith.R
import com.example.campwith.data.camp.CampDetailResponse
import com.example.campwith.databinding.ActivityCampDetailBinding
import com.example.campwith.presentation.base.BaseActivity
import com.example.campwith.presentation.campdetail.viewmodel.CampDetailViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_camp_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CampDetailActivity :
    BaseActivity<ActivityCampDetailBinding, CampDetailViewModel>(R.layout.activity_camp_detail) {

    override val viewModel: CampDetailViewModel by viewModel()
    lateinit var id: String
    lateinit var campItem: CampDetailResponse
    val campReviewFragment = CampReviewFragment()
    val campMapFragment = CampMapFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        id = intent.getStringExtra("id").toString()

        viewModel.getCampDetail(id)
        viewModel.campDetailLiveData.observe(
            this,
            Observer {
                campItem = it
                binding.itemCamp = campItem
                Glide.with(this)
                    .load(campItem.firstImageUrl)
                    .into(binding.ivCampDetail)
                val bundle = Bundle()
                bundle.putParcelable("campItem", campItem)
                campReviewFragment.arguments = bundle
                campMapFragment.arguments = bundle
            }
        )

        supportFragmentManager.beginTransaction().add(R.id.fl_container, campReviewFragment)
            .commit();

        tl_tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            lateinit var selectedFragment: Fragment
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> selectedFragment = campReviewFragment
                    1 -> selectedFragment = campMapFragment
                }
                supportFragmentManager.beginTransaction().replace(
                    R.id.fl_container,
                    selectedFragment
                ).commit()
            }
        })
    }
}