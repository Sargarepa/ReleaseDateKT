package com.example.android.releasedatekt.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.android.releasedatekt.R
import com.example.android.releasedatekt.databinding.FragmentHomeBinding
import com.example.android.releasedatekt.databinding.MediaItemBinding
import com.example.android.releasedatekt.domain.Movie
import com.example.android.releasedatekt.viewmodels.HomeViewModel

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, HomeViewModel.Factory(activity.application))
            .get(HomeViewModel::class.java)
    }

    private var viewModelAdapter: HomeAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.movies.observe(this, Observer {movies ->
            movies?.apply {
                viewModelAdapter?.movies = movies
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Create binding variable
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_home,
            container,
            false)

        binding.setLifecycleOwner(viewLifecycleOwner)

        binding.viewModel = viewModel

        viewModelAdapter = HomeAdapter(MovieClickListener {
            Toast.makeText(context, "${it.title} has been clicked!", Toast.LENGTH_SHORT).show()
        })

        binding.homeRecyclerView.apply {
            adapter = viewModelAdapter
            layoutManager = LinearLayoutManager(context)
        }

        return binding.root
    }


}

class MovieClickListener(val clickListener: (Movie) -> Unit) {
    fun onClick(movie: Movie) = clickListener(movie)
}

class HomeAdapter(val clickListener: MovieClickListener) : RecyclerView.Adapter<MovieViewHolder>() {

    var movies: List<Movie> = emptyList()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val viewDataBinding: MediaItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            MovieViewHolder.LAYOUT,
            parent,
            false
        )

        return MovieViewHolder(viewDataBinding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.viewDataBinding.callback = clickListener
        holder.viewDataBinding.movie = movies[position]
    }
}

class MovieViewHolder(val viewDataBinding: MediaItemBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.media_item
    }
}


