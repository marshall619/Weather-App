package com.example.weatherapp

data class Todo (var userid:Int,var id:Int,var title:String,var comp:Boolean){
    override fun toString(): String {
        return "Todo(userid=$userid, id=$id, title='$title', comp=$comp)"
    }
}