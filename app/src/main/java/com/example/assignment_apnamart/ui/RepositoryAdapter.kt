package com.example.assignment_apnamart.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment_apnamart.R
import com.example.assignment_apnamart.network.model.GithubRepo
import kotlinx.android.synthetic.main.item_repo.view.*

class RepositoryAdapter(private var context: Context) :
    ListAdapter<GithubRepo, RepositoryAdapter.MyViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_repo, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listItem: GithubRepo = getItem(position)
        //listItem.expanded = false
        holder.onbind(listItem)
        holder.itemView.setOnClickListener {
            this.currentList.forEach {
                if (it == listItem)
                    listItem.expanded = !listItem.expanded
                else
                    it.expanded = false
            }
            //listItem.expanded = !listItem.expanded
            notifyDataSetChanged()
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onbind(item: GithubRepo) {
            Glide.with(itemView)
                .load(item.owner.avatar_url)
                .centerCrop()
                .error(android.R.drawable.stat_notify_error)
                .placeholder(android.R.drawable.stat_sys_warning)
                .into(itemView.profilePic)
            itemView.userName.text = item.name
            itemView.repoName.text = item.owner.login
            itemView.repoDetail.text = item.description
            itemView.expandedView.visibility = if (item.expanded) View.VISIBLE else View.GONE
            itemView.language.text = item.language
            itemView.stargazersCount.text = item.starsCount.toString()
            itemView.forkCount.text = item.forksCount.toString()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<GithubRepo>() {
        override fun areItemsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
            return oldItem.id == newItem.id
        }
    }
}