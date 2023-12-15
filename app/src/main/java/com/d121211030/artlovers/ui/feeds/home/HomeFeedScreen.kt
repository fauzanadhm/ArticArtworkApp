package com.d121211030.artlovers.ui.feeds.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.d121211030.artlovers.R
import com.d121211030.artlovers.adapter.ArtFeedAdapter
import com.d121211030.artlovers.ui.feeds.FeedScreen
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import java.util.TimerTask

/**
 * Fragment for artwork home feed
 */
@AndroidEntryPoint
class HomeFeedScreen : FeedScreen() {

    private lateinit var searchTextView : EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set up recycler view and adapter
        val artFeedAdapter = ArtFeedAdapter(
           lovePressedCallback = { artwork -> updateIsLoved(artwork)},
           detailPressedCallback = { artwork -> navigateToDetail(artwork)},
        )
        val recyclerView : RecyclerView = view.findViewById(R.id.home_list)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = artFeedAdapter

        // observe home artworks to update adapter when it changes
        viewModel.homeArtworks.observe(viewLifecycleOwner) { artworkList ->
            artworkList?.let {
                artFeedAdapter.updateListItems(it)
            }
        }

        // observe loved artworks to reload feed when it changes
        viewModel.lovedArtworks.observe(viewLifecycleOwner) {
            reloadFeed()
        }

        // search bar
        searchTextView = view.findViewById(R.id.search_bar)
        searchTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            var timer = Timer()
            override fun afterTextChanged(s: Editable?) {
                timer.cancel()
                timer = Timer()
                timer.schedule(
                    object : TimerTask() {
                        override fun run() {
                            s?.let { viewModel.loadHomeArtwork(s.toString()) }
                        }
                    },
                    DELAY
                )
            }
        })
    }

    // TODO - instead of making network calls each time, update the view model
    // Reloads the home feed whenever an artwork isLoved is changed
    private fun reloadFeed() {
        viewModel.loadHomeArtwork(searchTextView.text.toString())
    }

    companion object {
        const val DELAY = 1000L
    }

}