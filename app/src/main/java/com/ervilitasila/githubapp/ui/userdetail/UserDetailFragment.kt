package com.ervilitasila.githubapp.ui.userdetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ervilitasila.githubapp.R
import com.ervilitasila.githubapp.databinding.FragmentDatailUserBinding

class UserDetailFragment : Fragment() {
    private val userDetailViewModel: UserDetailViewModel by viewModels()
    private var userName: String? = null
    private var viewBinding: FragmentDatailUserBinding?= null

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

    private fun loadingDetailUser(){
        userDetailViewModel.userSelected.observe(viewLifecycleOwner, Observer { user ->
            with(viewBinding) {
                this?.userName?.text = user?.name
                this?.userLogin?.text = user?.login
                this?.userLocation?.text = user?.location
                this?.userCompany?.text = user?.company
                this?.userFollowers?.text = user?.followers.toString()
                this?.userFollowing?.text = user?.following.toString()
                this?.userProfile?.let {
                    Glide.with(requireView())
                        .load(user?.avatar_url)
                        .error(R.drawable.splash_bg)
                        .into(it)
                }
            }
        })

        userDetailViewModel.listUserRepositories.observe(viewLifecycleOwner, Observer { listRepos ->
            with(viewBinding){
                this?.recyclerRepositories?.layoutManager = LinearLayoutManager(context)
                this?.recyclerRepositories?.adapter = RepositoryAdapters(
                    context,
                    listRepos
                )
            }
        })
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