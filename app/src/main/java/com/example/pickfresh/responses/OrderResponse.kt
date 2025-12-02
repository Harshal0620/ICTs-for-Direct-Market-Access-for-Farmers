package com.example.pickfresh.responses

import com.example.pickfresh.models.Orders

data class OrderResponse (var error:Boolean,var message:String,var data:ArrayList<Orders>)