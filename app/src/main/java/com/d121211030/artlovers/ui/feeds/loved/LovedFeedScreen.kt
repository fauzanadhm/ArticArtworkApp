package com.d121211030.artlovers.ui.feeds.loved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.d121211030.artlovers.R
import com.d121211030.artlovers.adapter.ArtFeedAdapter
import com.d121211030.artlovers.ui.feeds.FeedScreen
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment for loved artwork feed
 */
@AndroidEntryPoint
class LovedFeedScreen : FeedScreen() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.loved_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // empty view
        val emptyView : TextView = view.findViewById(R.id.empty_love_view)
        emptyView.visibility = View.VISIBLE

        // feed recycler view and adapter
        val artFeedAdapter = ArtFeedAdapter(
            lovePressedCallback = {artwork -> updateIsLoved(artwork)},
            detailPressedCallback = {artwork -> navigateToDetail(artwork)},
            )
        val recyclerView : RecyclerView = view.findViewById(R.id.loved_list)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = artFeedAdapter

        // observe loved artworks to update adapter when it changes
        viewModel.lovedArtworks.observe(viewLifecycleOwner) { artworkList ->
            if (artworkList.isNullOrEmpty()) {
                emptyView.visibility = View.VISIBLE
            } else {
                emptyView.visibility = View.GONE
                artFeedAdapter.updateListItems(artworkList)
            }
        }
    }
}