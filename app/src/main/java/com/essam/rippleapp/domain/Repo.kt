package com.essam.rippleapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repo(val id: Long, val name: String?, val description: String?, val owner: Owner?) :
    Parcelable {
    @Parcelize
    data class Owner(val avatar_url: String?) : Parcelable
}
