package com.example.pickfresh.responses

import com.example.pickfresh.models.Review

data class Reviewresponse (var error:Boolean,var message: String,var data:ArrayList<Review>)