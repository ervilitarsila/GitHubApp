package com.ervilitasila.githubapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ervilitasila.githubapp.R
import com.ervilitasila.githubapp.databinding.ItemUserBinding
import com.ervilitasila.githubapp.model.User

class UserAdapters(
    private val context: Context?,
    private var userList: List<User>,
    val itemClickListener: OnItemClickedListener? = null
) : RecyclerView.Adapter<UserAdapters.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: User = userList[position]

        with(holder) {
            viewBinding.userName.text = user.login
            Glide.with(this.itemView)
                .load(user.avatar_url)
                .error(R.drawable.splash_bg)
                .into(viewBinding.userProfile)

            viewBinding.itemUser.setOnClickListener {
                itemClickListener?.invoke(user.login, this)
            }
        }
    }

    fun updateUsers(newUsers: List<User>) {
        userList = newUsers
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewBinding = ItemUserBinding.bind(itemView)
    }
}

typealias OnItemClickedListener = (userName: String, viewHolder: UserAdapters.ViewHolder) -> Unit
