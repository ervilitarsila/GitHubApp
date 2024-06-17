package com.ervilitasila.githubapp.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ervilitasila.githubapp.R
import com.ervilitasila.githubapp.databinding.FragmentHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val userViewModel: UserViewModel by viewModel()
    private var viewBinding: FragmentHomeBinding? = null
    private var selectedUser: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (selectedUser != null) {
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

        userViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            viewBinding?.loadingFrame?.visibility = if (isLoading) View.VISIBLE else View.GONE
            viewBinding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        userViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (errorMessage != null) {
                showErrorDialog("Users  Loading Failed. Please try again.")
            }
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

    private fun showErrorDialog(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("Try Again") { _, _ ->
                userViewModel.getListUsers()
            }
            .show()
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }
}
