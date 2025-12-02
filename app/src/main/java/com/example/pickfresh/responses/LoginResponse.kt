package com.example.pickfresh.responses

import com.example.pickfresh.models.User

data class LoginResponse (var error:Boolean,var message:String,var data:ArrayList<User>)