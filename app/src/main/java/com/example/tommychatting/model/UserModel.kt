package com.example.tommychatting.model

class UserModel() {
    var uid: String = ""
//    var username: String =""
    var email: String = ""
    var token: String = ""

    constructor(uid: String, email: String, token: String = "") : this() {
        this.uid = uid
//        this.username = username
        this.email = email
        this.token = token
    }
}