package com.example.bookxperttest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookxperttest.R
import com.example.bookxperttest.models.ApiObject

class ApiObjectAdapter(
    private val onEditClick: (ApiObject) -> Unit,
    private val onDeleteClick: (ApiObject) -> Unit
) : ListAdapter<ApiObject, ApiObjectAdapter.ApiObjectViewHolder>(ApiDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiObjectViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_api_object, parent, false)
        return ApiObjectViewHolder(view, onEditClick, onDeleteClick)
    }

    override fun onBindViewHolder(holder: ApiObjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ApiObjectViewHolder(
        itemView: View,
        private val onEditClick: (ApiObject) -> Unit,
        private val onDeleteClick: (ApiObject) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val nameText: TextView = itemView.findViewById(R.id.textName)
        private val dataText: TextView = itemView.findViewById(R.id.textData)
        private val optionsMenu: ImageView = itemView.findViewById(R.id.imgOptions)

        fun bind(apiObject: ApiObject) {
            nameText.text = apiObject.name
            dataText.text = apiObject.data.toString()

            optionsMenu.setOnClickListener { view ->
                val popup = PopupMenu(view.context, view)
                popup.menuInflater.inflate(R.menu.menu_item_options, popup.menu)
                popup.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.action_edit -> {
                            onEditClick(apiObject)
                            true
                        }
                        R.id.action_delete -> {
                            onDeleteClick(apiObject)
                            true
                        }
                        else -> false
                    }
                }
                popup.show()
            }
        }
    }

    class ApiDiffCallback : DiffUtil.ItemCallback<ApiObject>() {
        override fun areItemsTheSame(oldItem: ApiObject, newItem: ApiObject): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ApiObject, newItem: ApiObject): Boolean {
            return oldItem == newItem
        }
    }
}