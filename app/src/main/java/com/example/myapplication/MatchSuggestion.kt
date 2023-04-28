package com.example.myapplication

class MatchSuggestion() {
    var suggestList = arrayOf<User>()
    fun matSug(User1: User, User2: User, compScore: Double): Boolean {
        //compScore will be computed in a separate function

        if (compScore >= 0.6){
            this.suggestList += User1
            this.suggestList += User2
            return true
        }
        else{
            return false
        }

    }
}
