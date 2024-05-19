package com.ervilitasila.githubapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ervilitasila.githubapp.R
import com.ervilitasila.githubapp.databinding.FragmentHomeBinding
import com.ervilitasila.githubapp.model.User


class HomeFragment : Fragment() {
    private val userViewModel : UserViewModel by viewModels()
    private var viewBinding: FragmentHomeBinding? = null
    private var selectedUserId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(selectedUserId != null) {
            postponeEnterTransition()
        }

        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(context, 2)
        viewBinding?.recyclerUsers?.layoutManager = layoutManager
        userViewModel.listUsers.observe(viewLifecycleOwner, Observer { users ->
            viewBinding?.recyclerUsers?.adapter = UserAdapters(context, users)
        })
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }
}