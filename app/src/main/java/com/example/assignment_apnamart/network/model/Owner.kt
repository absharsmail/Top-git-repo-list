package com.example.assignment_apnamart.network.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Owner(
    val login: String,
    val avatar_url: String
): Parcelable