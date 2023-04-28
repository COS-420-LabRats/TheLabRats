package com.example.myapplication

class BlockUser () {
    var blockedUsers = arrayOf<User>()
    fun doBlock (blockingUser: User, blockedUser: User): Boolean {

        val first = blockedUsers.size

        this.blockedUsers += blockingUser
        this.blockedUsers += blockedUser

        val second = blockedUsers.size
        if (second == first + 2){
            return true
        }
        else{
            return false
        }



    }
}
