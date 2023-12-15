package com.d121211030.artlovers.ui.detail

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.d121211030.artlovers.R
import com.d121211030.artlovers.data.model.Artwork
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailScreen : Fragment() {

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // First use arguments to populate view
        arguments?.let {
            val art = Artwork(
                id = it.getLong(key_id),
                title = it.getString(key_title) ?: "" ,
                artistDisplay = it.getString(key_artist),
                description = it.getString(key_description),
                imageId = it.getString(key_imageId),
                isLoved = it.getBoolean(key_isLoved)
            )
            viewModel.updateModel(
                art
            )
        }

        lifecycleScope.launch {
            viewModel.artwork.observe(viewLifecycleOwner) { artwork ->
                if (artwork == null) { Log.w("DetailScreen", "Artwork is null") }

                artwork?.let {
                    // Fetch detail info from remote if args was null (search results)
                    if (it.artistDisplay == null) {
                        viewModel.refreshArtworkDetail()
                    }
                    loadArtworkDetail(view, it)
                }
            }
        }

    }

    private fun loadArtworkDetail(view: View, artwork: Artwork) {
        val titleView = view.findViewById<TextView>(R.id.detail_title)
        val artistView = view.findViewById<TextView>(R.id.detail_artist)
        val isLovedView = view.findViewById<ImageView>(R.id.detail_loved)
        val descriptionView = view.findViewById<TextView>(R.id.detail_description)
        val artView : ImageView = view.findViewById<ImageView>(R.id.detail_image)

        titleView.text = artwork.title
        artistView.text = artwork.artistDisplay
        artwork.description?.let { descriptionView.text = Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT) }
        setLoveImageView(artwork.isLoved, isLovedView)
        artwork.imageId?.let { loadArtwork(artView, it) }

        isLovedView.setOnClickListener {
            viewModel.updateIsLoved()
        }
    }

    private fun setLoveImageView(isLoved: Boolean?, imageView: ImageView) {
        if (isLoved == true) {
            imageView.setImageResource(R.drawable.ic_heart)
        } else {
            imageView.setImageResource(R.drawable.ic_heart_outline)
        }
    }

    // Load artwork image via url using Picasso
    private fun loadArtwork(view: ImageView, artworkId: String) {
        try {
            val url = "https://www.artic.edu/iiif/2/${artworkId}/full/843,/0/default.jpg"
            Picasso.get()
                .load(url)
                .into(view)
        } catch (exception: Exception) {
            Log.w("DetailScreen", "Exception at loadArtwork($artworkId): $exception")
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
