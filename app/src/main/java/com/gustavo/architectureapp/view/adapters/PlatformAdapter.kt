package com.gustavo.architectureapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.gustavo.architectureapp.R
import com.gustavo.architectureapp.data.games.PlatformGrid
import com.gustavo.architectureapp.view.fragments.PlatformListFragmentDirections
import kotlinx.android.synthetic.main.platform_item.view.*

internal class PlatformAdapter(
    private val platformList: ArrayList<PlatformGrid>
) : RecyclerView.Adapter<PlatformAdapter.PlatformViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : PlatformViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.platform_item, parent, false)
        return PlatformViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlatformViewHolder, position: Int) {
        val platform = platformList[position]
        holder.bind(platform)
    }

    override fun getItemCount() = platformList.size

    class PlatformViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(platform: PlatformGrid) {

            view.apply {

                platformLogoImageView.setImageResource(platform.imageResourceId)

                platformCardView.setOnClickListener {
                    Navigation.findNavController(it).navigate(
                            PlatformListFragmentDirections.actionToGameListFragment(
                                    platform.platformId)
                    )
                }

            }
        }

    }

}
