package com.example.pickfresh.responses

import com.example.pickfresh.models.Items
import com.example.pickfresh.models.Seconditem

data class GoodResponse (var error:Boolean,var messsage:String,var data:ArrayList<Items>,
var second:ArrayList<Seconditem>)