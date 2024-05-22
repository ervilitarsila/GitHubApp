package com.ervilitasila.githubapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ervilitasila.githubapp.databinding.FragmentHomeBinding
import com.ervilitasila.githubapp.model.User
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val userViewModel : UserViewModel by viewModel()
    private var viewBinding: FragmentHomeBinding? = null
    private var selectedUser: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(selectedUser != null) {
            postponeEnterTransition()
        }

        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(context, 2)
        viewBinding?.recyclerUsers?.layoutManager = layoutManager
//        setMockData()
        userViewModel.listUsers.observe(viewLifecycleOwner, Observer { users ->
            viewBinding?.recyclerUsers?.adapter = UserAdapters(
                context,
                users,
                itemClickListener = { user, viewHolder ->
                    selectedUser = user
                    navigateToUserDetailFragment(selectedUser!!)
                })
        })
    }

    private fun navigateToUserDetailFragment(userName: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToUserDetailFragment(userName)
        findNavController().navigate(action)
    }

    private fun setMockData(){
        val user = listOf(
            User(1, "teste1", "https://avatars.githubusercontent.com/u/1?v=4", "www.google.com", null),
            User(1, "teste2", "https://avatars.githubusercontent.com/u/2?v=4", "www.google.com", null),
            User(1, "teste3", "https://avatars.githubusercontent.com/u/3?v=4", "www.google.com", null),
            User(1, "teste4", "https://avatars.githubusercontent.com/u/4?v=4", "www.google.com", null),
        )

        viewBinding?.recyclerUsers?.adapter = UserAdapters(
            context,
            user,
            itemClickListener = { user, viewHolder ->
                selectedUser = user
                navigateToUserDetailFragment(selectedUser!!)
            })
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }
}