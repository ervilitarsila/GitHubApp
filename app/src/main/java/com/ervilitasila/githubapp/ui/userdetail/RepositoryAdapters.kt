package com.ervilitasila.githubapp.ui.userdetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView
import com.ervilitasila.githubapp.R
import com.ervilitasila.githubapp.databinding.ItemRepositoryBinding
import com.ervilitasila.githubapp.databinding.ItemUserBinding
import com.ervilitasila.githubapp.model.Repository

class RepositoryAdapters(
    val context: Context?,
    val listRepositories: List<Repository>
    ) : RecyclerView.Adapter<RepositoryAdapters.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewBinding = ItemRepositoryBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = LayoutInflater.from(context).inflate(R.layout.item_repository, parent, false)
        return ViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return listRepositories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = listRepositories[position]

        with(holder.viewBinding){
            this.repositoryName.text = repository.name
            this.repositoryVisibility.text = repository.visibility
            this.repositoryDescription.text = repository.description
            this.repositoryLanguage.text = repository.language
            this.repositoryWatchers.text = repository.watchers.toString()
            this.repositoryForks.text = repository.forks.toString()
        }
    }
}