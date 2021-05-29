package com.decagon.android.sq007.model

import com.decagon.android.sq007.model.userprops.Address
import com.decagon.android.sq007.model.userprops.Company

data class User(
    val address: Address,
    val company: Company,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
)