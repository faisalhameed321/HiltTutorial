package com.example.dependecy_injection.ui.adopter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dependecy_injection.R
import com.example.dependecy_injection.data.model.StatusModel
import com.example.dependecy_injection.databinding.ImageItemsBinding

class FileAdapter :
    RecyclerView.Adapter<FileAdapter.FileViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val binding = ImageItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val model = differ.currentList[position]
        with(holder.binding) {
            img.load(model.path) {
                placeholder(R.drawable.image_placeholder)
                this.crossfade(true)
                crossfade(400)
            }

        }
    }

    override fun getItemCount() = differ.currentList.size

    class FileViewHolder(val binding: ImageItemsBinding) :
        RecyclerView.ViewHolder(binding.root)


    val diffCallBack = object : DiffUtil.ItemCallback<StatusModel>() {
        override fun areItemsTheSame(oldItem: StatusModel, newItem: StatusModel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: StatusModel, newItem: StatusModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallBack)
}