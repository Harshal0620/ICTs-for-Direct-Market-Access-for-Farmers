package com.example.pickfresh.responses

import com.example.pickfresh.models.Items

data class ProductResponse (var error:Boolean,var message:String,var data:ArrayList<Items> )