package com.ervilitasila.githubapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBindings
import com.ervilitasila.githubapp.R
import com.ervilitasila.githubapp.databinding.ActivityMainBinding
import com.ervilitasila.githubapp.databinding.FragmentHomeBinding
import com.ervilitasila.githubapp.model.User
import com.ervilitasila.githubapp.ui.adapters.UserAdapters


class HomeFragment : Fragment() {

    private var recyclerView: RecyclerView ? = null
    private var gridLayoutManager: GridLayoutManager ? = null
    private var userList: ArrayList<User> ? = null
    private var userAdapters: UserAdapters? = null
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        recyclerView = binding.recyclerUsers
        gridLayoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(true)
        userList = setDataInList()
        userAdapters = UserAdapters(context, userList!!)
        recyclerView?.adapter = userAdapters

        return binding.root
    }

    private fun setDataInList(): ArrayList<User>{
        var userList:ArrayList<User> = ArrayList()
        Log.i("etbs", "setDataInList")
        userList.add(User(1, "ervili", R.drawable.splash_bg, "www.github.com/ervilitarsila",null))
        userList.add(User(1, "ervili2", R.drawable.splash_bg, "www.github.com/ervilitarsila",null))
        userList.add(User(1, "ervili3", R.drawable.splash_bg, "www.github.com/ervilitarsila",null))
        userList.add(User(1, "ervili4", R.drawable.splash_bg, "www.github.com/ervilitarsila",null))
        userList.add(User(1, "ervili5", R.drawable.splash_bg, "www.github.com/ervilitarsila",null))
        userList.add(User(1, "ervili6", R.drawable.splash_bg, "www.github.com/ervilitarsila",null))
        userList.add(User(1, "ervili", R.drawable.splash_bg, "www.github.com/ervilitarsila",null))
        userList.add(User(1, "ervili2", R.drawable.splash_bg, "www.github.com/ervilitarsila",null))
        userList.add(User(1, "ervili3", R.drawable.splash_bg, "www.github.com/ervilitarsila",null))
        userList.add(User(1, "ervili4", R.drawable.splash_bg, "www.github.com/ervilitarsila",null))
        userList.add(User(1, "ervili5", R.drawable.splash_bg, "www.github.com/ervilitarsila",null))
        userList.add(User(1, "ervili6", R.drawable.splash_bg, "www.github.com/ervilitarsila",null))

        return userList
    }
}