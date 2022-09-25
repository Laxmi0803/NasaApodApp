package com.sample.nasaapodapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sample.nasaapodapp.R
import com.sample.nasaapodapp.adapter.FavoriteListAdapter
import com.sample.nasaapodapp.model.ApodDTO
import com.sample.nasaapodapp.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite.*

@AndroidEntryPoint
class FavoriteFragment : Fragment(),FavoriteListAdapter.OnItemClickListener{

    private val viewModel: FavoriteViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val favoriteAdapter = FavoriteListAdapter(this)

        recyclerView.apply {
            adapter = favoriteAdapter
            setHasFixedSize(true)
        }
        viewModel.getAllSavedApod().observe(viewLifecycleOwner) {
            favoriteAdapter.submitList(it)
        }
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val apod = favoriteAdapter.currentList[viewHolder.adapterPosition]
                viewModel.onFavoriteListSwiped(apod)
                Snackbar.make(recyclerView, "Deleted " , Snackbar.LENGTH_LONG)
                    .setAction(
                        "Undo",
                        View.OnClickListener {
                           viewModel.onUndoDeleteClick(apod)
                        }).show()
            }
        }).attachToRecyclerView(recyclerView)
    }

    override fun onItemClick(apod: ApodDTO) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToHomeFragment(apod.date)
        findNavController().navigate(action)
    }

}
