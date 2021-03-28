package com.gustavo.architectureapp.utils.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.gustavo.architectureapp.model.games.GameItem

class GamesDiffCallback(
    private val oldGameList: List<GameItem>,
    private val newGameList: List<GameItem>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldGameList.size

    override fun getNewListSize() = newGameList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldGameList[oldItemPosition].id == newGameList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldGameList[oldItemPosition] == newGameList[newItemPosition]
}