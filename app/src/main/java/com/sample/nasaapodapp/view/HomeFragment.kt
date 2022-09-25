package com.sample.nasaapodapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sample.nasaapodapp.R
import com.sample.nasaapodapp.common.NetworkResult
import com.sample.nasaapodapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    val args: HomeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getNasaApod(args.dateValue.toString())

        setupObserver()

    }

    private fun setupObserver() {
        viewModel.nasaApod.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    progressBar.visibility = View.INVISIBLE

                    it.data?.let { response ->

                        tv_title.text = response.title
                        tv_description.text = response.explanation
                        tv_date.text = response.date

                        Glide.with(requireActivity()).load(response?.url)
                            .into(image_apod)

                        fab.setOnClickListener {
                            lifecycleScope.launch(Dispatchers.IO) {
                                viewModel.saveApod(response)
                            }
                        }

                    }
                }
                is NetworkResult.Error -> {
                    progressBar.visibility = View.INVISIBLE

                    it.error?.let { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        println("Error: $message")
                    }
                }
                is NetworkResult.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
            }

        }
    }
}