package com.example.pickfresh.responses

import com.google.gson.annotations.SerializedName

data class FertiProducts (
    @SerializedName("error"   ) var error   : Boolean?        = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<Data> = arrayListOf()

){
    data class Data(
        @SerializedName("id"    ) var id    : String? = null,
        @SerializedName("name"  ) var name  : String? = null,
        @SerializedName("image" ) var image : String? = null,
        @SerializedName("cost"  ) var cost  : String? = null
    )
}
