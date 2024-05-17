package com.ervilitasila.githubapp.model

data class UserProfile(
    var repos_url: String,
    var name: String,
    var company : String,
    var location: String,
    var followers: Int,
    var following: Int,
    var repositories: MutableList<Repository>
)

