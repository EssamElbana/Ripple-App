package com.essam.rippleapp.data.cache

import com.essam.rippleapp.domain.Repo

class InMemoryCacheImp : InMemoryCache {
    private val map: MutableMap<String, List<Repo>> = HashMap()

    override fun save(key: String, list: List<Repo>) {
        if(map.containsKey(key))
            map.remove(key)
        map[key] = list
    }

    override fun load(key: String): List<Repo> {
        return map[key] ?: emptyList()
    }
}

interface InMemoryCache {
    fun save(key: String, list: List<Repo>)
    fun load(key: String): List<Repo>
}