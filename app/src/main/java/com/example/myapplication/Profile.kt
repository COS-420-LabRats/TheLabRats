package com.example.myapplication

//Challenge faced: we need to figure out how to deal with .jpg and .png for the pictures
//right now they are represented as strings
class Profile(UID: Int, Name: String, DOB: String, Age: Int, Biography: String, Picture1: String,
Picture2: String, Picture3: String, Picture4: String, Picture5:String,
              Preferences: Preferences, Address1: Address, Address2: Address, AccessibilityReqs: String)
{
    fun getProfile(){
        println("Profile gotten")
    }
    fun setProfile(){
        println("Profile set")
    }
    fun verifyAge(DOB: String){
        println("Age verified")
    }
    fun addPictures(Picture1: String, Picture2: String, Picture3: String, Picture4: String, Picture5: String){
        println("Pictures Added")
    }
}



