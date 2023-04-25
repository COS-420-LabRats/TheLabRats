package com.example.myapplication

class Preferences(UID: Int, HasAddress: Boolean, Job: String, Rent: Float, CriminalHistory: Boolean,
                  CriminalBackground: Boolean, Pets: Boolean, Smoker: Boolean) {
    val UID: Int
        get() {
            TODO()
        }
    var HasAddress: Boolean
        get() {
            TODO()
        }
        set(value) {}
    var Job: String
        get() {
            TODO()
        }
        set(value) {}
    var Rent: Float
        get() {
            TODO()
        }
        set(value) {}
    var CriminalHistory: Boolean
        get() {
            TODO()
        }
        set(value) {}
    var CriminalBackground: Boolean
        get() {
            TODO()
        }
        set(value) {}
    var Pets: Boolean
        get() {
            TODO()
        }
        set(value) {}
    var Smoker: Boolean
        get() {
            TODO()
        }
        set(value) {}

    fun login(UID: Int, Password:String, email:String){
        println("Login success")
    }

    fun logout(){
        println("Logged out")
    }

    fun deleteUser(){
        println("User deleted")
    }


}