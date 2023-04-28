package com.example.myapplication

import org.junit.Assert.*

import org.junit.Test

class MergeAccountTest {

    @Test
    fun mergeAccountsIsTrue() {
        val UserA = User(304, "Asample", "Bsample")
        val UserB = User(305, "Csample", "Dsample")

        val MergeAccountAttempt =  MergeAccount()

        assertTrue(MergeAccountAttempt.MergeAccounts(UserA, UserB))
    }
}
