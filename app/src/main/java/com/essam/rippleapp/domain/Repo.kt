package com.essam.rippleapp.domain

data class Repo(val id: Long, val name: String?, val description: String?, val owner: Owner?) {
    data class Owner(val avatar_url: String?)
}
