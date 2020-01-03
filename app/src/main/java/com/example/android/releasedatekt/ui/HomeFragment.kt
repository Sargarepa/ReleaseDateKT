package com.example.android.releasedatekt.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.example.android.releasedatekt.R
import com.example.android.releasedatekt.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Create binding variable
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_home,
            container,
            false)
        return binding.root
    }


}


