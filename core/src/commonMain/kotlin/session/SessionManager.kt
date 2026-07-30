package com.hasan.dnb.session

class SessionManager(private val sessionStorage: SessionStorage) {
    suspend fun save(session: Session) {
        sessionStorage.save(session)
    }
    fun get(): Session?{
        return sessionStorage.get()
    }
    suspend fun clear(){
        sessionStorage.clear()
    }

}