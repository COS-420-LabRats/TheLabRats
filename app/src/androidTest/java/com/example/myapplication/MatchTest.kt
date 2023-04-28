package com.example.myapplication

import org.junit.Assert.*
import org.junit.Test

class MatchTest(){

    @Test
    fun noSwipes(){
        val testUserA = User(420, "email", "password")
        val testUserB = User(421, "other email", "other password")
        val testDirection = "down"

        val testMatch = Match()
        assertFalse(testMatch.Swipe(testUserA, testUserB, testDirection))


    }

}

