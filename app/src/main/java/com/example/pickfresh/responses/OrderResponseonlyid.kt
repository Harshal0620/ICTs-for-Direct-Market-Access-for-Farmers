package com.example.pickfresh.responses

import com.example.pickfresh.models.Orderid

data class OrderResponseonlyid (var id:Boolean,var message:String,
var data:ArrayList<Orderid>)