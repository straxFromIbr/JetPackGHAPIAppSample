package com.straxfromibr.jetpackghapi.model.repository

interface UserRepository
{
    suspend fun getUser(userName: String): User
}