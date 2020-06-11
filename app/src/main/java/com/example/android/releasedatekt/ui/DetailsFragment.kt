package com.example.android.releasedatekt.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.android.releasedatekt.application
import com.example.android.releasedatekt.databinding.FragmentDetailsBinding
import com.example.android.releasedatekt.viewmodels.DetailsViewModel
import com.example.android.releasedatekt.viewmodels.ViewModelFactory
import javax.inject.Inject

class DetailsFragment : Fragment() {

    @Inject
    lateinit var detailsViewModelFactory: ViewModelFactory

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProviders.of(this, detailsViewModelFactory).get(DetailsViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.movieId = DetailsFragmentArgs.fromBundle(arguments!!).selectedMovie
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        requireContext().application.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailsBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        return binding.root
    }
}