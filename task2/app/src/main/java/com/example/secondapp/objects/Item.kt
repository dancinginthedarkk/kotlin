package com.example.secondapp.objects

import com.google.gson.annotations.SerializedName

data class Item (
    @SerializedName("_id")
    val url : String
)