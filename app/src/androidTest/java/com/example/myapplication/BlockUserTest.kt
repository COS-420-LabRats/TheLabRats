package com.example.myapplication

import org.junit.Assert.*

import org.junit.Test

class BlockUserTest {

    @Test
    fun testDoBlock() {
        val buTest =  BlockUser()

        val User1 = User(4, "testPassA", "testEmailA")
        val User2 = User(5, "testPassB", "testEmailB")

        assertTrue(buTest.doBlock(User1, User2))
    }
}