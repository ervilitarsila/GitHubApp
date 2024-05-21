package com.ervilitasila.githubapp.model

data class Repository(
    var id: Int = 0,
    var name: String,
    var language:  String,
    var forks: Int,
    var watchers: Int,
    var visibility: String,
    var description: String
)

