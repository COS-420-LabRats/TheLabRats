package com.example.myapplication

import org.junit.Assert.*

import org.junit.Test

class ProfileTest {

    @Test
    fun verifyAgeTest() {
        val testAddress1 = Address()
        val testAddress2 = Address()
        val testPref = Preferences(4, true, "hello", 4.2f, true, true, true, true)
        val testDOB = "2000"
        val testProfile = Profile(420, "test", "test1", 421, "test2", "test4", "test5", "test6", "test7", "test8", testPref, testAddress1, testAddress2, "stringgy")

        assertTrue(testProfile.verifyAge(testDOB))
    }

}
