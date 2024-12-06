package com.example.englishcoreappk.Retrofit

import kotlin.collections.mutableListOf

object UserData {
    var User=""
    var TeachersGroups: MutableList<Groups> = mutableListOf()
    fun setUpUSR(Nuser: String){
        User= Nuser
    }
    fun SetupGroups(Groups: MutableList<Groups>){
        this.TeachersGroups= Groups
    }
}