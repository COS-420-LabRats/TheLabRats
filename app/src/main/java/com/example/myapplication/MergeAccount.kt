package com.example.myapplication

class MergeAccount() {

    fun MergeAccounts(User1 :User, User2: User): Boolean {
        var mergedAccounts = arrayOf<User>()

        var first = mergedAccounts.size

        mergedAccounts += User1
        mergedAccounts += User2

        var second = mergedAccounts.size

        if (first + 2 == second){
            return true
        }
        else{
            return true
        }

    }

}
