package com.ervilitasila.githubapp.ui.userdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ervilitasila.githubapp.R
import com.ervilitasila.githubapp.databinding.FragmentDatailUserBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailFragment : Fragment() {
    private val userDetailViewModel: UserDetailViewModel by viewModel()
    private var userName: String? = null
    private var viewBinding: FragmentDatailUserBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentDatailUserBinding.inflate(inflater, container, false)
        arguments?.let {
            userName = it.getString("userName")
        }

        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userName?.let {
            userDetailViewModel.getDetailUserSelected(it)
            userDetailViewModel.getDetailUserSelectedRepositories(it)
        }

        loadingDetailUser()
        setupBackButton()
    }

    private fun loadingDetailUser() {
        userDetailViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            viewBinding?.loadingOverlay?.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        userDetailViewModel.userSelected.observe(viewLifecycleOwner, Observer { user ->
            with(viewBinding) {
                if (user == null) {
                    showErrorDialog("User not found")
                } else {
                    this?.userName?.text = user.name
                    this?.userLogin?.text = user.login
                    this?.userLocation?.text = user.location
                    this?.userCompany?.text = user.company
                    this?.userFollowers?.text = user.followers.toString()
                    this?.userFollowing?.text = user.following.toString()
                    this?.userProfile?.let {
                        Glide.with(requireView())
                            .load(user.avatar_url)
                            .error(R.drawable.splash_bg)
                            .into(it)
                    }
                }
            }
        })

        userDetailViewModel.listUserRepositories.observe(viewLifecycleOwner, Observer { listRepos ->
            with(viewBinding) {
                this?.recyclerRepositories?.layoutManager = LinearLayoutManager(context)
                this?.recyclerRepositories?.adapter = RepositoryAdapters(
                    context,
                    listRepos
                )
            }
        })

        userDetailViewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                showErrorDialog(it)
            }
        })
    }

    private fun showErrorDialog(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("Try Again") { _, _ ->
                userName?.let {
                    userDetailViewModel.getDetailUserSelected(it)
                    userDetailViewModel.getDetailUserSelectedRepositories(it)
                }
            }
            .setNegativeButton("Back to Home") { _, _ ->
                findNavController().navigate(R.id.action_userDetailFragment_to_homeFragment)
            }
            .show()
    }

    private fun setupBackButton() {
        viewBinding?.btnBack?.setOnClickListener {
            findNavController().navigate(R.id.action_userDetailFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }
}
