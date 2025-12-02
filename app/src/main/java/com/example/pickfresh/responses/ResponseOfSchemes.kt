package com.example.pickfresh.responses

import com.google.gson.annotations.SerializedName

data class ResponseOfSchemes (
    @SerializedName("error"   ) var error   : Boolean?        = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<SchemesView> = arrayListOf()

){
    data class SchemesView (
        @SerializedName("id"        ) var id        : String? = null,
        @SerializedName("url"       ) var url       : String? = null,
        @SerializedName("schemeDes" ) var schemeDes : String? = null,
        @SerializedName("dateUp"    ) var dateUp    : String? = null
    )
}
