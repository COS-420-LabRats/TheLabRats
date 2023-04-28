package com.example.myapplication

open class User (ID: Int, Password: String, Email:String) {
    val ID: Int
        get() {
            return this.ID
        }
    var Password: String
        get() {
            TODO()
        }
        set(value) {}
    val Email: String
        get() {
            TODO()
        }
    var connectedUID : Int
        get() {
            return this.connectedUID
        }
        set(value) {
            this.connectedUID = value
        }




}
