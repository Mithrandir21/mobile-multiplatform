package com.demo.ui.albums.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.demo.R
import com.demo.base.BaseFragment
import com.demo.databinding.FragmentAlbumListBinding
import com.demo.ui.albums.list.FragmentAlbumListViewModel.Data
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAlbumList : BaseFragment<FragmentAlbumListBinding>(R.layout.fragment_album_list), AlbumAdapterInterface {

    private val viewModel: FragmentAlbumListViewModel by lazy { viewModel() }

    private val adapter = AlbumAdapter(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentAlbumListBinding.bind(view)


        val spacingDecoration = SpacingItemDecoration(
            spanCount = 1,
            spacing = resources.getDimensionPixelSize(R.dimen.grid_item_spacing),
            includeEdge = true,
            headerNum = 0,
            isReverse = false
        )
        viewBinding.albumList.addItemDecoration(spacingDecoration)
        viewBinding.albumList.adapter = adapter

        viewModel.observeErrors().observe(viewLifecycleOwner) { (throwable, _) ->
            Toast.makeText(requireContext(), "An error occurred: ${throwable.message}", Toast.LENGTH_LONG).show()
        }

        viewModel.observeProgress().observe(viewLifecycleOwner) { (loading, _) ->
            viewBinding.albumListProgressBar.isVisible = loading
        }

        viewModel.observeViewData().observe(viewLifecycleOwner) { data ->
            when (data) {
                is Data.AlbumsData -> adapter.setDataList(data.albums)
            }
        }


        /**
         * If there is a 'savedInstanceState', the Fragment is being recreated and the original data from the
         * ViewModel will be available. It is therefore not necessary to make a refresh call.
         */
        if (savedInstanceState == null) {
            viewModel.startObservingAlbums()
            viewModel.refreshAlbums()
        }
    }

    override fun modelAlbumSelected(modelAlbum: ModelAlbum) =
        when (modelAlbum.intermediary) {
            true -> FragmentAlbumListDirections.goToIntermediaryStep(modelAlbum.album.id)
            false -> FragmentAlbumListDirections.goToAlbumDetails(modelAlbum.album.id)
        }
            .let { direction -> navController.navigate(direction) }
}