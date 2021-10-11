package com.demo.feature.intermediary

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.demo.base.BaseFragment
import com.demo.feature.intermediary.databinding.FragmentIntermediaryLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentIntermediary : BaseFragment<FragmentIntermediaryLayoutBinding>(R.layout.fragment_intermediary_layout) {

    private val navArgs by navArgs<FragmentIntermediaryArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentIntermediaryLayoutBinding.bind(view)

        viewBinding.intermediaryStepButton.setOnClickListener {
            navController.navigate(FragmentIntermediaryDirections.goToAlbumDetails(navArgs.albumId))
        }
    }
}