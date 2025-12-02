package com.example.pickfresh.responses

import com.google.gson.annotations.SerializedName

data class UserResponsesForIt (

    @SerializedName("error"   ) var error   : Boolean?        = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<Data> = arrayListOf()
){
    data class Data(
        @SerializedName("id"        ) var id        : String? = null,
        @SerializedName("itemId"    ) var itemId    : String? = null,
        @SerializedName("userId"    ) var userId    : String? = null,
        @SerializedName("status"    ) var status    : String? = null,
        @SerializedName("qty"       ) var qty       : String? = null,
        @SerializedName("dateOn"    ) var dateOn    : String? = null,
        @SerializedName("name"      ) var name      : String? = null,
        @SerializedName("image"     ) var image     : String? = null,
        @SerializedName("cost"      ) var cost      : String? = null,
        @SerializedName("mail"      ) var mail      : String? = null,
        @SerializedName("mobile"    ) var mobile    : String? = null,
        @SerializedName("password"  ) var password  : String? = null,
        @SerializedName("location"  ) var location  : String? = null,
        @SerializedName("type"      ) var type      : String? = null,
        @SerializedName("date"      ) var date      : String? = null,
        @SerializedName("state"     ) var state     : String? = null,
        @SerializedName("address"   ) var address   : String? = null,
        @SerializedName("productID" ) var productID : String? = null
    )
}
