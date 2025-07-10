package com.example.data.database

import com.example.data.util.Constant.MONGO_DATABASE_NAME
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase

object DatabaseFactory {
    fun create(): MongoDatabase{
        val connectionString = System.getenv("MONGO_URI") ?: throw IllegalArgumentException("MONGO_URI is not set")
        val mongoClient = MongoClient.create(connectionString)
        return mongoClient.getDatabase(MONGO_DATABASE_NAME)
    }
}