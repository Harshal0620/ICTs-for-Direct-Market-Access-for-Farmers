package com.example.pickfresh.responses

import com.example.pickfresh.models.Orders
import com.example.pickfresh.models.Review

data class CustomeResponse (var error:Boolean,var message:String,var data:ArrayList<Orders>,
var data2:ArrayList<Review>)