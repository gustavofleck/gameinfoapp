package com.gustavo.architectureapp.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gustavo.architectureapp.R
import com.gustavo.architectureapp.data.model.GameScreenshot
import com.gustavo.architectureapp.data.model.GameScreenshotResponse
import com.gustavo.architectureapp.data.storage.FileManager
import com.gustavo.architectureapp.utils.image.ImageLoader
import kotlinx.android.synthetic.main.grid_image_item.view.*

internal class GameDetailsImageAdapter(
    private val imageLoader: ImageLoader,
    private val context: Context
) : RecyclerView.Adapter<GameDetailsImageAdapter.GameImageViewHolder>() {

    private var screenshotList = listOf<GameScreenshot>()

    fun updateScreenshotList(screenshotList: List<GameScreenshot>) {
        this.screenshotList = screenshotList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameImageViewHolder {
        return GameImageViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.grid_image_item, parent, false),
            imageLoader,
            FileManager(context)
        )
    }

    override fun onBindViewHolder(holder: GameImageViewHolder, position: Int) {
        val screenshot = screenshotList[position]
        holder.bind(screenshot)
    }

    override fun getItemCount() = screenshotList.size

    internal class GameImageViewHolder(
        val view: View,
        private val imageLoader: ImageLoader,
        private val fileManager: FileManager
    ): RecyclerView.ViewHolder(view) {

        fun bind(screenshot: GameScreenshot) {
            with(view.gameDetailsGridImageItem) {
                imageLoader.loadSquareImage(this, screenshot.imageUri)
                setOnClickListener {
                    fileManager.createShareableImageFile(this)
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        putExtra(Intent.EXTRA_STREAM, fileManager.createUriForShareableFile())
                        type = "image/jpeg"
                    }
                    context.startActivity(shareIntent)
                }
            }
        }

    }
}