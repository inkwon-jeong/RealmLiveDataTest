package com.example.realmlivedatatest.realm

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import io.realm.OrderedRealmCollectionChangeListener
import io.realm.RealmModel
import io.realm.RealmResults


class LiveRealmObject<T : RealmModel> @MainThread constructor(
    private val results: RealmResults<T>
) : LiveData<T?>() {

    private val listener =
        OrderedRealmCollectionChangeListener<RealmResults<T>> { results, _ ->
            value = results.firstOrNull()
        }

    override fun onActive() {
        super.onActive()
        if (results.isValid) { // invalidated results can no longer be observed.
            value = results.firstOrNull()
            results.addChangeListener(listener)
        }
    }

    override fun onInactive() {
        super.onInactive()
        if (results.isValid) {
            results.removeChangeListener(listener)
        }
    }

    init {
        require(results.isManaged)
        require(results.isValid)

        if (results.isLoaded) {
            value = results.firstOrNull()
        }
    }
}