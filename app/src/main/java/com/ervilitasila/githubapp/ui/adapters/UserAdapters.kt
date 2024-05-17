package com.ervilitasila.githubapp.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ervilitasila.githubapp.R
import com.ervilitasila.githubapp.databinding.ItemUserBinding
import com.ervilitasila.githubapp.model.User

class UserAdapters(
    var context: Context?,
    var userList: ArrayList<User>
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
            viewBinding.userProfile.setImageResource(R.drawable.splash)
            viewBinding.itemUser.setOnClickListener{
                Toast.makeText(context, user.login, Toast.LENGTH_LONG).show()
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewBinding = ItemUserBinding.bind(itemView)
    }

}