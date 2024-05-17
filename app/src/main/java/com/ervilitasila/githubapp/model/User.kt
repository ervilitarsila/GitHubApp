package com.ervilitasila.githubapp.model

data class User(
    var id: Int = 0,
    var login: String,
    var avatar_url: Int,
    var url: String,
    var userProfile: UserProfile?
)
