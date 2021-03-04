package com.essam.rippleapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.essam.rippleapp.R
import com.essam.rippleapp.domain.Repo

class ReposAdapter() : RecyclerView.Adapter<ReposAdapter.ReposViewHolder>(){

    private var dataList: MutableList<Repo> = ArrayList()
    fun setDataList(list:List<Repo>) {
        dataList = list as MutableList<Repo>
        notifyDataSetChanged()
    }

    fun addItemsToTheList(list: List<Repo>) {
        val lastPosition = dataList.size - 1
        dataList.addAll(list)
        notifyItemRangeInserted(lastPosition, list.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        return ReposViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.bindTo(dataList[position])
    }

    class ReposViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val repoName: TextView = itemView.findViewById(R.id.repoName_textView)
        private val repoDescription: TextView = itemView.findViewById(R.id.repoDescription_textView)
        private val avatar: ImageView = itemView.findViewById(R.id.ownerAvatar_imageView)

        fun bindTo(repo: Repo?) {
            repoName.text = repo?.name
            repoDescription.text = repo?.description
            Glide.with(itemView)
                .load(repo?.owner?.avatar_url)
                .transform(CircleCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(avatar)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
