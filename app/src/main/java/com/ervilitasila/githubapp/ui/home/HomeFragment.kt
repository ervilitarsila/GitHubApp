package com.ervilitasila.githubapp.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        val adapter = UserAdapters(
            context,
            listOf(),
            itemClickListener = { user, viewHolder ->
                selectedUser = user
                navigateToUserDetailFragment(selectedUser!!)
            }
        )
        viewBinding?.recyclerUsers?.adapter = adapter

        userViewModel.listUsers.observe(viewLifecycleOwner, Observer { users ->
            adapter.updateUsers(users)
        })

        viewBinding?.searchIcon?.setOnClickListener {
            val query = viewBinding?.searchInput?.text.toString()
            userViewModel.filterUsers(query)
        }

        viewBinding?.searchInput?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                userViewModel.filterUsers(query)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun navigateToUserDetailFragment(userName: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToUserDetailFragment(userName)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }
}
