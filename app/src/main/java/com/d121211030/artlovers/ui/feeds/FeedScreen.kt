package com.d121211030.artlovers.ui.feeds

import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.d121211030.artlovers.R
import com.d121211030.artlovers.data.model.Artwork

abstract class FeedScreen : Fragment() {

    val viewModel: FeedViewModel by viewModels()

    fun updateIsLoved(artwork: Artwork) {
        viewModel.updateIsLoved(artwork)
    }

    // use NavController to navigate from feed fragment to detail fragment
    fun navigateToDetail(artwork: Artwork) {
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        try {
            navController.navigate(
                R.id.detailScreen,
                //TODO create parcelable Artwork model to pass here
                bundleOf(
                    key_id to artwork.id,
                    key_title to artwork.title,
                    key_artist to artwork.artistDisplay,
                    key_description to artwork.description,
                    key_isLoved to artwork.isLoved,
                    key_imageId to artwork.imageId
                )
            )
        } catch (exception: Exception) {
            Log.w("FeedScreen", "Exception at navigateToDetail(${artwork}): ${exception}")
        }
    }

    companion object {
        const val key_id = "ART_ID"
        const val key_title = "ART_TITLE"
        const val key_artist = "ART_ARTIST"
        const val key_description = "ART_DESC"
        const val key_isLoved = "ART_LOVED"
        const val key_imageId = "ART_IMAGEID"
    }

}
