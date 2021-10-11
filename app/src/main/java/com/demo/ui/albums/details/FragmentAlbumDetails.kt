package com.demo.ui.albums.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.demo.R
import com.demo.base.BaseFragment
import com.demo.databinding.FragmentAlbumDetailsBinding
import com.demo.domain.model.albums.Album
import com.demo.ui.albums.details.FragmentAlbumDetailsViewModel.Data
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAlbumDetails : BaseFragment<FragmentAlbumDetailsBinding>(R.layout.fragment_album_details) {

    private val viewModel: FragmentAlbumDetailsViewModel by lazy { viewModel() }

    private val navArgs by navArgs<FragmentAlbumDetailsArgs>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentAlbumDetailsBinding.bind(view)


        viewModel.observeErrors().observe(viewLifecycleOwner) { (throwable, _) ->
            Toast.makeText(requireContext(), "An error occurred: ${throwable.message}", Toast.LENGTH_LONG).show()
        }

        viewModel.observeProgress().observe(viewLifecycleOwner) { (loading, _) ->
            viewBinding.albumDetailsProgressBar.isVisible = loading
        }

        viewModel.observeViewData().observe(viewLifecycleOwner) { data ->
            when (data) {
                is Data.AlbumData -> setupAlbum(data.album)
                is Data.NoAlbumData -> albumNotFound(data.albumID)
            }
        }

        /**
         * If there is a 'savedInstanceState', the Fragment is being recreated and the original data from the
         * ViewModel will be available. It is therefore not necessary to make a refresh call.
         */
        if (savedInstanceState == null) {
            viewModel.observeAlbum(navArgs.albumId)
        }
    }

    private fun setupAlbum(album: Album) {
        viewBinding.apply {
            detailsAlbumIdLabel.text = getString(R.string.album_id, album.id)
            detailsAlbumUserIdLabel.text = getString(R.string.album_user_id, album.userId)
            detailsAlbumNameLabel.text = getString(R.string.album_name, album.title)
        }
    }

    private fun albumNotFound(albumID: Int) {
        viewBinding.apply {
            detailsAlbumIdLabel.isVisible = false
            detailsAlbumUserIdLabel.isVisible = false
            detailsAlbumNameLabel.isVisible = false

            detailsAlbumNotFound.isVisible = true
            detailsAlbumNotFound.text = getString(R.string.no_album_found_for_id, albumID)
        }
    }
}