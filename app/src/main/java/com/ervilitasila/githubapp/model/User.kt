package com.ervilitasila.githubapp.model

data class User(
    var id: Int = 0,
    var login: String,
    var avatar_url: String,
    var url: String,
    var userProfile: UserProfile?
)
