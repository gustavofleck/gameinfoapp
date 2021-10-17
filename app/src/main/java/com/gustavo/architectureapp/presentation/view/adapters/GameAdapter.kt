package com.gustavo.architectureapp.presentation.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gustavo.architectureapp.R
import com.gustavo.architectureapp.data.model.GameItem
import com.gustavo.architectureapp.utils.diffutils.GamesDiffCallback
import com.gustavo.architectureapp.utils.image.ImageLoader
import com.gustavo.architectureapp.presentation.view.fragments.GameListFragmentDirections
import kotlinx.android.synthetic.main.game_item.view.*

internal class GameAdapter(
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    private var gameList = listOf<GameItem>()

    fun addNewGameList(gameList: List<GameItem>) {
        val newGameList = this.gameList + gameList
        val diffResult = DiffUtil.calculateDiff(GamesDiffCallback(this.gameList, newGameList))

        this.gameList = newGameList

        diffResult.dispatchUpdatesTo(this)
    }

    fun updateGameList(gameList: List<GameItem>) {
        this.gameList = gameList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.game_item, parent, false),
            imageLoader
        )
    }

    override fun getItemCount() = gameList.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = gameList[position]
        holder.bind(game)

    }

    internal class GameViewHolder(
        val view: View,
        private val imageLoader: ImageLoader
    ) : RecyclerView.ViewHolder(view) {

        fun bind(gameItem: GameItem) {

            view.apply {

                gameNameTextView.text = gameItem.name

                imageLoader.loadCircleImage(gameImageView, gameItem.backgroundImageUri)

                gameCardView.setOnClickListener {
                    Navigation.findNavController(it).navigate(GameListFragmentDirections.actionGameListFragmentToGameDetailsFragment(gameItem.id))
                }
            }

        }
    }

}
