package com.example.realmlivedatatest.model

import io.realm.RealmObject

open class Counter(
    var count: Int = 1
) : RealmObject() {
    fun increment() {
        count++
    }
}