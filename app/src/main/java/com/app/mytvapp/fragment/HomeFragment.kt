package com.app.mytvapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.app.mytvapp.R
import com.app.mytvapp.databinding.FragmentHomeBinding
import com.app.mytvapp.model.DataModel
import com.bumptech.glide.Glide
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class HomeFragment : Fragment(){
    private lateinit var mBinding:FragmentHomeBinding
    lateinit var listFragment: ListFragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        listFragment = ListFragment()

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.add(R.id.list_fragment, listFragment)
        transaction.commit()

        val gson = Gson()
        val i: InputStream = requireActivity().assets.open("movies.json")
        val br = BufferedReader(InputStreamReader(i))
        val dataList: DataModel = gson.fromJson(br, DataModel::class.java)

        listFragment.bindData(dataList)

        listFragment.setOnContentSelectedListener {
            updateBanner(it)
        }
    }

    fun updateBanner(dataList: DataModel.Result.Detail) {
        mBinding.title.text = dataList.title
        mBinding.description.text = dataList.overview

        val url = dataList.backdrop_path
        Glide.with(this).load(url).into(mBinding.imgBanner)
    }
}