package com.example.myapplication

import org.junit.Assert.*

import org.junit.Test

class MatchSuggestionTest {

    @Test
    fun matSug() {

        var ms = MatchSuggestion()

        var UserA = User(45566, "testPassA", "testEmailA")
        var UserB = User(456, "testPassB", "testEmailB")

        var compScore = 65.0

        assertTrue(ms.matSug(UserA, UserB, compScore))


    }
}
