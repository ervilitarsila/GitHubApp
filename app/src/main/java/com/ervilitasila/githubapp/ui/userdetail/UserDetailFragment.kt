package com.ervilitasila.githubapp.ui.userdetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ervilitasila.githubapp.R

class UserDetailFragment : Fragment() {

    private var userName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            userName = it.getString("userName")
        }
        Log.i("etbs","DetailUser= $userName")

        return inflater.inflate(R.layout.fragment_datail_user, container, false)
    }
}