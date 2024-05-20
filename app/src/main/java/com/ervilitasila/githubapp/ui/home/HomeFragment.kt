package com.ervilitasila.githubapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ervilitasila.githubapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val userViewModel : UserViewModel by viewModels()
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
//        val action = HomeFragmentDirections.actionHomeFragmentToDatailUserFragment(userName)
//        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }
}