package com.hasan.dnb.session

import com.russhwolf.settings.Settings

class SettingsSessionStorage(
    val settings: Settings
) : SessionStorage {

    companion object {
        private const val ACCESS_TOKEN = "access_token"
    }

    override suspend fun save(session: Session) {
        settings.putString(ACCESS_TOKEN, session.accessToken)
    }

    override fun get(): Session? {
        val token = settings.getStringOrNull(ACCESS_TOKEN)
        return token?.let {
            Session(it)
        }
    }

    override suspend fun clear() {
        settings.remove(ACCESS_TOKEN)
    }

}