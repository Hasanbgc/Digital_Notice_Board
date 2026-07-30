package com.hasan.dnb.session

interface SessionStorage {
    suspend fun save(session: Session)
    fun get(): Session?
    suspend fun clear()
}